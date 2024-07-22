package io.ruoergai.modules.security.service.impl;

import io.ruoergai.common.service.impl.BaseServiceImpl;
import io.ruoergai.modules.security.password.PasswordUtils;
import io.ruoergai.modules.security.service.RegistService;
import io.ruoergai.modules.sys.dao.SysUserDao;
import io.ruoergai.modules.sys.entity.SysUserEntity;
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