package io.lx.modules.wxapp.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.lx.common.page.PageData;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.common.utils.ConvertUtils;
import io.lx.modules.wxapp.dao.TbSelfDrivingsDao;
import io.lx.modules.wxapp.dto.TbSelfDrivingsDTO;
import io.lx.modules.wxapp.entity.TbSelfDrivingsEntity;
import io.lx.modules.wxapp.service.TbRecommendsService;
import io.lx.modules.wxapp.service.TbSelfDrivingsService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

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
public class TbSelfDrivingsServiceImpl extends CrudServiceImpl<TbSelfDrivingsDao, TbSelfDrivingsEntity, TbSelfDrivingsDTO> implements TbSelfDrivingsService {

    @Lazy
    @Resource
    TbRecommendsService tbRecommendsService;
    @Override
    public QueryWrapper<TbSelfDrivingsEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<TbSelfDrivingsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }


    @Override
    public PageData<TbSelfDrivingsDTO> getListByPage(Map<String, Object> params, String keyword){

        // 初始化查询条件
        QueryWrapper<TbSelfDrivingsEntity> wrapper = new QueryWrapper<>();
        // 如果有 keyword，按 title 和 subtitle 模糊匹配
        if (StrUtil.isNotBlank(keyword)) {
            wrapper.like("title", keyword)
                    .or()
                    .like("sub_title", keyword);
        }

        // 执行查询
        IPage<TbSelfDrivingsEntity> page;
        page = baseDao.selectPage(getPage(params, "updated_at", false), wrapper);

        // 将结果转换为 DTO
        List<TbSelfDrivingsDTO> dtoList = page.getRecords().stream().map(entity -> {
            TbSelfDrivingsDTO dto = convertToDTO(entity);
            return dto;
        }).collect(Collectors.toList());

        return new PageData<>(dtoList, page.getTotal(),page.getSize(),page.getPages(),page.getCurrent());

    }
    // 转换实体对象到 DTO
    private TbSelfDrivingsDTO convertToDTO(TbSelfDrivingsEntity entity) {
        TbSelfDrivingsDTO dto = new TbSelfDrivingsDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    @Override
    public void update(TbSelfDrivingsDTO dto){
        TbSelfDrivingsEntity entity = ConvertUtils.sourceToTarget(dto, currentModelClass());
        baseDao.updateById(entity);

        // 更新猜你喜欢
        tbRecommendsService.updateInfo(dto.getId(),"02",dto.getTitle(),dto.getSubTitle(),dto.getCoverImgPath());
    }

    @Override
    public void onsale(TbSelfDrivingsDTO dto){
        TbSelfDrivingsEntity entity = baseDao.selectById(dto.getId());
        entity.setOnsale(dto.getOnsale());
        entity.setUpdatedAt(new Date());
        baseDao.updateById(entity);

        // 更新首页推荐
        tbRecommendsService.updateSale(dto.getId(),"02",dto.getOnsale());
    }
}