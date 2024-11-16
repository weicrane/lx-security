package io.lx.modules.wxapp.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.lx.common.exception.RenException;
import io.lx.common.page.PageData;
import io.lx.common.service.impl.CrudServiceImpl;
import io.lx.common.utils.ExcelUtils;
import io.lx.modules.wxapp.dao.TbPoiInfoDao;
import io.lx.modules.wxapp.dto.TbPoiInfoDTO;
import io.lx.modules.wxapp.entity.TbPoiInfoEntity;
import io.lx.modules.wxapp.service.TbPoiInfoService;
import jakarta.annotation.Resource;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
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
    public void importPoiData(MultipartFile file) throws Exception {
        List<TbPoiInfoEntity> poiInfoList = new ArrayList<>();

        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {  // Skip header row
                Row row = sheet.getRow(i);
                if (row == null) continue;
                try {
                    TbPoiInfoEntity entity = new TbPoiInfoEntity();
                    entity.setTitle(row.getCell(1).getStringCellValue());
                    entity.setDescription(row.getCell(2).getStringCellValue());
                    entity.setPoiType(row.getCell(3).getStringCellValue());
                    entity.setLatitude(BigDecimal.valueOf(row.getCell(4).getNumericCellValue()));
                    entity.setLongitude(BigDecimal.valueOf(row.getCell(5).getNumericCellValue()));
                    entity.setDateId(row.getCell(6).getStringCellValue());
                    entity.setJourneyType(row.getCell(7).getStringCellValue());

                    poiInfoList.add(entity);
                } catch (Exception e) {
                    System.err.println("解析错误：行号 " + (i + 1) + "，错误：" + e.getMessage());
                }
            }

            if (!poiInfoList.isEmpty()) {
                insertPoiData(poiInfoList);
            } else {
                throw new Exception("导入的数据为空或全部解析失败");
            }

        } catch (Exception e) {
            System.err.println("导入失败：" + e.getMessage());
            throw new Exception("文件读取或批量插入异常", e);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void insertPoiData(List<TbPoiInfoEntity> poiList) {
        try {
            // 使用循环逐个插入
            for (TbPoiInfoEntity entity : poiList) {
                tbPoiInfoDao.insert(entity);  // 调用 insert 方法
            }
        } catch (Exception e) {
            System.err.println("批量插入失败，回滚所有操作");
            throw new RuntimeException("批量插入失败，事务回滚", e);  // 确保发生异常时回滚
        }
    }


    @Override
    public void importPoiXlsx(MultipartFile file) throws Exception{

        TbPoiInfoImportListener listener = new TbPoiInfoImportListener();
        // 调用Excel工具类
        ExcelUtils.importExcel(file, TbPoiInfoEntity.class, listener);

        // 获取解析完成的数据
        List<TbPoiInfoEntity> poiInfoList = listener.getPoiInfoList();

        // 插入数据库
        if (!poiInfoList.isEmpty()) {
            for (TbPoiInfoEntity entity : poiInfoList) {
                tbPoiInfoDao.insert(entity);
            }
        } else {
            throw new RenException("导入的Excel文件中没有有效数据");
        }
    }

}
