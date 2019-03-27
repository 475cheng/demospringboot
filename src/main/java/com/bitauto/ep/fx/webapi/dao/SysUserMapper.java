package com.bitauto.ep.fx.webapi.dao;

import com.bitauto.ep.fx.webapi.configuration.core.Mapper;
import com.bitauto.ep.fx.webapi.model.SysUser;
import io.sentry.event.User;

import java.util.List;

public interface SysUserMapper extends Mapper<SysUser> {
    List<SysUser> queryUserOrRole();
}