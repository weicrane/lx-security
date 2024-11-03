package io.lx.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.dao.UserMembershipsDao;
import io.lx.dto.UserMembershipsDTO;
import io.lx.entity.OrdersEntity;
import io.lx.entity.UserMembershipsEntity;
import io.lx.service.OrdersService;
import io.lx.service.UserMembershipsService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.lx.constant.ApiConstant.*;

/**
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-19
 */
@Service
public class UserMembershipsServiceImpl extends CrudServiceImpl<UserMembershipsDao, UserMembershipsEntity, UserMembershipsDTO> implements UserMembershipsService {
    @Resource
    OrdersService ordersService;

    @Override
    public QueryWrapper<UserMembershipsEntity> getWrapper(Map<String, Object> params) {
        String id = (String) params.get("id");

        QueryWrapper<UserMembershipsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }

    @Override
    public Map<String, Object> getMembershipsByUserId(Long userId) {
        // 1.获取全部会员实体
        QueryWrapper<UserMembershipsEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId) // 添加查询条件
                .select("travel_guides_id", "self_drivings_id", "routes_guides_id");
        List<UserMembershipsEntity> shipsList = baseDao.selectList(queryWrapper);

        // 2.创建三个列表来存储 travel_guides_id, self_drivings_id 和 routes_guides_id
        List<Integer> travelGuidesIds = new ArrayList<>();
        List<Integer> selfDrivingsIds = new ArrayList<>();
        List<Integer> routesGuidesIds = new ArrayList<>();

        // 3.遍历 shipsList，将每个实体的相应字段添加到对应的列表中
        for (UserMembershipsEntity entity : shipsList) {
            if (entity.getTravelGuidesId() != null) {
                travelGuidesIds.add(entity.getTravelGuidesId());
            }
            if (entity.getSelfDrivingsId() != null) {
                selfDrivingsIds.add(entity.getSelfDrivingsId());
            }
            if (entity.getRoutesGuidesId() != null) {
                routesGuidesIds.add(entity.getRoutesGuidesId());
            }
        }

        // 此时 travelGuidesIds, selfDrivingsIds, routesGuidesIds 分别包含了对应的 ID 列表
        Map<String, Object> map = new HashMap<>(2);
        map.put("travelGuidesIds", travelGuidesIds);
        map.put("selfDrivingsIds", selfDrivingsIds);
        map.put("routesGuidesIds", routesGuidesIds);
        return map;
    }

    public void updateUserMemShips(String orderId) {
        // 1.查询订单详情
        OrdersEntity orderEntity = ordersService.getOrderDetail(orderId);
        // 判断类型
        UserMembershipsEntity entity = new UserMembershipsEntity();
        // 2-1.网盘会员
        if (ORDER_TYPE_WANGPAN.equals(orderEntity.getProductType())){
            entity.setUserId(orderEntity.getUserId()); // userID
            entity.setMemberType(ORDER_TYPE_WANGPAN); //会员
            entity.setTravelGuidesId(orderEntity.getProductId());//网盘路书id
        }else if (ORDER_TYPE_ROUTES.equals(orderEntity.getProductType())){
            // 2-2.玩法会员
            entity.setUserId(orderEntity.getUserId()); // userID
            entity.setMemberType(ORDER_TYPE_ROUTES); //会员
            entity.setRoutesGuidesId(orderEntity.getProductId());//网盘路书id
        }


        // 写入表
        baseDao.insert(entity);

        // 3.其他情况
    }

    @Override
    public Boolean isMember(Long userId,String type, Integer productId){

        QueryWrapper<UserMembershipsEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId) // 添加查询条件
                .eq("member_type",type);

        switch (type) {
            case ORDER_TYPE_WANGPAN:
                // 网盘路书
                queryWrapper.eq("travel_guides_id", productId); // 添加查询条件
                break;
            case ORDER_TYPE_DRIVING:
                // 自驾活动
                queryWrapper.eq("self_drivings_id", productId); // 添加查询条件
                break;
            // 可以添加更多 case 语句
            case ORDER_TYPE_ROUTES:
                // 四季玩法
                queryWrapper.eq("routes_guides_id", productId); // 添加查询条件
                break;
            default:
                // 当所有 case 都不匹配时执行的代码
                break;
        }

        List<UserMembershipsEntity> shipsList = baseDao.selectList(queryWrapper);

        if (shipsList!=null && shipsList.size()>0){
            return true;
        }else {
            return false;
        }

    }



}