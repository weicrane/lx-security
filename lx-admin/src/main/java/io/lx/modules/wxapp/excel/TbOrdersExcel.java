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
 * @since 1.0.0 2024-10-26
 */
@Data
public class TbOrdersExcel {
    @ExcelProperty(value = "")
    private String orderId;
    @ExcelProperty(value = "")
    private Long userId;
    @ExcelProperty(value = "")
    private String openid;
    @ExcelProperty(value = "")
    private String productType;
    @ExcelProperty(value = "")
    private Long productId;
    @ExcelProperty(value = "")
    private BigDecimal amount;
    @ExcelProperty(value = "")
    private String status;
    @ExcelProperty(value = "")
    private Date createdAt;
    @ExcelProperty(value = "")
    private Date updatedAt;

}