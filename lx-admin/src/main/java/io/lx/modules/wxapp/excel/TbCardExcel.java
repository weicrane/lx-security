package io.lx.modules.wxapp.excel;

import com.alibaba.excel.annotation.ExcelProperty;
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
    @ExcelProperty(value = "主键ID")
    private Integer id;
    @ExcelProperty(value = "16位卡密")
    private String cardCode;
    @ExcelProperty(value = "状态(0:未使用, 1:已使用)")
    private Integer status;
    @ExcelProperty(value = "有效期")
    private Date expireTime;
    @ExcelProperty(value = "类别，00-终身会员，01-网盘路书，02-自驾活动，03-四季玩法")
    private String productType;
    @ExcelProperty(value = "线路玩法ID")
    private Integer routesGuidesId;
    @ExcelProperty(value = "创建时间")
    private Date createdAt;
    @ExcelProperty(value = "更新时间")
    private Date updatedAt;
    @ExcelProperty(value = "备注")
    private String description;

}