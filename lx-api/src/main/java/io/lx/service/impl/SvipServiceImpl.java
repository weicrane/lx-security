package io.lx.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.lx.common.exception.RenException;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.dto.SvipDTO;
import io.lx.dto.SvipDao;
import io.lx.entity.SvipEntity;
import io.lx.service.SvipService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-27
 */
@Service
public class SvipServiceImpl extends CrudServiceImpl<SvipDao, SvipEntity, SvipDTO> implements SvipService {

    @Override
    public QueryWrapper<SvipEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<SvipEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }
    @Override
    public  BigDecimal getSvipPrice(){
        try {
            SvipEntity entity = baseDao.selectById(0);
            return entity.getPrice();
        }catch (Exception e) {
            throw new RenException("查询会员价格失败");
        }

    }


}