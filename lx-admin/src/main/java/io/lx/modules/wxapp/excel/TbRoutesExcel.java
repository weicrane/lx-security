package io.lx.modules.wxapp.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 路线信息表
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-09-21
 */
@Data
public class TbRoutesExcel {
    @ExcelProperty(value = "")
    private Integer id;
    @ExcelProperty(value = "")
    private Integer routeCode;
    @ExcelProperty(value = "")
    private String name;
    @ExcelProperty(value = "")
    private String description;
    @ExcelProperty(value = "")
    private BigDecimal price;
    @ExcelProperty(value = "")
    private String duration;
    @ExcelProperty(value = "")
    private Date createdAt;
    @ExcelProperty(value = "")
    private Date updatedAt;

}