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
 * @since 1.0.0 2024-10-20
 */
@Data
public class TbRoadDiscussExcel {
    @ExcelProperty(value = "")
    private Integer id;
    @ExcelProperty(value = "")
    private String content;
    @ExcelProperty(value = "")
    private String status;
    @ExcelProperty(value = "")
    private Long userId;
    @ExcelProperty(value = "")
    private Integer conditionId;
    @ExcelProperty(value = "")
    private Date createdAt;
    @ExcelProperty(value = "")
    private Date updatedAt;

}