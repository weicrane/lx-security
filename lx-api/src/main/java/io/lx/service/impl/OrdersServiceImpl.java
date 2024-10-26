package io.lx.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wechat.pay.java.service.payments.jsapi.model.Amount;
import io.lx.common.exception.RenException;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.dao.OrdersDao;
import io.lx.dto.OrdersDTO;
import io.lx.dto.TravelGuidesDTO;
import io.lx.entity.OrdersEntity;
import io.lx.service.OrdersService;
import io.lx.service.TravelGuidesService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-26
 */
@Service
public class OrdersServiceImpl extends CrudServiceImpl<OrdersDao, OrdersEntity, OrdersDTO> implements OrdersService {

    @Resource
    TravelGuidesService travelGuidesService;


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
     * 获取产品价格
     * 00-终身会员，01-网盘路书，a02-自驾活动，03-四季玩法
     */
    public Amount getAmount(String productType, Long productId){
        Amount amount = new Amount();
        switch (productType) {
            case "00":
                // 处理终身会员的逻辑
                amount.setTotal(1);//TODO:终身会员价格要修改
                break;

            case "01":
                // 处理网盘路书的逻辑
                TravelGuidesDTO dto = travelGuidesService.getTravelGuidesDetail(productId);
                try {
                    BigDecimal price = dto.getPrice();
                    amount.setTotal(price.multiply(BigDecimal.valueOf(100)).intValue());
                }catch (Exception e){
                    throw new RenException("查询商品价格失败");
                }
                break;

            case "a02":
                // 处理自驾活动的逻辑
                break;

            case "03":
                // 处理四季玩法的逻辑
                break;

            default:
                // 处理其他情况的逻辑
                break;
        }
    return amount;
    }


}