package io.lx.modules.wxapp.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.lx.common.page.PageData;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.modules.wxapp.dao.TbPicsDao;
import io.lx.modules.wxapp.dto.TbPicsDTO;
import io.lx.modules.wxapp.entity.TbPicsEntity;
import io.lx.modules.wxapp.service.TbPicsService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-11-02
 */
@Service
public class TbPicsServiceImpl extends CrudServiceImpl<TbPicsDao, TbPicsEntity, TbPicsDTO> implements TbPicsService {

    @Override
    public QueryWrapper<TbPicsEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<TbPicsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }

    @Override
    public void uploadFile(TbPicsEntity fileRecord){
        insert(fileRecord);
    }


    @Override
    public PageData<TbPicsDTO> getListByPage(Map<String, Object> params, Integer guideId){

        // 初始化查询条件
        QueryWrapper<TbPicsEntity> wrapper = new QueryWrapper<>();
        // 如果有 routeGuideId，按routeGuideId匹配
        if (guideId!=null) {
            wrapper.eq("guide_id", guideId);
        }

        // 执行查询
        IPage<TbPicsEntity> page;
        page = baseDao.selectPage(getPage(params, "updated_at", false), wrapper);

        // 将结果转换为 DTO
        List<TbPicsDTO> dtoList = page.getRecords().stream().map(entity -> {
            TbPicsDTO dto = convertToDTO(entity);
            return dto;
        }).collect(Collectors.toList());

        return new PageData<>(dtoList, page.getTotal(),page.getSize(),page.getPages(),page.getCurrent());

    }
    // 转换实体对象到 DTO
    private TbPicsDTO convertToDTO(TbPicsEntity entity) {
        TbPicsDTO dto = new TbPicsDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    public List<TbPicsDTO> getListByGuideId(Integer guideId){
        // 初始化查询条件
        QueryWrapper<TbPicsEntity> wrapper = new QueryWrapper<>();
        // 如果有 routeGuideId，按routeGuideId匹配
        if (guideId!=null) {
            wrapper.eq("guide_id", guideId);
        }
        List<TbPicsEntity> list = baseDao.selectList(wrapper);
        // 将结果转换为 DTO
        List<TbPicsDTO> dtoList = list.stream().map(entity -> {
            TbPicsDTO dto = convertToDTO(entity);
            return dto;
        }).collect(Collectors.toList());
        return dtoList;
    }

    /**
     * 删除线路相关图片记录
     * @param id
     */
    @Override
    public void deleteByRouteId(Integer id){
        QueryWrapper<TbPicsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("guide_id", id);
        List<TbPicsEntity> list = baseDao.selectList(wrapper);

        // 如果列表为空，则无需删除，直接返回
        if (list.isEmpty()) {
            return;
        }

        // 提取所有记录的主键 ID，构造一个 List
        List<Long> idList = list.stream()
                .map(TbPicsEntity::getId) // 假设主键字段为 id，请替换为实际字段名
                .collect(Collectors.toList());

        // 批量删除这些记录
        baseDao.deleteBatchIds(idList);
    }

}