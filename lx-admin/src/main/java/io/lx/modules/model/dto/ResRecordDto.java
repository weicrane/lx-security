package io.lx.modules.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 模型信息
 */
@Data
@Schema(title = "资源库信息")
public class ResRecordDto implements Serializable {

    private Long id;

    private String ori_Name;

    private String saved_Name;

    private String file_Path;

    private Date upload_Time;

    private Integer status;
}
