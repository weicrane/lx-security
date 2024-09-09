/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.lx.modules.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.lx.common.constant.Constant;
import io.lx.common.page.PageData;
import io.lx.common.service.impl.BaseServiceImpl;
import io.lx.common.utils.ConvertUtils;
import io.lx.modules.job.dao.ScheduleJobLogDao;
import io.lx.modules.job.dto.ScheduleJobLogDTO;
import io.lx.modules.job.entity.ScheduleJobLogEntity;
import io.lx.modules.job.service.ScheduleJobLogService;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ScheduleJobLogServiceImpl extends BaseServiceImpl<ScheduleJobLogDao, ScheduleJobLogEntity> implements ScheduleJobLogService {

	@Override
	public PageData<ScheduleJobLogDTO> page(Map<String, Object> params) {
		IPage<ScheduleJobLogEntity> page = baseDao.selectPage(
			getPage(params, Constant.CREATE_DATE, false),
			getWrapper(params)
		);
		return getPageData(page, ScheduleJobLogDTO.class);
	}

	private QueryWrapper<ScheduleJobLogEntity> getWrapper(Map<String, Object> params){
		String jobId = (String)params.get("jobId");

		QueryWrapper<ScheduleJobLogEntity> wrapper = new QueryWrapper<>();
		wrapper.eq(StrUtil.isNotBlank(jobId), "job_id", jobId);

		return wrapper;
	}

	@Override
	public ScheduleJobLogDTO get(Long id) {
		ScheduleJobLogEntity entity = baseDao.selectById(id);

		return ConvertUtils.sourceToTarget(entity, ScheduleJobLogDTO.class);
	}

}