package io.lx.modules.wxapp.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.lx.common.exception.RenException;
import io.lx.common.page.PageData;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.modules.wxapp.dao.TbCardDao;
import io.lx.modules.wxapp.dto.GenCardsDTO;
import io.lx.modules.wxapp.dto.TbCardDTO;
import io.lx.modules.wxapp.entity.TbCardEntity;
import io.lx.modules.wxapp.service.TbCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.baomidou.mybatisplus.extension.toolkit.Db.saveBatch;

/**
 * 卡密表实现
 *
 * @author Mofeng
 * @since 1.0.0 2025-03-16
 */
@Slf4j
@Service
public class TbCardServiceImpl extends CrudServiceImpl<TbCardDao, TbCardEntity, TbCardDTO> implements TbCardService {

    private static final String CHARACTERS = "ABCDEFGHJKMNPQRSTUVWXYZ2356789";
    private static final int CODE_LENGTH = 16;
    private static final int MAX_RETRY = 3;
    private static final int BATCH_SIZE = 500;

    // 线程安全的 SecureRandom
    private static final ThreadLocal<SecureRandom> RANDOM_THREAD_LOCAL = ThreadLocal.withInitial(SecureRandom::new);

    @Override
    public QueryWrapper<TbCardEntity> getWrapper(Map<String, Object> params) {
        String id = (String) params.get("id");

        QueryWrapper<TbCardEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }

    /**
     * 生成卡密
     * TODO:校验线路ID
     * @param dto 请求参数
     */
    @Transactional(rollbackFor = Exception.class)
    public void genCards(GenCardsDTO dto) {
        validateDTO(dto);

        Set<String> cardCodes = generateUniqueCardCodes(dto.getAmount());

        // 先检查数据库是否已存在相同卡密，避免不必要的插入失败
        Set<String> existingCodes = findExistingCodes(cardCodes);
        cardCodes.removeAll(existingCodes);

        if (cardCodes.isEmpty()) {
            throw new RenException("生成的卡密全部重复，请重试");
        }

        insertWithRetry(dto, cardCodes, MAX_RETRY);
    }

    /**
     * 参数校验
     */
    private void validateDTO(GenCardsDTO dto) {
        if (dto.getAmount() == null || dto.getAmount() <= 0) {
            throw new RenException("生成数量必须大于0");
        }
        if (!Set.of("00", "03").contains(dto.getProductType())) {
            throw new RenException("无效的产品类别");
        }
        if (dto.getExpireTime().before(new Date())) {
            throw new RenException("有效期必须晚于当前时间");
        }
        // 如果类型是 "00"，则 routesGuidesId 必须为空
        if ("00".equals(dto.getProductType()) && dto.getRoutesGuidesId()!=null) {
            throw new RenException("类型为会员时，线路必须为空");
        }
    }

    /**
     * 生成唯一的卡密
     */
    private Set<String> generateUniqueCardCodes(int amount) {
        return Stream.generate(() -> generateRandomCode(RANDOM_THREAD_LOCAL.get()))
                .parallel()
                .distinct()
                .limit(amount)
                .collect(Collectors.toCollection(() -> new HashSet<>(amount)));
    }

    /**
     * 生成随机卡密
     */
    private String generateRandomCode(SecureRandom random) {
        char[] buffer = new char[CODE_LENGTH];
        for (int i = 0; i < CODE_LENGTH; i++) {
            buffer[i] = CHARACTERS.charAt(random.nextInt(CHARACTERS.length()));
        }
        return new String(buffer);
    }

    /**
     * 通过多次尝试插入
     */
    private void insertWithRetry(GenCardsDTO dto, Set<String> cardCodes, int retryLeft) {
        List<TbCardEntity> cards = buildCardList(dto, cardCodes);

        try {
            boolean success = saveBatch(cards, BATCH_SIZE);
            if (!success) {
                throw new RuntimeException("批量插入失败");
            }
        } catch (Exception e) {
            log.error("卡密批量插入失败，剩余重试次数: {}", retryLeft, e);
            if (retryLeft > 0) {
                Set<String> failedCodes = findFailedCodes(cardCodes);
                insertWithRetry(dto, failedCodes, retryLeft - 1);
            } else {
                throw new RenException("本次卡密生成失败，请重试");
            }
        }
    }

