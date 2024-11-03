package io.lx.modules.wxapp.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.modules.wxapp.dao.KmlRecordDao;
import io.lx.modules.wxapp.dto.KmlRecordDTO;
import io.lx.modules.wxapp.entity.KmlRecordEntity;
import io.lx.modules.wxapp.service.KmlRecordService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-11-02
 */
@Service
public class KmlRecordServiceImpl extends CrudServiceImpl<KmlRecordDao, KmlRecordEntity, KmlRecordDTO> implements KmlRecordService {

    @Override
    public QueryWrapper<KmlRecordEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<KmlRecordEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }

    @Override
    public void uploadFile(KmlRecordEntity fileRecord){
        insert(fileRecord);
    }

}