package io.lx.modules.wxapp.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.lx.common.exception.RenException;
import io.lx.common.page.PageData;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.modules.wxapp.dao.TbCardRedeemDao;
import io.lx.modules.wxapp.dto.TbCardRedeemDTO;
import io.lx.modules.wxapp.entity.TbCardRedeemEntity;
import io.lx.modules.wxapp.service.TbCardRedeemService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 卡密兑换记录表
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2025-03-16
 */
@Service
public class TbCardRedeemServiceImpl extends CrudServiceImpl<TbCardRedeemDao, TbCardRedeemEntity, TbCardRedeemDTO> implements TbCardRedeemService {

    @Override
    public QueryWrapper<TbCardRedeemEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<TbCardRedeemEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }

    /**
     * 搜索卡密列表-分页
     */
    @Override
    public PageData<TbCardRedeemDTO> searchByPage(Map<String, Object> params){
        QueryWrapper<TbCardRedeemEntity> wrapper = new QueryWrapper<>();

        // 处理 routesGuidesId 查询
        if (params.get("routesGuidesId") != null && !params.get("routesGuidesId").toString().isBlank()) {
            try {
                Integer routesGuidesId = Integer.parseInt(params.get("routesGuidesId").toString());
                wrapper.eq("routes_guides_id", routesGuidesId);
            } catch (NumberFormatException e) {
                throw new RenException("线路ID格式不正确");
            }
        }

        // 处理 userId 查询
        if (params.get("userId") != null && !params.get("userId").toString().isBlank()) {
            try {
                Long userId = Long.parseLong(params.get("userId").toString());
                wrapper.eq("user_id", userId);
            } catch (NumberFormatException e) {
                throw new RenException("用户ID格式不正确");
            }
        }

        // 处理 productType 查询
        if (params.get("productType") != null && StrUtil.isNotBlank(params.get("productType").toString())) {
            wrapper.eq("product_type", params.get("productType").toString());
        }

        // 分页查询
        IPage<TbCardRedeemEntity> page = baseDao.selectPage(getPage(params, "updated_at", false), wrapper);

        List<TbCardRedeemDTO> dtoList = page.getRecords().stream().map(entity -> {
            TbCardRedeemDTO dto = convertToDTO(entity);
            return dto;
        }).collect(Collectors.toList());

        return new PageData<>(dtoList, page.getTotal(),page.getSize(),page.getPages(),page.getCurrent());

    }
    // 转换实体对象到 DTO
    private TbCardRedeemDTO convertToDTO(TbCardRedeemEntity entity) {
        TbCardRedeemDTO dto = new TbCardRedeemDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}