package io.lx.modules.wxapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.exception.RenException;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.modules.wxapp.dao.TbRoutesGuidesDao;
import io.lx.modules.wxapp.dto.TbRoutesGuidesDTO;
import io.lx.modules.wxapp.entity.TbRoutesGuidesEntity;
import io.lx.modules.wxapp.service.*;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-19
 */
@Service
public class TbRoutesGuidesServiceImpl extends CrudServiceImpl<TbRoutesGuidesDao, TbRoutesGuidesEntity, TbRoutesGuidesDTO> implements TbRoutesGuidesService {


    @Resource
    TbUserMembershipsService tbUserMembershipsService;
    @Resource
    TbJourneyService tbJourneyService;
    @Resource
    TbPoiInfoService tbPoiInfoService;
    @Resource
    TbPicsService tbPicsService;

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

}