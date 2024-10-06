package io.lx.modules.wxapp.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.util.Date;

/**
 * 用户Token
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-09-21
 */
@Data
public class TbTokenExcel {
    @ExcelProperty(value = "id")
    private Long id;
    @ExcelProperty(value = "用户ID")
    private Long userId;
    @ExcelProperty(value = "token")
    private String token;
    @ExcelProperty(value = "过期时间")
    private Date expireDate;
    @ExcelProperty(value = "更新时间")
    private Date updateDate;

}