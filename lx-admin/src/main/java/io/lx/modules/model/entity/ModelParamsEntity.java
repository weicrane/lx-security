package io.lx.modules.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 模型信息
 *
 * @author wyh
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("model_params")
public class ModelParamsEntity {
    private Integer model_id;
    private String param_name;
    private String param_categ;
    private String param_descr;
}
