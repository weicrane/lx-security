package io.lx.modules.wxapp.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.lx.common.exception.RenException;
import io.lx.common.page.PageData;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.common.utils.CoordinateTransformUtils;
import io.lx.common.utils.ExcelUtils;
import io.lx.modules.wxapp.dao.TbPoiInfoDao;
import io.lx.modules.wxapp.dto.TbPoiInfoDTO;
import io.lx.modules.wxapp.entity.PoiExcelEntity;
import io.lx.modules.wxapp.entity.TbPoiInfoEntity;
import io.lx.modules.wxapp.service.TbPoiInfoService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 服务实现类
 */
@Service
public class TbPoiInfoServiceImpl extends CrudServiceImpl<TbPoiInfoDao, TbPoiInfoEntity, TbPoiInfoDTO> implements TbPoiInfoService {

    @Resource
    private TbPoiInfoDao tbPoiInfoDao;

    @Override
    public QueryWrapper<TbPoiInfoEntity> getWrapper(Map<String, Object> params) {
        String id = (String) params.get("id");

        QueryWrapper<TbPoiInfoEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(id), "id", id);

        return wrapper;
    }


    @Override
    public PageData<TbPoiInfoDTO> selectPage(Map<String, Object> params, Integer guidesId, String dateId, String journeyType){

        // 初始化查询条件
        QueryWrapper<TbPoiInfoEntity> wrapper = new QueryWrapper<>();
        // 如果有线路id
        if (guidesId!= null) {
            wrapper.eq("guides_id", guidesId);
        }

        // 如果有dateId
        if (StrUtil.isNotBlank(dateId)) {
            wrapper.eq("date_id", dateId);
        }

        // 如果有journeyType
        if (StrUtil.isNotBlank(journeyType)) {
            wrapper.eq("journey_type", journeyType);
        }

        // 执行查询
        IPage<TbPoiInfoEntity> page;
        page = baseDao.selectPage(getPage(params, "", false), wrapper);

        // 将结果转换为 DTO
        List<TbPoiInfoDTO> dtoList = page.getRecords().stream().map(entity -> {
            TbPoiInfoDTO dto = convertToDTO(entity);
            return dto;
        }).collect(Collectors.toList());

        return new PageData<>(dtoList, page.getTotal(),page.getSize(),page.getPages(),page.getCurrent());

    }
    // 转换实体对象到 DTO
    private TbPoiInfoDTO convertToDTO(TbPoiInfoEntity entity) {
        TbPoiInfoDTO dto = new TbPoiInfoDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Object importPoiXlsx(MultipartFile file, Integer id) throws Exception{

        TbPoiInfoImportListener listener = new TbPoiInfoImportListener();
        // 调用Excel工具类
        ExcelUtils.importExcel(file, PoiExcelEntity.class, listener);

        // 获取解析完成的数据
        List<PoiExcelEntity> poiInfoList = listener.getPoiInfoList();

//        List<TbPoiInfoEntity> insertList = new ArrayList<>();
        // 插入数据库
        if (!poiInfoList.isEmpty()) {
            for (PoiExcelEntity entity : poiInfoList) {
                if (entity.getGuidesId()!=id){
                    throw new RenException("Excel文件解析到异常数据，与线路ID("+id+")不符，请核对此条数据："+entity);
                }

                TbPoiInfoEntity dbentity = new TbPoiInfoEntity();
                BeanUtils.copyProperties(entity,dbentity);

                // WGS84 转 GCJ-02
                double lon = Double.parseDouble(String.valueOf(entity.getLongitude()));
                double lat = Double.parseDouble(String.valueOf(entity.getLatitude()));
                double[] gcj02Coord = CoordinateTransformUtils.wgs84ToGcj02(lat, lon);

                dbentity.setLongitude(BigDecimal.valueOf(gcj02Coord[1]));
                dbentity.setLatitude(BigDecimal.valueOf(gcj02Coord[0]));

                baseDao.insert(dbentity);
            }
//            baseService.insertBatch(insertList,10);
//            boolean issuccess = tbPoiInfoDao.insertBatch(insertList);
        } else {
            throw new RenException("Excel文件未解析到有效数据");
        }
        return "success";
    }

    /**
     * 删除线路相关点
     * @param id
     */
    @Override
    public void deleteByRouteId(Integer id){
        QueryWrapper<TbPoiInfoEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("guides_id", id);
        List<TbPoiInfoEntity> list = baseDao.selectList(wrapper);

        // 如果列表为空，则无需删除，直接返回
        if (list.isEmpty()) {
            return;
        }

        // 提取所有记录的主键 ID，构造一个 List
        List<Long> idList = list.stream()
                .map(TbPoiInfoEntity::getId) // 假设主键字段为 id，请替换为实际字段名
                .collect(Collectors.toList());

        // 批量删除这些记录
        baseDao.deleteBatchIds(idList);
    }

}
