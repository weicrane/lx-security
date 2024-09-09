package io.lx.modules.security.service.impl;

import io.lx.common.service.impl.BaseServiceImpl;
import io.lx.modules.security.password.PasswordUtils;
import io.lx.modules.security.service.RegistService;
import io.lx.modules.sys.dao.SysUserDao;
import io.lx.modules.sys.entity.SysUserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistServiceImpl extends BaseServiceImpl<SysUserDao, SysUserEntity> implements RegistService {
    private final SysUserDao sysUserDao;

    @Override
    public void regist(String username, String passowrd, String mobile,String realname) {
        sysUserDao.regist(username,PasswordUtils.encode(passowrd), mobile, realname);
    }

}