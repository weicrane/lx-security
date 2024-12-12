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

import java.math.BigDecimal;
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

    @Override
    public SelfDrivingsEntity getPartnersDetailById(Integer id){
        SelfDrivingsEntity entity = baseDao.selectById(id);
        return entity;
    }

    @Override
    public BigDecimal calculateTotalAmount(Integer num1,
                                           Integer num2,
                                           Integer num3,Integer productId){
        SelfDrivingsEntity entity = baseDao.selectById(productId);
        BigDecimal price1 = entity.getPrice1();
        BigDecimal price2 = entity.getPrice2();
        BigDecimal price3 = entity.getPrice3();

        // 初始化总金额为 0
        BigDecimal totalAmount = BigDecimal.ZERO;
        // 计算商品1的金额
        if (price1 != null && num1 != null && num1 > 0) {
            totalAmount = totalAmount.add(price1.multiply(BigDecimal.valueOf(num1)));
        }

        // 计算商品2的金额
        if (price2 != null && num2 != null && num2 > 0) {
            totalAmount = totalAmount.add(price2.multiply(BigDecimal.valueOf(num2)));
        }

        // 计算商品3的金额
        if (price3 != null && num3 != null && num3 > 0) {
            totalAmount = totalAmount.add(price3.multiply(BigDecimal.valueOf(num3)));
        }
        return totalAmount;
    }


}