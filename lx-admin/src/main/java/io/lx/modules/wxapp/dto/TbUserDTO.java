package io.lx.modules.wxapp.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户信息表
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-09-21
 */
@Data
@Schema(name = "用户信息表")
public class TbUserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@TableId
	@SchemaProperty(name = "用户ID")
	private Long id;
	/**
	 * 用户名
	 */
	@SchemaProperty(name = "用户名")
	private String username;
	/**
	 * 手机号
	 */
	@SchemaProperty(name = "手机号")
	private String mobile;
	/**
	 * 创建时间
	 */
	@SchemaProperty(name = "")
	private Date createdAt;
	/**
	 *
	 */
	@SchemaProperty(name = "")
	private Date updatedAt;
	/**
	 * 昵称
	 */
	@SchemaProperty(name = "昵称")
	private String nickname;
	/**
	 * 头像地址
	 */
	@SchemaProperty(name = "头像地址")
	private String avatarUrl;

	/**
	 * 会员类型
	 */
	@SchemaProperty(name = "会员类型")
	private String memberType;
	/**
	 * openid
	 */
	@SchemaProperty(name = "openid")
	private String openid;
	/**
	 * 是否svip：0-非会员，1-终身全部会员
	 */
	@SchemaProperty(name = "是否svip")
	private String svip;
}
