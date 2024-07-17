package io.renren.modules.security.service.impl;

import io.renren.common.service.impl.BaseServiceImpl;
import io.renren.modules.security.password.BCryptPasswordEncoder;
import io.renren.modules.security.password.PasswordUtils;
import io.renren.modules.security.service.ResetService;
import io.renren.modules.sys.dao.SysUserDao;
import io.renren.modules.sys.entity.SysUserEntity;
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