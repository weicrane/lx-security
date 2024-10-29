package io.lx.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.lx.common.page.PageData;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.dao.SelfDrivingsDao;
import io.lx.dto.SelfDrivingsDTO;
import io.lx.entity.SelfDrivingsEntity;
import io.lx.service.SelfDrivingsService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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
public class SelfDrivingsServiceImpl extends CrudServiceImpl<SelfDrivingsDao, SelfDrivingsEntity, SelfDrivingsDTO> implements SelfDrivingsService {

    @Override
    public QueryWrapper<SelfDrivingsEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<SelfDrivingsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }

    @Override
    public PageData<SelfDrivingsDTO> getSelfDrivingListByPage(Map<String, Object> params, String keyword){

        // 初始化查询条件
        QueryWrapper<SelfDrivingsEntity> wrapper = new QueryWrapper<>();
        // 如果有 keyword，按 title 和 subtitle 模糊匹配
        if (StrUtil.isNotBlank(keyword)) {
            wrapper.like("title", keyword)
                    .or()
                    .like("sub_title", keyword);
        }

        // 执行查询
        IPage<SelfDrivingsEntity> page;
        page = baseDao.selectPage(getPage(params, "updated_at", false), wrapper);

        // 将结果转换为 DTO
        List<SelfDrivingsDTO> dtoList = page.getRecords().stream().map(entity -> {
            SelfDrivingsDTO dto = convertToDTO(entity);
            return dto;
        }).collect(Collectors.toList());

        return new PageData<>(dtoList, page.getTotal(),page.getSize(),page.getPages(),page.getCurrent());

    }
    // 转换实体对象到 DTO
    private SelfDrivingsDTO convertToDTO(SelfDrivingsEntity entity) {
        SelfDrivingsDTO dto = new SelfDrivingsDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }


}