package io.lx.modules.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 模型信息
 *
 * @author wyh
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("res_record")
public class ResRecordEntity {

    private Long id;

    private String oriName;

    private String savedName;

    private String filePath;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date uploadTime;

    private Integer status;
}
