package io.lx.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.exception.RenException;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.common.utils.ConvertUtils;
import io.lx.dao.HighlightsDao;
import io.lx.dto.HighlightsDTO;
import io.lx.entity.HighlightsEntity;
import io.lx.service.HighlightsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-11-09
 */
@Service
public class HighlightsServiceImpl extends CrudServiceImpl<HighlightsDao, HighlightsEntity, HighlightsDTO> implements HighlightsService {

    @Override
    public QueryWrapper<HighlightsEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<HighlightsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }

    @Override
    public List<HighlightsDTO> getJourneyDetail(Integer journeyId){
        QueryWrapper<HighlightsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("journey_id", journeyId);
        wrapper.orderByAsc("light_seq");
        List<HighlightsEntity> list = baseDao.selectList(wrapper);
        if (list.isEmpty()){
            throw new RenException("管理员未录入行程详情、游玩亮点");
        }
        return ConvertUtils.sourceToTarget(list, HighlightsDTO.class);
    }


}