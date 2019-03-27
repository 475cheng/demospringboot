package com.bitauto.ep.fx.webapi.dao;

import com.bitauto.ep.fx.jdbcx.BeanRepository;
import com.bitauto.ep.fx.jdbcx.DataRepository;
import com.bitauto.ep.fx.webapi.model.UserInfoEntity;

@DataRepository(name = "aftermarket")
public class UserBeanRepository extends BeanRepository<UserInfoEntity> {

}
