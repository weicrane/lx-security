package io.lx.modules.wxapp.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-11-02
 */
@Data
@Schema(name = "")
public class PoiExcelDTO implements Serializable {

	@SchemaProperty(name = "")
	@ExcelIgnore
	private Long id;

	@SchemaProperty(name = "线路id")
	private Integer guidesId;

	@SchemaProperty(name = "标题")
	private String title;

	@SchemaProperty(name = "描述")
	private String description;

	@SchemaProperty(name = "类型")
	private String poiType;

	@SchemaProperty(name = "经度")
	private BigDecimal longitude;

	@SchemaProperty(name = "纬度")
	private BigDecimal latitude;

	@SchemaProperty(name = "")
	private String dateId;

	@SchemaProperty(name = "")
	private String journeyType;

	@SchemaProperty(name = "")
	private String filePath;

}
