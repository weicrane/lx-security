package io.lx.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.lx.common.constant.Constant;
import io.lx.common.page.PageData;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.dao.RecommendsDao;
import io.lx.dto.RecommendsDTO;
import io.lx.entity.RecommendsEntity;
import io.lx.service.RecommendsService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 首页推荐
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-12-18
 */
@Service
public class RecommendsServiceImpl extends CrudServiceImpl<RecommendsDao, RecommendsEntity, RecommendsDTO> implements RecommendsService {

    @Override
    public QueryWrapper<RecommendsEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<RecommendsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }

    @Override
    public PageData<RecommendsDTO> getRecommendsListByPage( Map<String, Object> params){
        QueryWrapper<RecommendsEntity> wrapper = new QueryWrapper<>();

        // 获取排序字段和排序方式
        String orderField = (String) params.get(Constant.ORDER_FIELD);
        String order = (String) params.get(Constant.ORDER);

        // 根据传入的排序字段和方式构造排序规则
        if ("asc".equalsIgnoreCase(order)) {
            wrapper.orderByAsc("orders");  // 按 order 字段升序排序
            wrapper.orderByDesc("updated_at");  // 按 updated_at 字段降序排序
        } else if ("desc".equalsIgnoreCase(order)) {
            wrapper.orderByDesc("orders");  // 按 order 字段降序排序
            wrapper.orderByAsc("updated_at");  // 按 updated_at 字段升序排序
        }

        // 分页查询
        IPage<RecommendsEntity> page = baseDao.selectPage(getPage(params, "updated_at", false), wrapper);

        // 构造返回数据
        List<RecommendsDTO> dtoList = page.getRecords().stream().map(entity -> {
            RecommendsDTO dto = convertToDTO(entity);
            return dto;
        }).collect(Collectors.toList());

        return new PageData<>(dtoList, page.getTotal(), page.getSize(), page.getPages(), page.getCurrent());

    }

    // 转换实体对象到 DTO
    private RecommendsDTO convertToDTO(RecommendsEntity entity) {
        RecommendsDTO dto = new RecommendsDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

}