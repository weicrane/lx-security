package io.lx.modules.wxapp.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.lx.common.exception.RenException;
import io.lx.common.page.PageData;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.modules.wxapp.dao.TbHighlightsDao;
import io.lx.modules.wxapp.dto.TbHighlightsDTO;
import io.lx.modules.wxapp.entity.TbHighlightsEntity;
import io.lx.modules.wxapp.entity.TbJourneyEntity;
import io.lx.modules.wxapp.service.TbHighlightsService;
import io.lx.modules.wxapp.service.TbJourneyService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-11-09
 */
@Service
public class TbHighlightsServiceImpl extends CrudServiceImpl<TbHighlightsDao, TbHighlightsEntity, TbHighlightsDTO> implements TbHighlightsService {

    @Resource
    TbJourneyService tbJourneyService;
    @Override
    public QueryWrapper<TbHighlightsEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<TbHighlightsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }

    @Override
    public PageData<TbHighlightsDTO> selectPage(Map<String, Object> params, Integer journeyId, Integer id){

        // 初始化查询条件
        QueryWrapper<TbHighlightsEntity> wrapper = new QueryWrapper<>();
        // 如果有行程id
        if (journeyId!= null) {
            wrapper.eq("journey_id", journeyId);
        }

        // 如果有id
        if (id!= null) {
            wrapper.eq("id", id);
        }

        // 按顺序排序
        wrapper.orderByAsc("light_seq");
        // 执行查询
        IPage<TbHighlightsEntity> page;
        page = baseDao.selectPage(getPage(params, "", false), wrapper);

        // 将结果转换为 DTO
        List<TbHighlightsDTO> dtoList = page.getRecords().stream().map(entity -> {
            TbHighlightsDTO dto = convertToDTO(entity);
            return dto;
        }).collect(Collectors.toList());

        return new PageData<>(dtoList, page.getTotal(),page.getSize(),page.getPages(),page.getCurrent());

    }
    // 转换实体对象到 DTO
    private TbHighlightsDTO convertToDTO(TbHighlightsEntity entity) {
        TbHighlightsDTO dto = new TbHighlightsDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }



    @Override
    public void submit(TbHighlightsDTO dto){
        TbJourneyEntity tbJourneyEntity = tbJourneyService.selectById(dto.getJourneyId());
        if (tbJourneyEntity == null){
            throw new RenException("行程未找到，请检查journeyId是否正确");
        }

        // 查询现有亮点
        QueryWrapper<TbHighlightsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("journey_id",dto.getJourneyId());
        List<TbHighlightsEntity> list = baseDao.selectList(wrapper);
        Integer num = list.size();

        TbHighlightsEntity entity = new TbHighlightsEntity();
        entity.setJourneyId(dto.getJourneyId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setFilePath(dto.getFilePath());
        entity.setLightSeq(num);

        baseDao.insert(entity);

    }

}