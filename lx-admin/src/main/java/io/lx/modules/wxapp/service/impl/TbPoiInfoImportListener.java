package io.lx.modules.wxapp.service.impl;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import io.lx.modules.wxapp.entity.TbPoiInfoEntity;

import java.util.ArrayList;
import java.util.List;

public class TbPoiInfoImportListener extends AnalysisEventListener<TbPoiInfoEntity> {

    private final List<TbPoiInfoEntity> poiInfoList = new ArrayList<>();

    @Override
    public void invoke(TbPoiInfoEntity entity, AnalysisContext context) {
        poiInfoList.add(entity); // 收集读取的行数据
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("所有数据解析完成，共读取：" + poiInfoList.size() + " 条数据");
    }

    public List<TbPoiInfoEntity> getPoiInfoList() {
        return poiInfoList;
    }
}
