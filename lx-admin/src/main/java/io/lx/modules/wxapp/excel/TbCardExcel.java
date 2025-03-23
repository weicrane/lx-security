package io.lx.modules.wxapp.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.util.Date;

/**
 * 卡密表
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2025-03-16
 */
@Data
public class TbCardExcel {
    @ExcelProperty(value = "卡ID")
    @ColumnWidth(20) // 设置列宽为 20
    private Integer id;

    @ExcelProperty(value = "兑换码（请注意保密）")
    @ColumnWidth(30) // 设置列宽为 20
    private String cardCode;

    @ExcelProperty(value = "状态(0-未使用，1-已使用)")
    @ColumnWidth(15) // 设置列宽为 20
    private Integer status;

    @ExcelProperty(value = "有效期")
    @ColumnWidth(30) // 设置列宽为 20
    private Date expireTime;

    @ExcelProperty(value = "类别(00-会员，03-线路)")
    @ColumnWidth(15) // 设置列宽为 20
    private String productType;

    @ExcelProperty(value = "线路ID")
    @ColumnWidth(15) // 设置列宽为 20
    private Integer routesGuidesId;

    @ExcelProperty(value = "创建时间")
    @ColumnWidth(30) // 设置列宽为 20
    private Date createdAt;

    @ExcelProperty(value = "更新时间")
    @ColumnWidth(30) // 设置列宽为 20
    private Date updatedAt;

    @ExcelProperty(value = "备注")
    private String description;

}