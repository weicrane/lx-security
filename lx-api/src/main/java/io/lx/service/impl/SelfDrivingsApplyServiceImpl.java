package io.lx.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.dao.SelfDrivingsApplyDao;
import io.lx.dto.SelfDrivingsApplyDTO;
import io.lx.entity.SelfDrivingsApplyEntity;
import io.lx.entity.UserEntity;
import io.lx.service.SelfDrivingsApplyService;
import io.lx.utils.OrderNumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

import static io.lx.constant.ApiConstant.SELF_DRIVING_APPLY_PENDING_REVIEW;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-29
 */
@Service
public class SelfDrivingsApplyServiceImpl extends CrudServiceImpl<SelfDrivingsApplyDao, SelfDrivingsApplyEntity, SelfDrivingsApplyDTO> implements SelfDrivingsApplyService {

    @Override
    public QueryWrapper<SelfDrivingsApplyEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<SelfDrivingsApplyEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }


    @Override
    public void submitApply(SelfDrivingsApplyDTO dto, UserEntity user){

        SelfDrivingsApplyEntity entity = new SelfDrivingsApplyEntity();
        BeanUtils.copyProperties(dto,entity);

        // 生成报名单号
        String orderNum = OrderNumberUtils.generateOrderNumber();
        entity.setApplyId(orderNum);

        entity.setStatus(SELF_DRIVING_APPLY_PENDING_REVIEW); //待审核
        entity.setUserId(user.getId());

        baseDao.insert(entity);

    }

}