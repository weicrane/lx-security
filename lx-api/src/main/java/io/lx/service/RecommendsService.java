package io.lx.service;

import io.lx.common.page.PageData;
import io.lx.common.service.CrudService;
import io.lx.dto.RecommendsDTO;
import io.lx.entity.RecommendsEntity;

import java.util.Map;

/**
 * 首页推荐
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-12-18
 */
public interface RecommendsService extends CrudService<RecommendsEntity, RecommendsDTO> {


    PageData<RecommendsDTO> getRecommendsListByPage(Map<String, Object> params);

}