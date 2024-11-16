package io.lx.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.dao.PoiInfoDao;
import io.lx.dto.PoiInfoDTO;
import io.lx.entity.PoiInfoEntity;
import io.lx.service.PoiInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.lx.constant.ApiConstant.ZERO_STRING;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-11-02
 */
@Service
public class PoiInfoServiceImpl extends CrudServiceImpl<PoiInfoDao, PoiInfoEntity, PoiInfoDTO> implements PoiInfoService {

    @Override
    public QueryWrapper<PoiInfoEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<PoiInfoEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }

    @Override
    public List<PoiInfoDTO> getPoiList(Integer routeGuideId,List<String> poiTypeList,String dateId,String journeyType){
        QueryWrapper<PoiInfoEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("guides_id",routeGuideId);

        if (poiTypeList != null && !poiTypeList.isEmpty()) {
            // 遍历 poiTypeList，将每个值去掉前导零后加入查询条件
            List<String> formattedPoiTypes = poiTypeList.stream()
                    .map(poiType -> poiType.replaceAll("^0+", "")) // 去掉前导零
                    .collect(Collectors.toList());

            // 使用 in 查询条件，匹配多个 poiType
            wrapper.in("poi_type", formattedPoiTypes);
        }

        // 传0或者不传表示总览
        if (StrUtil.isNotBlank(dateId) && !ZERO_STRING.equals(dateId)){
            wrapper.eq("date_id",dateId);
        }

        wrapper.eq("journey_type",journeyType);

        List<PoiInfoEntity> entityList = baseDao.selectList(wrapper);

        // 将实体列表转换为 DTO 列表
        List<PoiInfoDTO> dtoList = new ArrayList<>();
        for (PoiInfoEntity entity : entityList) {
            PoiInfoDTO dto = convertToDTO(entity);  // 转换为 DTO
            dtoList.add(dto);  // 添加到 DTO 列表
        }

        return dtoList;  // 返回 DTO 列表
    }

    // 转换实体对象到 DTO
    private PoiInfoDTO convertToDTO(PoiInfoEntity entity) {
        PoiInfoDTO dto = new PoiInfoDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    @Override
    public PoiInfoDTO getPoiInfo(Long pointId){
        PoiInfoEntity entity = baseDao.selectById(pointId);
        PoiInfoDTO dto = new PoiInfoDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

}