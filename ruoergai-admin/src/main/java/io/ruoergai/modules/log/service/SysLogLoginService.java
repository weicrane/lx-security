/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.ruoergai.modules.log.service;

import io.ruoergai.common.page.PageData;
import io.ruoergai.common.service.BaseService;
import io.ruoergai.modules.log.dto.SysLogLoginDTO;
import io.ruoergai.modules.log.entity.SysLogLoginEntity;

import java.util.List;
import java.util.Map;

/**
 * 登录日志
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0
 */
public interface SysLogLoginService extends BaseService<SysLogLoginEntity> {

    PageData<SysLogLoginDTO> page(Map<String, Object> params);

    List<SysLogLoginDTO> list(Map<String, Object> params);

    void save(SysLogLoginEntity entity);
}