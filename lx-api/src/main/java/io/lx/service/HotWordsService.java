/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.lx.service;

import io.lx.common.service.BaseService;
import io.lx.dto.HotWordsDTO;
import io.lx.entity.HotWordsEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("UserService")
public interface HotWordsService extends BaseService<HotWordsEntity> {

	List<HotWordsDTO> getHotSearchWords();

}
