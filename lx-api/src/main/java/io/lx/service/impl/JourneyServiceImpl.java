package io.lx.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.exception.RenException;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.dao.JourneyDao;
import io.lx.dto.JourneyDTO;
import io.lx.entity.JourneyEntity;
import io.lx.entity.UserEntity;
import io.lx.service.JourneyService;
import io.lx.service.UserMembershipsService;
import io.lx.utils.KmlUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.lx.constant.ApiConstant.ONE_STRING;
import static io.lx.constant.ApiConstant.ORDER_TYPE_ROUTES;

/**
 *
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-11-02
 */
@Service
public class JourneyServiceImpl extends CrudServiceImpl<JourneyDao, JourneyEntity, JourneyDTO> implements JourneyService {

    @Resource
    UserMembershipsService userMembershipsService;
    @Resource
    KmlUtils kmlUtils;
    @Override
    public QueryWrapper<JourneyEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<JourneyEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }

    /**
     *   1.判断用户是否购买
     *   2.已购买，返回全部
     *   3.未购买，只返回第一天
     *
     * @param
     * @param journeyType
     * @param
     * @return
     */
    @Override
    public  Map<String,Object>  getMainJourney(Integer guideId, String journeyType, UserEntity user){
        // 查询会员关系，判断是否已购买或者终身会员
        QueryWrapper<JourneyEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("guide_id", guideId);
        wrapper.eq("journey_type",journeyType);
        wrapper.orderByAsc("date_id");
        List<JourneyEntity> list = baseDao.selectList(wrapper);
        int count = (list != null) ? list.size() : 0;

        Map<String,Object> map = new HashMap<>();
        map.put("count",count);

        Boolean isMember = userMembershipsService.isMember(user.getId(),ORDER_TYPE_ROUTES,guideId);
        if (ONE_STRING.equals(user.getSvip())||isMember){ // SVIP 或已购买
            // 返回全部
            map.put("journeyList",list);
            map.put("access",true);
        }else if (count > 0){
            // 其他情况，只返回第一条
            List<JourneyEntity> singleItemList = new ArrayList<>();
            singleItemList.add(list.get(0)); // 将第一条数据添加到新的列表
            map.put("journeyList", singleItemList);
            map.put("access",false);
        }else {
            // 没有设置
            map.put("journeyList",null);
            map.put("access",false);
        }
        return map;

    }


    @Override
    public Map<String,Object> getPathCoordinates(Integer journeyId){
        // 查询kml路径
        JourneyEntity entity = baseDao.selectById(journeyId);
        if (StrUtil.isBlank(entity.getKmlPath())){
            throw new RenException("管理员暂未录入路径数据");
        }
//        List<List<Double>> path = kmlUtils.parseKML(entity.getKmlPath(),"D1");
        Map<String, List<double[]>> path = kmlUtils.parseRoutes(entity.getKmlPath());

        Map<String,Object> map = new HashMap<>();
        map.put("path",path);
        return map;
    }
}