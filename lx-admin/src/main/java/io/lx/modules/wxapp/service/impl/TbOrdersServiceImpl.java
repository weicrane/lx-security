package io.lx.modules.wxapp.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.lx.common.page.PageData;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.modules.wxapp.dao.TbOrdersDao;
import io.lx.modules.wxapp.dto.TbOrdersDTO;
import io.lx.modules.wxapp.entity.TbOrdersEntity;
import io.lx.modules.wxapp.entity.TbUserEntity;
import io.lx.modules.wxapp.service.TbOrdersService;
import io.lx.modules.wxapp.service.TbUserService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-26
 */
@Service
public class TbOrdersServiceImpl extends CrudServiceImpl<TbOrdersDao, TbOrdersEntity, TbOrdersDTO> implements TbOrdersService {

    @Lazy
    @Resource
    private TbUserService tbUserService;
    @Override
    public QueryWrapper<TbOrdersEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<TbOrdersEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }

    /**
     * 分页查询订单
     * @param params
     * @return
     */
    @Override
    public PageData<TbOrdersDTO> getOrderListByPage(Map<String, Object> params) {
        QueryWrapper<TbOrdersEntity> wrapper = new QueryWrapper<>();

        // 0.订单号
        String orderId = Objects.toString(params.get("orderId"), "").trim();
        if (StrUtil.isNotBlank(orderId)) {
            wrapper.eq("order_id", orderId);
        }

        // 1.手机号条件处理：先获取用户id，根据id筛选
        String mobile = Objects.toString(params.get("mobile"), "").trim();
        if (StrUtil.isNotBlank(mobile)) {
            TbUserEntity user = tbUserService.getUserEntityByMobile(mobile);
            if (user != null) {
                wrapper.eq("user_id", user.getId());  // 假设 user_id 是订单表中的字段
            }
        }

        // 2.订单类型
        String productType = Objects.toString(params.get("productType"), "").trim();
        if (StrUtil.isNotBlank(productType)) {
            wrapper.eq("product_type", productType);
        }

        // 3.支付内容
        String description = Objects.toString(params.get("description"), "").trim();
        if (StrUtil.isNotBlank(description)) {
            wrapper.like("description", description);
        }

        // 4.支付状态
        String status = Objects.toString(params.get("status"), "").trim();
        if (StrUtil.isNotBlank(status)) {
            wrapper.eq("status", status);
        }

        // 5.分页查询，按照 updated_at 倒序排序
        IPage<TbOrdersEntity> page = baseDao.selectPage(getPage(params, "updated_at", false), wrapper);

        // 6.查询线路详情，构造返回（根据线路id查询详情）
        List<TbOrdersDTO> dtoList = page.getRecords().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return new PageData<>(dtoList, page.getTotal(), page.getSize(), page.getPages(), page.getCurrent());
    }

    // 转换实体对象到 DTO
    private TbOrdersDTO convertToDTO(TbOrdersEntity entity) {
        TbOrdersDTO dto = new TbOrdersDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    /**
     * 批量补手机号
     */
    public void test(){
        QueryWrapper<TbOrdersEntity> wrapper = new QueryWrapper<>();
        wrapper.isNull("mobile").or().eq("mobile", "");
        List<TbOrdersEntity> list = baseDao.selectList(wrapper);

        for (TbOrdersEntity order : list) {
            if (StrUtil.isBlank(order.getMobile())) { // 判断 mobile 是否为空
                TbUserEntity user = tbUserService.getUserEntity(order.getUserId()); // 根据 userId 查询用户
                if (user != null && StrUtil.isNotBlank(user.getMobile())) {
                    order.setMobile(user.getMobile()); // 补充 mobile 字段
                }
            }
        }
        // 如果需要批量更新数据库
        if (!list.isEmpty()) {
            for (TbOrdersEntity order : list) {
                baseDao.updateById(order); // 更新数据库中的数据
            }
        }
    }

}