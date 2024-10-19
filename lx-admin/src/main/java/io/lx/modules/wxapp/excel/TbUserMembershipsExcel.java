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
 * @since 1.0.0 2024-10-19
 */
@Data
public class TbUserMembershipsExcel {
    @ExcelProperty(value = "")
    private Long id;
    @ExcelProperty(value = "")
    private Long userId;
    @ExcelProperty(value = "")
    private String memberType;
    @ExcelProperty(value = "")
    private Integer travelGuidesId;
    @ExcelProperty(value = "")
    private Integer selfDrivingsId;
    @ExcelProperty(value = "")
    private Integer routesGuidesId;
    @ExcelProperty(value = "")
    private Date startDate;
    @ExcelProperty(value = "")
    private Date endDate;
    @ExcelProperty(value = "")
    private Date createdAt;

}