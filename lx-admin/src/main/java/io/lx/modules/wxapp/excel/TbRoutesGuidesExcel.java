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
 * @since 1.0.0 2024-10-19
 */
@Data
public class TbRoutesGuidesExcel {
    @ExcelProperty(value = "")
    private Integer id;
    @ExcelProperty(value = "")
    private String title;
    @ExcelProperty(value = "")
    private String subTitle;
    @ExcelProperty(value = "")
    private Integer travelType;
    @ExcelProperty(value = "")
    private String season;
    @ExcelProperty(value = "")
    private String destinationCity;
    @ExcelProperty(value = "")
    private String departureCity;
    @ExcelProperty(value = "")
    private String tag;
    @ExcelProperty(value = "")
    private String purchaseNotes;
    @ExcelProperty(value = "")
    private String coverImgPath;
    @ExcelProperty(value = "")
    private String detailImgPath;
    @ExcelProperty(value = "")
    private Date createdAt;
    @ExcelProperty(value = "")
    private Date updatedAt;
    @ExcelProperty(value = "")
    private BigDecimal price;
    @ExcelProperty(value = "")
    private Integer inventory;

}