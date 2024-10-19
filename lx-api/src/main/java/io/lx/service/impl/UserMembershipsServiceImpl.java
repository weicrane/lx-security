package io.lx.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.dao.UserMembershipsDao;
import io.lx.dto.UserMembershipsDTO;
import io.lx.entity.UserMembershipsEntity;
import io.lx.service.UserMembershipsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-19
 */
@Service
public class UserMembershipsServiceImpl extends CrudServiceImpl<UserMembershipsDao, UserMembershipsEntity, UserMembershipsDTO> implements UserMembershipsService {

    @Override
    public QueryWrapper<UserMembershipsEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<UserMembershipsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }

    @Override
    public Map<String, Object>  getMembershipsByUserId(Long userId){
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
        map.put("travelGuidesIds",travelGuidesIds);
        map.put("selfDrivingsIds",selfDrivingsIds);
        map.put("routesGuidesIds",routesGuidesIds);
        return map;
    }


}