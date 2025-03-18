package io.lx.modules.wxapp.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.util.Date;

/**
 * 卡密兑换记录表
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2025-03-16
 */
@Data
public class TbCardRedeemExcel {
    @ExcelProperty(value = "主键ID")
    private Integer id;
    @ExcelProperty(value = "用户ID")
    private Long userId;
    @ExcelProperty(value = "类别，00-终身会员，01-网盘路书，02-自驾活动，03-四季玩法")
    private String productType;
    @ExcelProperty(value = "线路玩法ID")
    private Integer routesGuidesId;
    @ExcelProperty(value = "16位卡密")
    private String cardCode;
    @ExcelProperty(value = "兑换时间")
    private Date redeemTime;
    @ExcelProperty(value = "")
    private Date createdAt;
    @ExcelProperty(value = "")
    private Date updatedAt;

}