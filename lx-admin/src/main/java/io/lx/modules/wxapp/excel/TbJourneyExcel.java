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
 * @since 1.0.0 2024-11-02
 */
@Data
public class TbJourneyExcel {
    @ExcelProperty(value = "")
    private Integer id;
    @ExcelProperty(value = "")
    private Integer routeId;
    @ExcelProperty(value = "")
    private String name;
    @ExcelProperty(value = "")
    private String description;
    @ExcelProperty(value = "")
    private String journeyType;
    @ExcelProperty(value = "")
    private String dateId;
    @ExcelProperty(value = "")
    private String duration;
    @ExcelProperty(value = "")
    private String kmlPath;
    @ExcelProperty(value = "")
    private String travelType;
    @ExcelProperty(value = "")
    private Date createdAt;
    @ExcelProperty(value = "")
    private Date updatedAt;

}