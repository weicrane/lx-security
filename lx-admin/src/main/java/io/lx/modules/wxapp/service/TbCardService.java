package io.lx.modules.wxapp.service;

import io.lx.common.page.PageData;
import io.lx.common.service.CrudService;
import io.lx.modules.wxapp.dto.GenCardsDTO;
import io.lx.modules.wxapp.dto.TbCardDTO;
import io.lx.modules.wxapp.entity.TbCardEntity;

import java.util.Map;

/**
 * 卡密表
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2025-03-16
 */
public interface TbCardService extends CrudService<TbCardEntity, TbCardDTO> {

    /**
     * 生成卡密
     * @param dto
     */
    void genCards(GenCardsDTO dto);

    /**
     * 修改卡密
     * @param dto
     */
    void editCard(TbCardDTO dto);

    /**
     * 搜索卡密列表-分页
     */
    PageData<TbCardDTO> searchByPage(Map<String, Object> params);
}