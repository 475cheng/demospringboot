package com.bitauto.ep.fx.webapi.service.impl;

import com.bitauto.ep.fx.webapi.configuration.core.AbstractService;
import com.bitauto.ep.fx.webapi.dao.SysUserMapper;
import com.bitauto.ep.fx.webapi.model.SysUser;
import com.bitauto.ep.fx.webapi.service.SysUserService;

import io.sentry.event.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/05/11.
 */
@Service
@Transactional
public class SysUserServiceImpl extends AbstractService<SysUser> implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public List<SysUser> queryUserOrRole() {
        return sysUserMapper.queryUserOrRole();
    }
}