    /**
     * 查找已存在的卡密，减少插入失败
     */
    private Set<String> findExistingCodes(Set<String> expectedCodes) {
        if (expectedCodes.isEmpty()) {
            return Collections.emptySet();
        }

        QueryWrapper<TbCardEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("card_code").in("card_code", expectedCodes);

        List<TbCardEntity> existingList = baseDao.selectList(queryWrapper);

        return existingList.stream()
                .map(TbCardEntity::getCardCode)
                .collect(Collectors.toSet());
    }

    /**
     * 找出插入失败的卡密（可能由于唯一性约束失败）
     */
    private Set<String> findFailedCodes(Set<String> expectedCodes) {
        Set<String> existingCodes = findExistingCodes(expectedCodes);

        Set<String> failedCodes = new HashSet<>(expectedCodes);
        failedCodes.removeAll(existingCodes);

        return failedCodes;
    }

    /**
     * 构建 TbCardEntity 列表
     */
    private List<TbCardEntity> buildCardList(GenCardsDTO dto, Set<String> cardCodes) {
        return cardCodes.stream()
                .map(code -> createCardEntity(dto, code))
                .collect(Collectors.toList());
    }

    /**
     * 创建 TbCardEntity 实例
     */
    private TbCardEntity createCardEntity(GenCardsDTO dto, String code) {
        TbCardEntity card = new TbCardEntity();
        card.setCardCode(code);
        card.setStatus(0);
        card.setExpireTime(dto.getExpireTime());
        card.setProductType(dto.getProductType());
        card.setRoutesGuidesId(dto.getRoutesGuidesId());
        card.setDescription(dto.getDescription());
        return card;
    }


    /**
     * 修改卡密
     * @param dto
     */
    public void editCard(TbCardDTO dto){
        TbCardEntity entity =  baseDao.selectById(dto.getId());
        if (entity==null){
            throw new RenException("查询卡密信息失败");
        }
        entity.setExpireTime(dto.getExpireTime());
        entity.setDescription(dto.getDescription());
        baseDao.updateById(entity);
    }



    /**
     * 搜索卡密列表-分页
     */
    @Override
    public PageData<TbCardDTO> searchByPage(Map<String, Object> params){
        QueryWrapper<TbCardEntity> wrapper = new QueryWrapper<>();

        // 处理 productType 查询
        if (params.get("routesGuidesId") != null && !params.get("routesGuidesId").toString().isBlank()) {
            try {
                Integer routesGuidesId = Integer.parseInt(params.get("routesGuidesId").toString());
                wrapper.eq("routes_guides_id", routesGuidesId);
            } catch (NumberFormatException e) {
                throw new RenException("线路ID格式不正确");
            }
        }
        // 处理 cardCode 查询
        if (params.get("cardCode") != null && StrUtil.isNotBlank(params.get("cardCode").toString())) {
            wrapper.eq("card_code", params.get("cardCode").toString());
        }


        // 处理 status 查询
        if (params.get("status") != null && !params.get("status").toString().isBlank()) {
            try {
                Integer status = Integer.parseInt(params.get("status").toString());
                wrapper.eq("status", status);
            } catch (NumberFormatException e) {
                throw new RenException("兑换状态格式不正确");
            }
        }

        // 处理 productType 查询
        if (params.get("productType") != null && StrUtil.isNotBlank(params.get("productType").toString())) {
            wrapper.eq("product_type", params.get("productType").toString());
        }

        // 分页查询
        IPage<TbCardEntity> page = baseDao.selectPage(getPage(params, "updated_at", false), wrapper);

        List<TbCardDTO> dtoList = page.getRecords().stream().map(entity -> {
            TbCardDTO dto = convertToDTO(entity);
            return dto;
        }).collect(Collectors.toList());

        return new PageData<>(dtoList, page.getTotal(),page.getSize(),page.getPages(),page.getCurrent());

    }
    // 转换实体对象到 DTO
    private TbCardDTO convertToDTO(TbCardEntity entity) {
        TbCardDTO dto = new TbCardDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}

