package io.lx.modules.wxapp.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.common.utils.ConvertUtils;
import io.lx.modules.wxapp.dao.TbPartnersDao;
import io.lx.modules.wxapp.dto.TbPartnersDTO;
import io.lx.modules.wxapp.entity.TbPartnersEntity;
import io.lx.modules.wxapp.service.TbPartnersService;
import io.lx.modules.wxapp.service.TbRecommendsService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-31
 */
@Service
public class TbPartnersServiceImpl extends CrudServiceImpl<TbPartnersDao, TbPartnersEntity, TbPartnersDTO> implements TbPartnersService {

    @Lazy
    @Resource
    TbRecommendsService tbRecommendsService;
    @Override
    public QueryWrapper<TbPartnersEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<TbPartnersEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }

    @Override
    public void update(TbPartnersDTO dto){
        TbPartnersEntity entity = ConvertUtils.sourceToTarget(dto, currentModelClass());
        baseDao.updateById(entity);

        // 更新猜你喜欢
        tbRecommendsService.updateInfo(dto.getId(),"04",dto.getTitle(),dto.getSubTitle(),dto.getCoverImgPath());
    }

    @Override
    @Transactional
    public void onsale(TbPartnersDTO dto){
        TbPartnersEntity entity = baseDao.selectById(dto.getId());
        entity.setOnsale(dto.getOnsale());
        entity.setUpdatedAt(new Date());
        baseDao.updateById(entity);

        // 更新首页推荐
        tbRecommendsService.updateSale(dto.getId(),"04",dto.getOnsale());
    }
}