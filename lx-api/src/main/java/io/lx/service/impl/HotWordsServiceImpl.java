
package io.lx.service.impl;

import io.lx.common.service.impl.BaseServiceImpl;
import io.lx.common.utils.ConvertUtils;
import io.lx.dao.HotWordsDao;
import io.lx.dto.HotWordsDTO;
import io.lx.entity.HotWordsEntity;
import io.lx.service.HotWordsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HotWordsServiceImpl extends BaseServiceImpl<HotWordsDao, HotWordsEntity> implements HotWordsService {

    @Override
    public List<HotWordsDTO> getHotSearchWords() {
        List<HotWordsEntity> list = baseDao.selectList(null);
        return ConvertUtils.sourceToTarget(list,HotWordsDTO.class);
    }

}