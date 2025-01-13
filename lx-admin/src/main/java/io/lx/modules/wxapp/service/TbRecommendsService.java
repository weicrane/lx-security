package io.lx.modules.wxapp.service;

import io.lx.common.page.PageData;
import io.lx.common.service.CrudService;
import io.lx.modules.wxapp.dto.TbRecommendsDTO;
import io.lx.modules.wxapp.entity.TbRecommendsEntity;

import java.util.Map;

/**
 * 首页推荐
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-12-18
 */
public interface TbRecommendsService extends CrudService<TbRecommendsEntity, TbRecommendsDTO> {
     PageData<TbRecommendsEntity> getTbRecommendsListByPage(Map<String, Object> params);

     void save(TbRecommendsDTO dto);

     void deleteByIdAndType(Integer id,String type);

     /**
      * 更新数据
      */
     void updateInfo(Integer id,String type,String title,String subTitle,String coverImgPath);

}