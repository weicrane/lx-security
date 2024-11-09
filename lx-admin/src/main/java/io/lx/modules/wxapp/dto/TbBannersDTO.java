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
 * @since 1.0.0 2024-09-21
 */
@Data
@Schema(name = "首页轮播图Banner")
public class TbBannersDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@SchemaProperty(name = "ID")
	private Integer id;

	@SchemaProperty(name = "电子路书（网盘）ID")
	private Integer routeCode;

	@SchemaProperty(name = "图片原文件名")
	private String oriName;

	@SchemaProperty(name = "服务器保存文件名")
	private String savedName;

	@SchemaProperty(name = "保存路径")
	private String path;

	@SchemaProperty(name = "显示顺序")
	private Integer displayOrder;

	@SchemaProperty(name = "创建时间")
	private Date createdAt;

	@SchemaProperty(name = "更新时间")
	private Date updatedAt;


}
