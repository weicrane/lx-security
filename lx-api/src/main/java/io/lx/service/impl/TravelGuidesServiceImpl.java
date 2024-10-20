package io.lx.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.common.utils.ConvertUtils;
import io.lx.dao.TravelGuidesDao;
import io.lx.dto.TravelGuidesDTO;
import io.lx.entity.TravelGuidesEntity;
import io.lx.service.TravelGuidesService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-05
 */
@Service
public class TravelGuidesServiceImpl extends CrudServiceImpl<TravelGuidesDao, TravelGuidesEntity,TravelGuidesDTO> implements TravelGuidesService {

    @Override
    public QueryWrapper<TravelGuidesEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<TravelGuidesEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }

    @Override
    public List<TravelGuidesDTO> getTravelGuidesList(String keyword){
        QueryWrapper<TravelGuidesEntity> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(keyword)){
            wrapper.like( "title", keyword)
                    .or().like("sub_title", keyword);
        }
        List<TravelGuidesEntity> list = baseDao.selectList(wrapper);
        return ConvertUtils.sourceToTarget(list,TravelGuidesDTO.class);
    }

}