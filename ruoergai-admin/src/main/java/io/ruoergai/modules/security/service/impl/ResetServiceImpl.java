package io.ruoergai.modules.security.service.impl;

import io.ruoergai.common.service.impl.BaseServiceImpl;
import io.ruoergai.modules.security.password.PasswordUtils;
import io.ruoergai.modules.security.service.ResetService;
import io.ruoergai.modules.sys.dao.SysUserDao;
import io.ruoergai.modules.sys.entity.SysUserEntity;
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