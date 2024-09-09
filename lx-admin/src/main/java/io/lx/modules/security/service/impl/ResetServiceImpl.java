package io.lx.modules.security.service.impl;

import io.lx.common.service.impl.BaseServiceImpl;
import io.lx.modules.security.password.PasswordUtils;
import io.lx.modules.security.service.ResetService;
import io.lx.modules.sys.dao.SysUserDao;
import io.lx.modules.sys.entity.SysUserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ResetServiceImpl extends BaseServiceImpl<SysUserDao, SysUserEntity> implements ResetService {
    private final SysUserDao sysUserDao;

    @Override
    public void reset(String username, String passowrd) {
        sysUserDao.resetPassword(username,PasswordUtils.encode(passowrd));
    }

}