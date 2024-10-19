package io.lx.modules.wxapp.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-10-10
 */
@Data
public class TbPoiInfoExcel {
    @ExcelProperty(value = "")
    private Long id;
    @ExcelProperty(value = "")
    private String title;
    @ExcelProperty(value = "")
    private String description;
    @ExcelProperty(value = "")
    private String poiType;
    @ExcelProperty(value = "")
    private BigDecimal price;
    @ExcelProperty(value = "")
    private BigDecimal latitude;
    @ExcelProperty(value = "")
    private BigDecimal longitude;
    @ExcelProperty(value = "")
    private Date createdAt;
    @ExcelProperty(value = "")
    private Date updatedAt;

}