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
@TableName("model_info")
public class ModelEntity {
    private Integer model_id;
    private String model_name;
    private String model_categ;
    private String model_descr;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date registration_date;
}
