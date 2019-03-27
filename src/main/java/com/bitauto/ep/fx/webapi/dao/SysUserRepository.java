package com.bitauto.ep.fx.webapi.dao;


import com.bitauto.ep.fx.webapi.model.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: ShenYC
 * @Describation:
 * @Date: Created in 15:39 2018/4/20
 */
@Repository
public interface SysUserRepository extends JpaRepository<SysUser, Integer> {

    @Query(value = "select s1.*,s2.id sid,s2.role_name roleName,s2.create_time ctime,s2.update_time utime,s2.delete_status dsta " +
            " from sys_user s1 LEFT JOIN sys_role s2 on s1.role_id=s2.id",
            nativeQuery = true)
    List<SysUser> listUser();
}