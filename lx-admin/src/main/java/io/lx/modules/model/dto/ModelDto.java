package io.lx.modules.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 模型信息
 */
@Data
@Schema(title = "模型信息")
public class ModelDto implements Serializable {
    private Integer model_id;
    private String model_name;
    private String model_categ;
    private String model_descr;
}
