package io.lx.modules.wxapp.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.exception.RenException;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.common.utils.ConvertUtils;
import io.lx.modules.wxapp.dao.TbRoutesGuidesDao;
import io.lx.modules.wxapp.dto.TbRoutesGuidesDTO;
import io.lx.modules.wxapp.entity.TbRoutesGuidesEntity;
import io.lx.modules.wxapp.service.*;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-19
 */
@Service
public class TbRoutesGuidesServiceImpl extends CrudServiceImpl<TbRoutesGuidesDao, TbRoutesGuidesEntity, TbRoutesGuidesDTO> implements TbRoutesGuidesService {


    @Lazy
    @Resource
    TbUserMembershipsService tbUserMembershipsService;
    @Resource
    TbJourneyService tbJourneyService;
    @Resource
    TbPoiInfoService tbPoiInfoService;
    @Resource
    TbPicsService tbPicsService;
    @Lazy
    @Resource
    TbRecommendsService tbRecommendsService;

    @Override
    public QueryWrapper<TbRoutesGuidesEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<TbRoutesGuidesEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }

    @Override
    public TbRoutesGuidesEntity selectById(Integer id){
        TbRoutesGuidesEntity entity = baseDao.selectById(id);
        return entity;
    }

    @Override
    public void onsale(TbRoutesGuidesDTO dto){
        TbRoutesGuidesEntity entity = baseDao.selectById(dto.getId());
        entity.setOnsale(dto.getOnsale());
        entity.setUpdatedAt(new Date());
        baseDao.updateById(entity);
    }

    /**
     *
     * @param id
     * 若有人购买了，不许删除，只可下架
     * 1.查询线路信息
     * 2.查询主线、小众行程
     * 3.查询关联poi
     * 4.查询图片墙
     * 5.依次删除
     */
    @Override
    @Transactional
    public void deleteById(Integer id){
        TbRoutesGuidesEntity entity = baseDao.selectById(id);
        if (entity==null){
            throw new RenException("未找到线路信息");
        }

        if (tbUserMembershipsService.isSold(id)){
            throw new RenException("已有客户购买，无法删除，请下架");
        }

        tbJourneyService.deleteByRouteId(id); //删除行程
        tbPoiInfoService.deleteByRouteId(id); //删除poi
        tbPicsService.deleteByRouteId(id); //删除图片

        baseDao.deleteById(id); //最后删除线路
    }

    @Override
    public void update(TbRoutesGuidesDTO dto){
        TbRoutesGuidesEntity entity = ConvertUtils.sourceToTarget(dto, currentModelClass());
        baseDao.updateById(entity);

        // 更新猜你喜欢
        tbRecommendsService.updateInfo(dto.getId(),"03",dto.getTitle(),dto.getSubTitle(),dto.getCoverImgPath());
    }

    @Override
    public List<TbRoutesGuidesDTO> getAllRoutesList(){
        QueryWrapper<TbRoutesGuidesEntity> wrapper = new QueryWrapper<>();
        List<TbRoutesGuidesEntity> entityList = baseDao.selectList(wrapper) ;
        // 判断 entityList 是否为空，避免 NPE
        if (entityList == null || entityList.isEmpty()) {
            return List.of(); // 返回空列表，避免返回 null
        }
        // 转换为 DTO 列表并返回
        return entityList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    // 转换实体对象到 DTO
    private TbRoutesGuidesDTO convertToDTO(TbRoutesGuidesEntity entity) {
        TbRoutesGuidesDTO dto = new TbRoutesGuidesDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

}