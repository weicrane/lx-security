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
 * @since 1.0.0 2024-10-10
 */
@Data
public class TbPoiImagesExcel {
    @ExcelProperty(value = "")
    private Long id;
    @ExcelProperty(value = "")
    private Long poiId;
    @ExcelProperty(value = "")
    private String imageUrl;
    @ExcelProperty(value = "")
    private String title;
    @ExcelProperty(value = "")
    private String description;
    @ExcelProperty(value = "")
    private String imgName;
    @ExcelProperty(value = "")
    private String imgPath;
    @ExcelProperty(value = "")
    private Date createdAt;

}