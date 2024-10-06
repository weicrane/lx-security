package io.lx.modules.wxapp.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.util.Date;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-09-21
 */
@Data
public class TbBannersExcel {
    @ExcelProperty(value = "")
    private Integer id;
    @ExcelProperty(value = "")
    private Integer routeCode;
    @ExcelProperty(value = "")
    private String oriName;
    @ExcelProperty(value = "")
    private String savedName;
    @ExcelProperty(value = "")
    private String path;
    @ExcelProperty(value = "")
    private Integer displayOrder;
    @ExcelProperty(value = "")
    private Date createdAt;
    @ExcelProperty(value = "")
    private Date updatedAt;

}