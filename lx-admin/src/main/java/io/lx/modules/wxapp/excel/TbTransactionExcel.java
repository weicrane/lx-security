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
 * @since 1.0.0 2024-10-27
 */
@Data
public class TbTransactionExcel {
    @ExcelProperty(value = "")
    private String transactionId;
    @ExcelProperty(value = "")
    private String outTradeNo;
    @ExcelProperty(value = "")
    private Integer total;
    @ExcelProperty(value = "")
    private String tradeState;
    @ExcelProperty(value = "")
    private String tradeType;
    @ExcelProperty(value = "")
    private String tradeStateDesc;
    @ExcelProperty(value = "")
    private String bankType;
    @ExcelProperty(value = "")
    private String successTime;
    @ExcelProperty(value = "")
    private String payer;
    @ExcelProperty(value = "")
    private String amount;
    @ExcelProperty(value = "")
    private String sceneInfo;
    @ExcelProperty(value = "")
    private String promotionDetail;
    @ExcelProperty(value = "")
    private String failMessage;
    @ExcelProperty(value = "")
    private Date createdAt;
    @ExcelProperty(value = "")
    private Date updatedAt;

}