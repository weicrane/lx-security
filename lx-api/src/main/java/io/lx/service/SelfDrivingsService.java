package io.lx.service;

import io.lx.common.page.PageData;
import io.lx.common.service.CrudService;
import io.lx.dto.SelfDrivingsDTO;
import io.lx.entity.SelfDrivingsEntity;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-19
 */
public interface SelfDrivingsService extends CrudService<SelfDrivingsEntity, SelfDrivingsDTO> {

    PageData<SelfDrivingsDTO> getSelfDrivingListByPage(Map<String, Object> params,String keyword);

    /**
     * 查询自驾活动详情
     * @param id
     * @return
     */
    SelfDrivingsEntity getPartnersDetailById(Integer id);

    BigDecimal calculateTotalAmount(Integer num1,
                                    Integer num2,
                                    Integer num3,
                                    Integer productId);
}