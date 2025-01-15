package io.lx.modules.wxapp.service;

import io.lx.common.page.PageData;
import io.lx.common.service.CrudService;
import io.lx.modules.wxapp.dto.TbPoiInfoDTO;
import io.lx.modules.wxapp.entity.TbPoiInfoEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-11-02
 */
public interface TbPoiInfoService extends CrudService<TbPoiInfoEntity, TbPoiInfoDTO> {

    PageData<TbPoiInfoDTO> selectPage(Map<String, Object> params, Integer guidesId, String dateId, String journeyType);

    /**
     * 批量导入Poi数据
     * @param file
     * @throws Exception
     */
    void importPoiXlsx(MultipartFile file) throws Exception;

    /**
     * 删除线路相关点
     * @param id
     */
    void deleteByRouteId(Integer id);

}