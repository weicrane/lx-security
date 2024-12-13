package io.lx.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.lx.common.exception.RenException;
import io.lx.common.page.PageData;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.dao.PartnersApplyDao;
import io.lx.dto.GetApplyDTO;
import io.lx.dto.PartnersApplyDTO;
import io.lx.dto.PartnersDTO;
import io.lx.entity.PartnersApplyEntity;
import io.lx.entity.PartnersEntity;
import io.lx.entity.UserEntity;
import io.lx.service.PartnersApplyService;
import io.lx.service.PartnersService;
import io.lx.utils.OrderNumberUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.lx.constant.ApiConstant.ORDER_STATUS_NOTPAY;
import static io.lx.constant.ApiConstant.SELF_DRIVING_APPLY_CANCEL;

/**
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-31
 */
@Service
public class PartnersApplyServiceImpl extends CrudServiceImpl<PartnersApplyDao, PartnersApplyEntity, PartnersApplyDTO> implements PartnersApplyService {

    @Resource
    PartnersService partnersService;

    @Override
    public QueryWrapper<PartnersApplyEntity> getWrapper(Map<String, Object> params) {
        String id = (String) params.get("id");

        QueryWrapper<PartnersApplyEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }


    @Override
    public Map<String,String> submitApply(PartnersApplyDTO dto, UserEntity user) {

        PartnersApplyEntity entity = new PartnersApplyEntity();
        BeanUtils.copyProperties(dto, entity);
        entity.setPayStatus(ORDER_STATUS_NOTPAY); // 0-未支付

        // 生成报名单号
        String orderNum = OrderNumberUtils.generateOrderNumber();
        entity.setApplyId(orderNum);

//        entity.setStatus(SELF_DRIVING_APPLY_PENDING_REVIEW); //待审核
        entity.setUserId(user.getId());

        baseDao.insert(entity);

        Map<String,String> map = new HashMap<>();
        map.put("orderId",orderNum);
        return map;
    }

    @Override
    public PageData<PartnersApplyDTO> getPartnersApplyByPage(Map<String, Object> params, String keyword, UserEntity user) {
        // 初始化查询条件
        QueryWrapper<PartnersApplyEntity> wrapper = new QueryWrapper<>();
        // 如果有 keyword，按 title 和 subtitle 模糊匹配
        if (StrUtil.isNotBlank(keyword)) {
            wrapper.like("title", keyword);
        }
        // 添加本人条件
        wrapper.eq("user_id",user.getId());
        // 执行查询
        IPage<PartnersApplyEntity> page;
        page = baseDao.selectPage(getPage(params, "updated_at", false), wrapper);

        // 将结果转换为 DTO
        List<PartnersApplyDTO> dtoList = page.getRecords().stream().map(entity -> {
            PartnersApplyDTO dto = convertToDTO(entity);
            return dto;
        }).collect(Collectors.toList());

        return new PageData<>(dtoList, page.getTotal(), page.getSize(), page.getPages(), page.getCurrent());

    }

    // 转换实体对象到 DTO
    private PartnersApplyDTO convertToDTO(PartnersApplyEntity entity) {
        PartnersApplyDTO dto = new PartnersApplyDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    @Override
    public void cancelApply(GetApplyDTO dto, UserEntity user) {

        // 查询单号
        PartnersApplyEntity entity = baseDao.selectById(dto.getApplyId());
        // 判断单号存在
        if (entity == null) {
            throw new RenException("订单不存在");
        }
        // 判断是否本人
        if (!user.getId().equals(entity.getUserId())) {
            throw new RenException("订单号与用户不符");
        }

        // 更新状态
        entity.setStatus(SELF_DRIVING_APPLY_CANCEL); // 已取消

        baseDao.updateById(entity);

    }

    @Override
    public Map<String, Object> getApplyDetailById(String applyId,UserEntity user){
        // 1.根据id查询预约申请单详情
        // 查询单号
        PartnersApplyEntity entity = baseDao.selectById(applyId);
        // 判断单号存在
        if (entity == null) {
            throw new RenException("订单不存在");
        }
        // 判断是否本人
        if (!user.getId().equals(entity.getUserId())) {
            throw new RenException("订单号与用户不符");
        }
        PartnersApplyDTO applyDTO = new PartnersApplyDTO();
        BeanUtils.copyProperties(entity, applyDTO);

        // 2.查询酒店详情
        PartnersEntity partnersEntity = partnersService.getPartnersDetailById(entity.getPartnersId());
        PartnersDTO partnersDTO = new PartnersDTO();
        BeanUtils.copyProperties(partnersEntity, partnersDTO);

        // 3.组装报文
        Map<String,Object> map = new HashMap<>();
        map.put("applyDetail",applyDTO);
        map.put("partnersDetail",partnersDTO);

        return map;
    }

    @Override
    public void updatePayStatus(String orderId,String status){

        // 查询单号-orderid与单号一致
        PartnersApplyEntity entity = baseDao.selectById(orderId);
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

}