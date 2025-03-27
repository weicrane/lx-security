package io.lx.modules.wxapp.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
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
    @ColumnWidth(20) // 设置列宽为 20
    private Integer id;

    @ExcelProperty(value = "用户ID")
    @ColumnWidth(40) // 设置列宽为 20
    private Long userId;

    @ExcelProperty(value = "类别(00-会员，03-线路)")
    @ColumnWidth(15) // 设置列宽为 20
    private String productType;

    @ExcelProperty(value = "线路ID")
    @ColumnWidth(15) // 设置列宽为 20
    private Integer routesGuidesId;

    @ExcelProperty(value = "标题")
    @ColumnWidth(40) // 设置列宽为 20
    private String tittle;

    @ExcelProperty(value = "兑换码")
    @ColumnWidth(30) // 设置列宽为 20
    private String cardCode;

    @ExcelProperty(value = "兑换时间")
    @ColumnWidth(30) // 设置列宽为 20
    private Date redeemTime;

    @ExcelProperty(value = "备注")
    @ColumnWidth(40) // 设置列宽为 20
    private String description;
}