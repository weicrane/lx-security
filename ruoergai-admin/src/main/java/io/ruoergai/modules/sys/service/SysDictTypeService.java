/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.ruoergai.modules.sys.service;

import io.ruoergai.common.page.PageData;
import io.ruoergai.common.service.BaseService;
import io.ruoergai.modules.sys.dto.SysDictTypeDTO;
import io.ruoergai.modules.sys.entity.DictType;
import io.ruoergai.modules.sys.entity.SysDictTypeEntity;

import java.util.List;
import java.util.Map;

/**
 * 数据字典
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface SysDictTypeService extends BaseService<SysDictTypeEntity> {

    PageData<SysDictTypeDTO> page(Map<String, Object> params);

    SysDictTypeDTO get(Long id);

    void save(SysDictTypeDTO dto);

    void update(SysDictTypeDTO dto);

    void delete(Long[] ids);

    /**
     * 获取所有字典
     */
    List<DictType> getAllList();

}