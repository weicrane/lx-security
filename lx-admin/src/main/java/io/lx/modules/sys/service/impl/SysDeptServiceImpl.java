/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.lx.modules.sys.service.impl;

import com.qiniu.util.StringUtils;
import io.lx.common.constant.Constant;
import io.lx.common.exception.ErrorCode;
import io.lx.common.exception.RenException;
import io.lx.common.service.impl.BaseServiceImpl;
import io.lx.common.utils.ConvertUtils;
import io.lx.common.utils.TreeUtils;
import io.lx.modules.security.user.SecurityUser;
import io.lx.modules.security.user.UserDetail;
import io.lx.modules.sys.dao.SysDeptDao;
import io.lx.modules.sys.dao.SysUserDao;
import io.lx.modules.sys.dto.SysDeptDTO;
import io.lx.modules.sys.entity.SysDeptEntity;
import io.lx.modules.sys.enums.SuperAdminEnum;
import io.lx.modules.sys.service.SysDeptService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@AllArgsConstructor
public class SysDeptServiceImpl extends BaseServiceImpl<SysDeptDao, SysDeptEntity> implements SysDeptService {
    private final SysUserDao sysUserDao;

    @Override
    public List<SysDeptDTO> list(Map<String, Object> params) {
        //普通管理员，只能查询所属部门及子部门的数据
        UserDetail user = SecurityUser.getUser();
        if (user.getSuperAdmin() == SuperAdminEnum.NO.value()) {
            params.put("deptIdList", getSubDeptIdList(user.getDeptId()));
        }

        //查询部门列表
        List<SysDeptEntity> entityList = baseDao.getList(params);

        List<SysDeptDTO> dtoList = ConvertUtils.sourceToTarget(entityList, SysDeptDTO.class);

        return TreeUtils.build(dtoList);
    }

    @Override
    public SysDeptDTO get(Long id) {
        //超级管理员，部门ID为null
        if (id == null) {
            return null;
        }

        SysDeptEntity entity = baseDao.getById(id);

        return ConvertUtils.sourceToTarget(entity, SysDeptDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysDeptDTO dto) {
        SysDeptEntity entity = ConvertUtils.sourceToTarget(dto, SysDeptEntity.class);

        entity.setPids(getPidList(entity.getPid()));
        insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysDeptDTO dto) {
        SysDeptEntity entity = ConvertUtils.sourceToTarget(dto, SysDeptEntity.class);

        //上级部门不能为自身
        if (entity.getId().equals(entity.getPid())) {
            throw new RenException(ErrorCode.SUPERIOR_DEPT_ERROR);
        }

        //上级部门不能为下级部门
        List<Long> subDeptList = getSubDeptIdList(entity.getId());
        if (subDeptList.contains(entity.getPid())) {
            throw new RenException(ErrorCode.SUPERIOR_DEPT_ERROR);
        }

        entity.setPids(getPidList(entity.getPid()));
        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        //判断是否有子部门
        List<Long> subList = getSubDeptIdList(id);
        if (subList.size() > 1) {
            throw new RenException(ErrorCode.DEPT_SUB_DELETE_ERROR);
        }

        //判断部门下面是否有用户
        int count = sysUserDao.getCountByDeptId(id);
        if (count > 0) {
            throw new RenException(ErrorCode.DEPT_USER_DELETE_ERROR);
        }

        //删除
        baseDao.deleteById(id);
    }

    @Override
    public List<Long> getSubDeptIdList(Long id) {
        List<Long> deptIdList = baseDao.getSubDeptIdList("%" + id + "%");
        deptIdList.add(id);

        return deptIdList;
    }

    /**
     * 获取所有上级部门ID
     *
     * @param pid 上级ID
     */
    private String getPidList(Long pid) {
        //顶级部门，无上级部门
        if (Constant.DEPT_ROOT.equals(pid)) {
            return Constant.DEPT_ROOT + "";
        }

        //所有部门的id、pid列表
        List<SysDeptEntity> deptList = baseDao.getIdAndPidList();

        //list转map
        Map<Long, SysDeptEntity> map = new HashMap<>(deptList.size());
        for (SysDeptEntity entity : deptList) {
            map.put(entity.getId(), entity);
        }

        //递归查询所有上级部门ID列表
        List<Long> pidList = new ArrayList<>();
        getPidTree(pid, map, pidList);

        return StringUtils.join(pidList, ",");
    }

    private void getPidTree(Long pid, Map<Long, SysDeptEntity> map, List<Long> pidList) {
        //顶级部门，无上级部门
        if (Constant.DEPT_ROOT.equals(pid)) {
            return;
        }

        //上级部门存在
        SysDeptEntity parent = map.get(pid);
        if (parent != null) {
            getPidTree(parent.getPid(), map, pidList);
        }

        pidList.add(pid);
    }
}
