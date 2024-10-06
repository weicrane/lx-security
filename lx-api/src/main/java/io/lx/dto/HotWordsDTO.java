/**
 /**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.lx.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;


/**
 * 热词
 *
 */
@Data
@Schema(title = "热词表单")
public class HotWordsDTO {

    @Schema(title = "ID")
    private Integer id;

    @Schema(title = "热词")
    private String keyword;

    @Schema(title = "创建时间")
    private Date createdAt;

    @Schema(title = "更新时间")
    private Date updatedAt;
}
