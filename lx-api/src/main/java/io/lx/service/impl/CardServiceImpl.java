package io.lx.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.exception.RenException;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.dao.CardDao;
import io.lx.dto.CardDTO;
import io.lx.dto.OrdersDTO;
import io.lx.dto.RoutesGuidesDTO;
import io.lx.entity.CardEntity;
import io.lx.entity.CardRedeemEntity;
import io.lx.entity.UserEntity;
import io.lx.service.*;
import io.lx.utils.OrderNumberUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static io.lx.constant.ApiConstant.*;

/**
 * 卡密表实现
 *
 * @author Mofeng
 * @since 1.0.0 2025-03-16
 */
@Slf4j
@Service
public class CardServiceImpl extends CrudServiceImpl<CardDao, CardEntity, CardDTO> implements CardService {

    @Lazy
    @Resource
    UserMembershipsService userMembershipsService;
    @Lazy
    @Resource
    OrdersService ordersService;
    @Lazy
    @Resource
    RoutesGuidesService routesGuidesService;
    @Lazy
    @Resource
    WxPayService wxPayService;

    @Resource
    CardRedeemService cardRedeemService;

    @Override
    public QueryWrapper<CardEntity> getWrapper(Map<String, Object> params) {
        String id = (String) params.get("id");

        QueryWrapper<CardEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }

    /**
     * 幂等
     * 兑换卡密
     * 0.查询用户状态，如果用户是会员，提示无需兑换
     * 1.判断卡密是否存在、有效
     * 2.判断卡密类型，如果是线路：判断用户是否有了权限,线路是否未下架
     * 3.生成有效订单
     * 4.如果是会员：设置会员
     * 5.如果是线路：添加线路
     * 6.卡密设为已兑换
     *
     * @param dto
     * @param user
     */
    @Override
    @Transactional
    public void exchangeCard(CardDTO dto, UserEntity user){

        // 判断不为空，且长度为16位
        if (StrUtil.isBlank(dto.getCardCode())||dto.getCardCode().length()!=16){
            throw new RenException("卡密格式不正对");
        }

        //0.查询用户状态，如果用户是会员，提示无需兑换
        if (StrUtil.isNotBlank(user.getSvip()) && ONE_STRING.equals(user.getSvip())){
            throw new RenException("用户已是会员，无需兑换！");
        }

        // 1. 判断卡密是否存在、有效
        QueryWrapper<CardEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("card_code", dto.getCardCode());
        CardEntity cardEntity = baseDao.selectOne(wrapper);

        if (cardEntity == null) {
            throw new RenException("卡密无效");
        }
        if (!ZERO_STRING.equals(cardEntity.getStatus().toString()) ) {
            throw new RenException("卡密已使用");
        }
        if (cardEntity.getExpireTime() == null ||
                cardEntity.getExpireTime().before(new Date())) {
            throw new RenException("卡密已过期");
        }

        //2.判断卡密类型
        String type = cardEntity.getProductType();
        if (type.isBlank()){
            throw new RenException("本卡无效，类型缺失，请联系工作人员");
        }
        if (!type.equals(dto.getProductType())){
            throw new RenException("本卡非当前类型，请重新选择兑换类别");
        }

        // 卡密是线路，判断用户是否已有权限
        if (ORDER_TYPE_ROUTES.equals(type)){

            if (!cardEntity.getRoutesGuidesId().equals(dto.getRoutesGuidesId())){
                throw new RenException("本卡非当前线路，请重新选择");
            }
            Map<String, Object> userShipsMap = userMembershipsService.getMembershipsByUserId(user.getId());

            // 获取用户已购的线路 ID 列表（ key 为 "routesGuidesIds"）
            List<Integer> purchasedRouteIds = (List<Integer>) userShipsMap.get("routesGuidesIds");

            // 判断线路未购买过
            if (purchasedRouteIds != null && purchasedRouteIds.contains(cardEntity.getRoutesGuidesId())) {
                throw new RenException("您已拥有该线路的访问权限，无需重复使用卡密兑换");
            }
        }


        // 3.生成有效订单
        String outTradeNo = OrderNumberUtils.generateOrderNumber();// 生成订单号
        OrdersDTO ordersDTO = new OrdersDTO();
        ordersDTO.setOrderId(outTradeNo);//订单号
        ordersDTO.setUserId(user.getId());// 用户id
        ordersDTO.setOpenid(user.getOpenid()); // 用户openid
        ordersDTO.setProductType(cardEntity.getProductType());  // 产品类型
        if (ORDER_TYPE_ROUTES.equals(type)){
            // 判断线路是否有效
            RoutesGuidesDTO  routesGuidesDTO= routesGuidesService.getRoutesGuidesDetail(cardEntity.getRoutesGuidesId());
            if (routesGuidesDTO==null){
                throw new RenException("此线路已下架，无法兑换，请联系工作人员");
            }
            ordersDTO.setProductId(cardEntity.getRoutesGuidesId()); // 线路id
            ordersDTO.setDescription("[已兑换]"+routesGuidesDTO.getTitle());
        }else if (ORDER_TYPE_SVIP.equals(type)){
            ordersDTO.setProductId(0); // 会员为0
            ordersDTO.setDescription("[已兑换]会员码核销");
        }
        ordersDTO.setAmount(BigDecimal.valueOf(0.00)); // 金额0
        ordersDTO.setStatus(ORDER_STATUS_SUCCESS); // 状态1-已支付
        ordersDTO.setMobile(user.getMobile()); // 手机号
        // 写入订单表
        ordersService.creatOrder(ordersDTO);

        // 4.兑换会员或者线路
        wxPayService.updateUserMemShips(ordersDTO.getOrderId());

        // 5.写入兑换记录表
        CardRedeemEntity cardRedeemEntity = new CardRedeemEntity();
        cardRedeemEntity.setUserId(user.getId());
        cardRedeemEntity.setCardCode(cardEntity.getCardCode());
        cardRedeemEntity.setProductType(cardEntity.getProductType());
        if (ORDER_TYPE_ROUTES.equals(type)){
            cardRedeemEntity.setRoutesGuidesId(cardEntity.getRoutesGuidesId());
        }
        cardRedeemEntity.setRedeemTime(new Date());
        cardRedeemService.insert(cardRedeemEntity);

        // 5.设置卡密失效，写入兑换记录表
        cardEntity.setStatus(1);
        cardEntity.setUpdatedAt(new Date());
        baseDao.updateById(cardEntity);

    }


}

