package io.lx.modules.wxapp.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 用户信息表
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-09-21
 */
@Data
public class TbUserExcel {
    @ExcelProperty(value = "")
    private Long id;
    @ExcelProperty(value = "")
    private String username;
    @ExcelProperty(value = "")
    private String password;
    @ExcelProperty(value = "")
    private String email;
    @ExcelProperty(value = "")
    private String mobile;
    @ExcelProperty(value = "")
    private String gender;
    @ExcelProperty(value = "")
    private String avatarUrl;
    @ExcelProperty(value = "")
    private Date createdAt;
    @ExcelProperty(value = "")
    private Date updatedAt;

}