package com.bitauto.ep.fx.webapi.service;
import com.bitauto.ep.fx.webapi.configuration.core.Service;
import com.bitauto.ep.fx.webapi.model.SysUser;
import io.sentry.event.User;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/05/11.
 */
public interface SysUserService extends Service<SysUser> {

    List<SysUser> queryUserOrRole();
}
