package io.lx.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.lx.common.page.PageData;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.dao.PicsDao;
import io.lx.dto.PicsDTO;
import io.lx.entity.PicsEntity;
import io.lx.service.PicsService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-11-02
 */
@Service
public class PicsServiceImpl extends CrudServiceImpl<PicsDao, PicsEntity, PicsDTO> implements PicsService {

    @Override
    public QueryWrapper<PicsEntity> getWrapper(Map<String, Object> params) {
        String id = (String) params.get("id");

        QueryWrapper<PicsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }

    @Override
    public PageData<PicsDTO> getPicsByPage(Map<String, Object> params, Integer guideId) {
        QueryWrapper<PicsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("guide_id", guideId);
        IPage<PicsEntity> page;
        page = baseDao.selectPage(getPage(params, "updated_at", false), wrapper);
        // 将结果转换为 DTO 并处理解密逻辑
        List<PicsDTO> dtoList = page.getRecords().stream().map(entity -> {
            PicsDTO dto = convertToDTO(entity);
            return dto;
        }).collect(Collectors.toList());
        return new PageData<>(dtoList, page.getTotal(), page.getSize(), page.getPages(), page.getCurrent());
    }

    // 转换实体对象到 DTO
    private PicsDTO convertToDTO(PicsEntity entity) {
        PicsDTO dto = new PicsDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }


}