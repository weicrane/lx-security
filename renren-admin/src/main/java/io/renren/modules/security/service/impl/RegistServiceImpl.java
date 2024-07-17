package io.renren.modules.security.service.impl;

import io.renren.common.service.impl.BaseServiceImpl;
import io.renren.modules.security.password.PasswordUtils;
import io.renren.modules.security.service.RegistService;
import io.renren.modules.security.service.ResetService;
import io.renren.modules.sys.dao.SysUserDao;
import io.renren.modules.sys.entity.SysUserEntity;
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