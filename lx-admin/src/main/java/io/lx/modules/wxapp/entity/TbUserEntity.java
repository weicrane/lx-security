package io.lx.modules.wxapp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

/**
 * 用户信息表
 *
 * @author Mofeng laoniane@gmail.com
 * @since 1.0.0 2024-09-21
 */
@Data
@TableName("tb_user")
public class TbUserEntity {

    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO) // 使用数据库的自增策略
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 密码
     */
    @JsonIgnore
    private String password;
    /**
     * 创建时间
     */
    private Date createdAt;
    /**
     *
     */
    private Date updatedAt;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像地址
     */
    private String avatarUrl;
    /**
     * ！弃用：会员类型：0-非会员，1-终身全部会员，2-特定路线会员
     */
    private String memberType;
    /**
     * openid
     */
    private String openid;
    /**
     * 是否svip：0-非会员，1-终身全部会员
     */
    private String svip;

}