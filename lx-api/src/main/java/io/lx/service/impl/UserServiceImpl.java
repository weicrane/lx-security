/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.lx.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.DigestUtil;
import io.lx.common.exception.ErrorCode;
import io.lx.common.exception.RenException;
import io.lx.common.service.impl.BaseServiceImpl;
import io.lx.common.utils.FileUtil;
import io.lx.common.validator.AssertUtils;
import io.lx.dao.UserDao;
import io.lx.dto.LoginDTO;
import io.lx.dto.UserDetailDTO;
import io.lx.entity.TokenEntity;
import io.lx.entity.UserEntity;
import io.lx.service.TokenService;
import io.lx.service.UserMembershipsService;
import io.lx.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Service
public class UserServiceImpl extends BaseServiceImpl<UserDao, UserEntity> implements UserService {

    @Value("${web.upload-path}")
    private String uploadPath;

    // 通过 @Autowired 注入 TokenService
    private final TokenService tokenService;
    private final UserMembershipsService userMembershipsService;

    @Autowired
    public UserServiceImpl(TokenService tokenService, UserMembershipsService userMembershipsService) {
        this.tokenService = tokenService;
        this.userMembershipsService = userMembershipsService;
    }

    @Override
    public UserEntity getByMobile(String mobile) {
        return baseDao.getUserByMobile(mobile);
    }

    @Override
    public UserEntity getUserByUserId(Long userId) {
        return baseDao.getUserByUserId(userId);
    }

    @Override
    public Map<String, Object> login(LoginDTO dto) {
        UserEntity user = getByMobile(dto.getMobile());
        AssertUtils.isNull(user, ErrorCode.ACCOUNT_PASSWORD_ERROR);

        //密码错误
        if (!user.getPassword().equals(DigestUtil.sha256Hex(dto.getPassword()))) {
            throw new RenException(ErrorCode.ACCOUNT_PASSWORD_ERROR);
        }

        //获取登录token
        TokenEntity tokenEntity = tokenService.createToken(user.getId());

        Map<String, Object> map = new HashMap<>(2);
        map.put("token", tokenEntity.getToken());
        map.put("expire", tokenEntity.getExpireDate().getTime() - System.currentTimeMillis());

        return map;
    }

    @Override
    public String uploadHeadIcon(MultipartFile file, String token) throws RenException {
        TokenEntity tokenEntity = tokenService.getByToken(token);
        if (tokenEntity == null) {
            throw new RenException("用户不存在");
        }
        if (ObjectUtil.isEmpty(file)) {
            throw new RenException("请上传头像文件");
        }
        if (FileUtil.isNotImage(FileUtil.fileExtensionName(file.getOriginalFilename()))) {
            throw new RenException("请上传图片类型文件");
        }

        UserEntity user = getUserByUserId(tokenEntity.getUserId());
        // 文件名默认为【手机号_时间戳.jpg】
        String originalFilename = file.getOriginalFilename();
        String filename = user.getMobile() + "_" + System.currentTimeMillis() + "." + cn.hutool.core.io.FileUtil.extName(originalFilename);
        // 上传路径默认为【/head/】
        String path = "/head/" + filename;
        File destFile = cn.hutool.core.io.FileUtil.file(uploadPath + path);
        cn.hutool.core.io.FileUtil.touch(destFile);
        try {
            cn.hutool.core.io.FileUtil.writeBytes(file.getBytes(), destFile);
        } catch (IOException e) {
            throw new RenException("请上传头像文件");
        }

        // 更新数据库
        baseDao.updateAvatarUrl(user.getId(), path);

        return path;
    }

    @Override
    public UserDetailDTO getUserInfoDetailByToken(String token) {
        TokenEntity tokenEntity = tokenService.getByToken(token);
        if (tokenEntity == null) {
            throw new RenException("用户不存在");
        }

        UserEntity user = getUserByUserId(tokenEntity.getUserId());
        // 构造返回值
        UserDetailDTO dto = new UserDetailDTO();
        // 自动映射相同字段
        BeanUtils.copyProperties(user, dto);

        if ("2".equals(user.getMemberType())) {
            // 当 memberType 为 "2"-特殊会员，获取全部会员列表
            Map<String, Object> membershipsMap = userMembershipsService.getMembershipsByUserId(tokenEntity.getUserId());
            dto.setMembershipsMap(membershipsMap);
        }

        return dto;
    }

}