package io.lx.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.lx.common.page.PageData;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.common.utils.AESUtils;
import io.lx.common.utils.ConvertUtils;
import io.lx.dao.TravelGuidesDao;
import io.lx.dto.MyTravelGuidesDTO;
import io.lx.dto.TravelGuidesDTO;
import io.lx.dto.UserDetailDTO;
import io.lx.entity.TravelGuidesEntity;
import io.lx.service.TravelGuidesService;
import io.lx.service.UserMembershipsService;
import io.lx.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.lx.constant.ApiConstant.ZERO_STRING;

/**
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-05
 */
@Service
public class TravelGuidesServiceImpl extends CrudServiceImpl<TravelGuidesDao, TravelGuidesEntity, TravelGuidesDTO> implements TravelGuidesService {

    @Resource
    UserService userService;
    @Resource
    AESUtils aesUtils;
    @Resource
    UserMembershipsService userMembershipsService;

    @Override
    public QueryWrapper<TravelGuidesEntity> getWrapper(Map<String, Object> params) {
        String id = (String) params.get("id");

        QueryWrapper<TravelGuidesEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }

    @Override
    public List<TravelGuidesDTO> getTravelGuidesList(String keyword, String orderField, String order) {
        QueryWrapper<TravelGuidesEntity> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(keyword)) {
            wrapper.like("title", keyword)
                    .or().like("sub_title", keyword);
        }
        if (StrUtil.isNotBlank(orderField) && StrUtil.isNotBlank(order)) {
            if ("asc".equalsIgnoreCase(order)) {
                wrapper.orderByAsc(orderField);
            } else if ("desc".equalsIgnoreCase(order)) {
                wrapper.orderByDesc(orderField);
            }
        }
        List<TravelGuidesEntity> list = baseDao.selectList(wrapper);
        return ConvertUtils.sourceToTarget(list, TravelGuidesDTO.class);
    }

    @Override
    public TravelGuidesDTO getTravelGuidesDetail(Integer id) {
        TravelGuidesEntity entity = baseDao.selectById(id);
        TravelGuidesDTO dto = new TravelGuidesDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    @Override
    public PageData<MyTravelGuidesDTO> getMyTravelGuides(String token, String keyword,Map<String, Object> params) {
        // 1.获取用户会员身份
        UserDetailDTO userDetailDTO = userService.getUserInfoDetailByToken(token);
        String svip = userDetailDTO.getSvip();

        // 创建分页对象
        IPage<TravelGuidesEntity> page = null;

        // 初始化查询条件
        QueryWrapper<TravelGuidesEntity> wrapper = new QueryWrapper<>();

        // 根据是否svip进行查询: 0-非会员，1-终身全部会员
        if (ZERO_STRING.equals(svip)){
            // 终身会员，返回所有产品的分页列表
            // 如果有 keyword，按 title 和 subtitle 模糊匹配
            if (StrUtil.isNotBlank(keyword)) {
                wrapper.like("title", keyword)
                        .or()
                        .like("sub_title", keyword);
            }
            page = baseDao.selectPage(getPage(params, null, false), wrapper);
        }else {
            Map<String, Object> memberships = userMembershipsService.getMembershipsByUserId(userDetailDTO.getId());
            List<Integer> travelGuidesIds = (List<Integer>) memberships.get("travelGuidesIds");
            if (travelGuidesIds.isEmpty()) {
                // 如果用户没有已购产品，返回空分页
                return new PageData<>(Collections.emptyList(), 0,0,0,0);
            }
            // 将购买的产品ID设置为查询条件，结合模糊搜索条件
            wrapper.in("id", travelGuidesIds);
            // 如果有 keyword，按 title 和 subtitle 模糊匹配
            if (StrUtil.isNotBlank(keyword)) {
                wrapper.like("title", keyword)
                        .or()
                        .like("sub_title", keyword);
            }
            page = baseDao.selectPage(getPage(params, null, false), wrapper);
        }

        // 将结果转换为 DTO 并处理解密逻辑
        List<MyTravelGuidesDTO> dtoList = page.getRecords().stream().map(entity -> {
            MyTravelGuidesDTO dto = convertToDTO(entity);
            // 解密 asset 字段
            try {
                dto.setAsset(decryptAssetField(dto.getAsset()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return dto;
        }).collect(Collectors.toList());
        return new PageData<>(dtoList, page.getTotal(),page.getSize(),page.getPages(),page.getCurrent());
    }

    // 转换实体对象到 DTO
    private MyTravelGuidesDTO convertToDTO(TravelGuidesEntity entity) {
        MyTravelGuidesDTO dto = new MyTravelGuidesDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    // 解密 asset 字段的方法
    private String decryptAssetField(String encryptedAsset) throws Exception {
        return aesUtils.decrypt(encryptedAsset);
    }
}