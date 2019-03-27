package com.bitauto.ep.fx.webapi.service.impl;

import com.bitauto.ep.fx.webapi.configuration.core.AbstractService;
import com.bitauto.ep.fx.webapi.dao.SysRoleMapper;
import com.bitauto.ep.fx.webapi.model.SysRole;
import com.bitauto.ep.fx.webapi.service.SysRoleService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/05/11.
 */
@Service
@Transactional
public class SysRoleServiceImpl extends AbstractService<SysRole> implements SysRoleService {
    @Resource
    private SysRoleMapper sysRoleMapper;

}
