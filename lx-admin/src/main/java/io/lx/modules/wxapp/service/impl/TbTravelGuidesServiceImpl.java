package io.lx.modules.wxapp.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.common.utils.AESUtils;
import io.lx.modules.wxapp.dao.TbTravelGuidesDao;
import io.lx.modules.wxapp.dto.TbTravelGuidesDTO;
import io.lx.modules.wxapp.entity.TbTravelGuidesEntity;
import io.lx.modules.wxapp.service.TbTravelGuidesService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-05
 */
@Service
public class TbTravelGuidesServiceImpl extends CrudServiceImpl<TbTravelGuidesDao, TbTravelGuidesEntity, TbTravelGuidesDTO> implements TbTravelGuidesService {

    @Override
    public QueryWrapper<TbTravelGuidesEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<TbTravelGuidesEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }


    @Override
    public void encryptSave(TbTravelGuidesDTO tbTravelGuidesDTO){
        String asset = tbTravelGuidesDTO.getAsset();
        TbTravelGuidesEntity tbTravelGuidesEntity =new TbTravelGuidesEntity();
        BeanUtils.copyProperties(tbTravelGuidesDTO,tbTravelGuidesEntity);
        try {
            tbTravelGuidesEntity.setAsset(AESUtils.encrypt(asset));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        baseDao.insert(tbTravelGuidesEntity);
    }
}