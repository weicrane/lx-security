package io.lx.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-11-02
 */
@Data
@Schema(name = "")
public class GetPoiListDTO implements Serializable {

	@SchemaProperty(name = "")
	@NotNull(message="routeGuideId不能为空")
	private Integer routeGuideId;

	private List<String> poiTypeList; // 使用 List 接口，确保通用性

	@SchemaProperty(name = "日程id")
	private String dateId;

	@SchemaProperty(name = "类型:1-主线；2-小众玩法")
	@NotBlank(message="journeyType不能为空")
	private String journeyType;
}
