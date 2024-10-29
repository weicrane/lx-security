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
 * @since 1.0.0 2024-10-29
 */
@Data
public class TbSelfDrivingsApplyExcel {
    @ExcelProperty(value = "")
    private String applyId;
    @ExcelProperty(value = "")
    private Long userId;
    @ExcelProperty(value = "")
    private Integer selfDrivingId;
    @ExcelProperty(value = "")
    private String name;
    @ExcelProperty(value = "")
    private String phone;
    @ExcelProperty(value = "")
    private Integer adults;
    @ExcelProperty(value = "")
    private Integer children;
    @ExcelProperty(value = "")
    private String remark;
    @ExcelProperty(value = "")
    private String status;
    @ExcelProperty(value = "")
    private Date createdAt;
    @ExcelProperty(value = "")
    private Date updatedAt;

}