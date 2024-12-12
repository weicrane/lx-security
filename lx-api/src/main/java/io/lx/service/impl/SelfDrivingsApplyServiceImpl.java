package io.lx.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.lx.common.exception.RenException;
import io.lx.common.page.PageData;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.dao.SelfDrivingsApplyDao;
import io.lx.dto.GetApplyDTO;
import io.lx.dto.SelfDrivingsApplyDTO;
import io.lx.dto.SelfDrivingsDTO;
import io.lx.entity.SelfDrivingsApplyEntity;
import io.lx.entity.SelfDrivingsEntity;
import io.lx.entity.UserEntity;
import io.lx.service.SelfDrivingsApplyService;
import io.lx.service.SelfDrivingsService;
import io.lx.utils.OrderNumberUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.lx.constant.ApiConstant.*;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-29
 */
@Service
public class SelfDrivingsApplyServiceImpl extends CrudServiceImpl<SelfDrivingsApplyDao, SelfDrivingsApplyEntity, SelfDrivingsApplyDTO> implements SelfDrivingsApplyService {

    @Resource
    SelfDrivingsService selfDrivingsService;

    @Override
    public QueryWrapper<SelfDrivingsApplyEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<SelfDrivingsApplyEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }


    @Override
    public Map<String,String> submitApply(SelfDrivingsApplyDTO dto, UserEntity user){

        SelfDrivingsApplyEntity entity = new SelfDrivingsApplyEntity();
        BeanUtils.copyProperties(dto,entity);
        entity.setPayStatus(ORDER_STATUS_NOTPAY); // 0-未支付

        // 生成报名单号
        String orderNum = OrderNumberUtils.generateOrderNumber();
        entity.setApplyId(orderNum);
        entity.setOrderId(orderNum);

//        entity.setStatus(SELF_DRIVING_APPLY_PENDING_REVIEW); //待审核
        entity.setUserId(user.getId());

        baseDao.insert(entity);

        Map<String,String> map = new HashMap<>();
        map.put("orderId",orderNum);
        return map;

    }

    @Override
    public PageData<SelfDrivingsApplyDTO> getSelfDriApplyByPage(Map<String, Object> params, String keyword, UserEntity user){
        // 初始化查询条件
        QueryWrapper<SelfDrivingsApplyEntity> wrapper = new QueryWrapper<>();
        // 如果有 keyword，按 title 和 subtitle 模糊匹配
        if (StrUtil.isNotBlank(keyword)) {
            wrapper.like("title", keyword);
        }
        // 添加本人条件
        wrapper.eq("user_id",user.getId());
        // 执行查询
        IPage<SelfDrivingsApplyEntity> page ;
        page = baseDao.selectPage(getPage(params, "updated_at", false), wrapper);

        // 将结果转换为 DTO
        List<SelfDrivingsApplyDTO> dtoList = page.getRecords().stream().map(entity -> {
            SelfDrivingsApplyDTO dto = convertToDTO(entity);
            return dto;
        }).collect(Collectors.toList());

        return new PageData<>(dtoList, page.getTotal(),page.getSize(),page.getPages(),page.getCurrent());

    }
    // 转换实体对象到 DTO
    private SelfDrivingsApplyDTO convertToDTO(SelfDrivingsApplyEntity entity) {
        SelfDrivingsApplyDTO dto = new SelfDrivingsApplyDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    @Override
    public void cancelApply(GetApplyDTO dto, UserEntity user){

        // 查询单号
        SelfDrivingsApplyEntity entity = baseDao.selectById(dto.getApplyId());
        // 判断单号存在
        if (entity == null) {
            throw new RenException("订单不存在");
        }
        // 判断是否本人
        if (!user.getId().equals(entity.getUserId())){
            throw new RenException("订单号与用户不符");
        }

        // 更新状态
        entity.setStatus(SELF_DRIVING_APPLY_CANCEL); // 已取消

        baseDao.updateById(entity);

    }

    @Override
    public Map<String, Object> getApplyDetailById(String applyId,UserEntity user){
        // 1.根据id查询报名单详情
        // 查询单号
        SelfDrivingsApplyEntity entity = baseDao.selectById(applyId);
        // 判断单号存在
        if (entity == null) {
            throw new RenException("订单不存在");
        }
        // 判断是否本人
        if (!user.getId().equals(entity.getUserId())) {
            throw new RenException("订单号与用户不符");
        }
        SelfDrivingsApplyDTO applyDTO = new SelfDrivingsApplyDTO();
        BeanUtils.copyProperties(entity, applyDTO);

        // 2.查询活动详情
        SelfDrivingsEntity partnersEntity = selfDrivingsService.getPartnersDetailById(entity.getSelfDrivingId());
        SelfDrivingsDTO partnersDTO = new SelfDrivingsDTO();
        BeanUtils.copyProperties(partnersEntity, partnersDTO);

        // 3.组装报文
        Map<String,Object> map = new HashMap<>();
        map.put("applyDetail",applyDTO);
        map.put("selfDrivingsDetail",partnersDTO);

        return map;
    }

    @Override
    public void updatePayStatus(String orderId,String status){

        // 查询单号-orderid与单号一致
        SelfDrivingsApplyEntity entity = baseDao.selectById(orderId);
        // 判断单号存在
        if (entity == null) {
            throw new RenException("订单不存在");
        }

        // 更新状态
        entity.setStatus(status);
        // 更新状态
        entity.setPayStatus(status);

        baseDao.updateById(entity);

    }

    @Override
    public SelfDrivingsApplyEntity getSelfDrivingsApply(String applyId){
        // 查询单号
        SelfDrivingsApplyEntity entity = baseDao.selectById(applyId);
        return entity;
    }
}