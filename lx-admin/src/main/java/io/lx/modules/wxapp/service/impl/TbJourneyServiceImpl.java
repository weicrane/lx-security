package io.lx.modules.wxapp.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.lx.common.page.PageData;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.modules.wxapp.dao.TbJourneyDao;
import io.lx.modules.wxapp.dto.SubmitKmlDTO;
import io.lx.modules.wxapp.dto.TbJourneyDTO;
import io.lx.modules.wxapp.entity.TbJourneyEntity;
import io.lx.modules.wxapp.service.TbHighlightsService;
import io.lx.modules.wxapp.service.TbJourneyService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.lx.common.constant.AdminConstant.JOURNEY_TYPE_MAIN_ALL;
import static io.lx.common.constant.AdminConstant.JOURNEY_TYPE_OTHER_ALL;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-11-02
 */
@Service
public class TbJourneyServiceImpl extends CrudServiceImpl<TbJourneyDao, TbJourneyEntity, TbJourneyDTO> implements TbJourneyService {

//    @Resource
//    TbRoutesGuidesService tbRoutesGuidesService;

    @Resource
    TbHighlightsService tbHighlightsService;

    @Override
    public QueryWrapper<TbJourneyEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<TbJourneyEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }


    @Override
    public void submitKml(SubmitKmlDTO dto){
        // 判断是新增还是修改
        QueryWrapper<TbJourneyEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("guide_id",dto.getGuideId());
        wrapper.eq("journey_type",dto.getJourneyType());
        List<TbJourneyEntity>  list = baseDao.selectList(wrapper);

        TbJourneyEntity entity = new TbJourneyEntity();

        if (list.isEmpty()){
            // 表里不存在，表示新增
            if (JOURNEY_TYPE_MAIN_ALL.equals(dto.getJourneyType())){
                // 主线总览
                entity.setName("主线总览");
            }else if (JOURNEY_TYPE_OTHER_ALL.equals(dto.getJourneyType())){
                // 小众总览
                entity.setName("小众玩法总览");
            }
            entity.setKmlPath(dto.getKmlPath());
            entity.setJourneyType(dto.getJourneyType());
            entity.setDateId("0");
            entity.setGuideId(dto.getGuideId());

            baseDao.insert(entity);
        }else {
            // 已存在，修改
            BeanUtils.copyProperties(list.get(0),entity);
            entity.setKmlPath(dto.getKmlPath());
            baseDao.updateById(entity);
        }

    }

    /**
     * 获取kml信息
     * @param guideId
     * @return
     */
    public Map<String,Object> getKmlInfo(Integer guideId){

        // 查询主线
        QueryWrapper<TbJourneyEntity> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("guide_id",guideId);
        wrapper1.eq("journey_type",JOURNEY_TYPE_MAIN_ALL);
        List<TbJourneyEntity>  entityList1 = baseDao.selectList(wrapper1);

        // 查询小众
        QueryWrapper<TbJourneyEntity> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("guide_id",guideId);
        wrapper2.eq("journey_type",JOURNEY_TYPE_OTHER_ALL);
        List<TbJourneyEntity>  entityList2 = baseDao.selectList(wrapper2);

        // 构造返回结果
        Map<String,Object> map = new HashMap<>();

        if (!entityList1.isEmpty() && StrUtil.isNotBlank(
                entityList1.get(0).getKmlPath()
        )){
            map.put("mainKmlPath",entityList1.get(0).getKmlPath());
        }else {
            map.put("mainKmlPath","");
        }

        if (!entityList2.isEmpty() && StrUtil.isNotBlank(
                entityList2.get(0).getKmlPath()
        )){
            map.put("otherKmlPath",entityList2.get(0).getKmlPath());
        }else {
            map.put("otherKmlPath","");
        }

            return map;
    }

    @Override
    public PageData<TbJourneyDTO> selectPage(Map<String, Object> params, Integer guideId,Integer id){

        // 初始化查询条件
        QueryWrapper<TbJourneyEntity> wrapper = new QueryWrapper<>();
        // 如果有线路id
        if (guideId!= null) {
            wrapper.eq("guide_id", guideId);
        }

        // 如果有id
        if (id!= null) {
            wrapper.eq("id", id);
        }

        // 只查询journeyType in ("1","2")
        wrapper.in("journey_type", Arrays.asList("1", "2"));

        // 按行程顺序排序
        wrapper.orderByAsc("date_id");
        // 执行查询
        IPage<TbJourneyEntity> page;
        page = baseDao.selectPage(getPage(params, "updated_at", false), wrapper);

        // 将结果转换为 DTO
        List<TbJourneyDTO> dtoList = page.getRecords().stream().map(entity -> {
            TbJourneyDTO dto = convertToDTO(entity);
            return dto;
        }).collect(Collectors.toList());

        return new PageData<>(dtoList, page.getTotal(),page.getSize(),page.getPages(),page.getCurrent());

    }
    // 转换实体对象到 DTO
    private TbJourneyDTO convertToDTO(TbJourneyEntity entity) {
        TbJourneyDTO dto = new TbJourneyDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    /**
     * 新增行程
     * 1.判断线路id是否存在
     * 2.查询已有多少条
     * @param dto
     */
    @Override
    public void submitJourney(TbJourneyDTO dto){
//        TbRoutesGuidesEntity TbRoutesGuidesEntity = tbRoutesGuidesService.selectById(dto.getGuideId());
//        if (TbRoutesGuidesEntity == null){
//            throw new RenException("线路未找到，请检查线路id是否正确");
//        }
        // 查询现有行程
        QueryWrapper<TbJourneyEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("guide_id",dto.getGuideId());
        wrapper.eq("journey_type",dto.getJourneyType());
        List<TbJourneyEntity> list = baseDao.selectList(wrapper);
        Integer num = list.size() + 1;
        String dateId = num.toString();

        TbJourneyEntity entity = new TbJourneyEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setJourneyType(dto.getJourneyType());
        entity.setGuideId(dto.getGuideId());
        entity.setIntro(dto.getIntro());
        entity.setDateId(dateId);
        baseDao.insert(entity);
    }

    @Override
    public TbJourneyEntity selectById(Integer id){
        TbJourneyEntity entity = baseDao.selectById(id);
        return entity;
    }

    /**
     * 删除线路相关
     * @param id
     */
    @Override
    @Transactional
    public void deleteByRouteId(Integer id){

        QueryWrapper<TbJourneyEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("guide_id", id);
        List<TbJourneyEntity> list = baseDao.selectList(wrapper);

        // 如果列表为空，则无需删除，直接返回
        if (list.isEmpty()) {
            return;
        }

        // 提取所有记录的主键 ID，构造一个 List
        List<Integer> idList = list.stream()
                .map(TbJourneyEntity::getId) // 假设主键字段为 id，请替换为实际字段名
                .collect(Collectors.toList());

        idList.forEach(tbHighlightsService::deleteByJourneyId);

        // 批量删除这些记录
        baseDao.deleteBatchIds(idList);
    }


}