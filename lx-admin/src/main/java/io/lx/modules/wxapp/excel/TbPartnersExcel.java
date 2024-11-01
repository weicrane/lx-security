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
 * @since 1.0.0 2024-10-31
 */
@Data
public class TbPartnersExcel {
    @ExcelProperty(value = "")
    private Integer id;
    @ExcelProperty(value = "")
    private String title;
    @ExcelProperty(value = "")
    private String subTitle;
    @ExcelProperty(value = "")
    private Integer type;
    @ExcelProperty(value = "")
    private String city;
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