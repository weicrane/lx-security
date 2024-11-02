package io.lx.modules.wxapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-11-02
 */
@Data
@Schema(name = "")
public class KmlRecordDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@SchemaProperty(name = "")
	private Integer id;

	@SchemaProperty(name = "")
	private String oriName;

	@SchemaProperty(name = "")
	private String savedName;

	@SchemaProperty(name = "")
	private String filePath;

	@SchemaProperty(name = "")
	private Date uploadTime;

	@SchemaProperty(name = "")
	private Integer status;


}
