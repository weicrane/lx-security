package io.lx.modules.wxapp.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.page.PageData;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.modules.wxapp.dao.TbOrdersDao;
import io.lx.modules.wxapp.dto.TbOrdersDTO;
import io.lx.modules.wxapp.entity.TbOrdersEntity;
import io.lx.modules.wxapp.service.TbOrdersService;
import io.lx.modules.wxapp.service.TbUserService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-26
 */
@Service
public class TbOrdersServiceImpl extends CrudServiceImpl<TbOrdersDao, TbOrdersEntity, TbOrdersDTO> implements TbOrdersService {

    @Lazy
    @Resource
    private TbUserService tbUserService;
    @Override
    public QueryWrapper<TbOrdersEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<TbOrdersEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }

    /**
     * 分页查询订单
     * @param params
     * @return
     */
    @Override
    public PageData<TbOrdersDTO> getOrderListByPage(Map<String, Object> params){
        QueryWrapper<TbOrdersEntity> wrapper = new QueryWrapper<>();

        // TODO:未完成
        // 1.手机号条件处理：先获取用户id，根据id筛选
//        tbUserService.getUserEntity()
        return  null;
    }


}