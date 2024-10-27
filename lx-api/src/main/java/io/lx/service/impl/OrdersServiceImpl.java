package io.lx.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.lx.common.page.PageData;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.dao.OrdersDao;
import io.lx.dto.OrdersDTO;
import io.lx.entity.OrdersEntity;
import io.lx.entity.UserEntity;
import io.lx.service.OrdersService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.lx.constant.ApiConstant.ORDER_STATUS_SUCCESS;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-26
 */
@Service
public class OrdersServiceImpl extends CrudServiceImpl<OrdersDao, OrdersEntity, OrdersDTO> implements OrdersService {

    @Override
    public QueryWrapper<OrdersEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<OrdersEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }

    /**
     * 写入预支付订单
     */
    public void creatOrder(OrdersDTO ordersDTO){
        OrdersEntity entity = new OrdersEntity();
        BeanUtils.copyProperties(ordersDTO,entity);
        baseDao.insert(entity);
    }

    /**
     * 查询订单状态
     * @param orderId
     * @return
     */
    public String getOederStatus(String orderId){
        // 查询订单
        OrdersEntity entity = baseDao.selectById(orderId);
        if (entity == null) {
            throw new IllegalArgumentException("订单不存在，无法更新状态");
        }
        return entity.getStatus();
    }

    /**
     * 更新订单状态: 0-未支付，1-已支付，2-支付失败, 3-取消支付
     *
     */
    public void updateOrderStatus(String orderId, String status) {
        // 查询订单
        OrdersEntity entity = baseDao.selectById(orderId);
        if (entity == null) {
            throw new IllegalArgumentException("订单不存在，无法更新状态");
        }
        if (ORDER_STATUS_SUCCESS.equals(entity.getStatus())) {
            throw new IllegalArgumentException("订单已完成，无法重复操作");
        }

        // 使用 UpdateWrapper 更新状态
        UpdateWrapper<OrdersEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("order_id", orderId).set("status", status);

        // 执行更新
        baseDao.update(null, updateWrapper);
    }

    @Override
    public OrdersEntity getOrderDetail(String orderId){
        return baseDao.selectById(orderId);
    }

    /**
     * 订单状态: 0-未支付，1-已支付，2-支付失败, 3-取消支付;
     * 对应前端参数：不传-全部，待付款（0-未支付），3-已取消（3-取消支付），1-已完成（1-已支付）
     * @param params
     * @param keyword
     * @param status
     * @param user
     * @return
     */
    @Override
    public PageData<OrdersDTO> getOrderListByPage(Map<String, Object> params,
                                                  String keyword,
                                                  String status,
                                                  UserEntity user){
        // 1.创建分页对象
        IPage<OrdersEntity> page = null;
        // 2.初始化查询条件
        QueryWrapper<OrdersEntity> wrapper = new QueryWrapper<>();

        // 3. 如果有 keyword，按 description 模糊匹配
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.lambda().like(OrdersEntity::getDescription, keyword);
        }

        // 4. 如果有 status，则按状态匹配
        if (status != null && !status.isEmpty()) {
            wrapper.lambda().eq(OrdersEntity::getStatus, status);
        }

        // 5. 按 userId 匹配用户订单
        if (user != null && user.getId() != null) {
            wrapper.lambda().eq(OrdersEntity::getUserId, user.getId());
        }

        // 6. 执行分页查询
        IPage<OrdersEntity> resultPage = baseDao.selectPage(getPage(params, null, false), wrapper);

        // 7. 转换结果为 DTO 并返回分页数据
        List<OrdersDTO> list = resultPage.getRecords().stream()
                .map(order -> {
                    OrdersDTO dto = new OrdersDTO();
                    BeanUtils.copyProperties(order, dto);
                    return dto;
                }).collect(Collectors.toList());

        return new PageData<>(list, resultPage.getTotal(),resultPage.getSize(),resultPage.getPages(),resultPage.getCurrent());
    }

}