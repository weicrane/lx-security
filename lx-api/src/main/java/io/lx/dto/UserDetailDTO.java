/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.lx.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.Map;


/**
 * 用户信息详情表单
 *
 */
@Data
@Schema(title = "用户信息表单")
public class UserDetailDTO {
    /**
     * 用户ID
     */
    @TableId
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
     * 会员类型
     */
    private String memberType;
    /**
     * openid
     */
    private String openid;
    /**
     * 会员体系
     */
    private Map<String,Object> membershipsMap;
    /**
     * 是否svip：0-非会员，1-终身全部会员
     */
    private String svip;

}
