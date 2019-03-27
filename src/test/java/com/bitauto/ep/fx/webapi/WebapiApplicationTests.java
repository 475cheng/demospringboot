package com.bitauto.ep.fx.webapi;

import com.bitauto.ep.fx.jdbcx.Query.EntityQueryBuilder;
import com.bitauto.ep.fx.jdbcx.Query.QueryBuilder;
import com.bitauto.ep.fx.jdbcx.Query.QueryCondition;
import com.bitauto.ep.fx.webapi.dao.SysUserRepository;
import com.bitauto.ep.fx.webapi.dao.UserBeanRepository;
import com.bitauto.ep.fx.webapi.model.UserInfoEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class WebapiApplicationTests {

	@Autowired
	private SysUserRepository sysUserRepository;
	@Autowired
	private UserBeanRepository userBeanRepository;
	@Test
	public void jdbcx(){

		//获取本仓库查询类
		//QueryBuilder queryBuilder = userBeanRepository.createQuery();
		QueryBuilder queryBuilder = new EntityQueryBuilder<>(UserInfoEntity.class);

		queryBuilder.Where()
				.And(QueryCondition.LE(UserInfoEntity.Columns.customID,2312312))
				.And(QueryCondition.EQ(UserInfoEntity.Columns.nickName,"121421"));
		//查询本类对应实体
		List<UserInfoEntity> users1 = userBeanRepository.queryForList(queryBuilder);
		System.out.println(users1);
	}
	@Test
	public void testorder(){

	}


}
