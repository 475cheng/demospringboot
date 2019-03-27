package com.bitauto.ep.fx.webapi.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
//Table注解表示类型对应表结构 name 为 表名。
@Table(name = "UserInfo")
public class UserInfoEntity {

    /**
     * 表字段定义静态类
     */
    public static final class Columns {
        public static final String cusUserID = "CusUserID";
        public static final String account = "Account";
        public static final String customID = "CustomID";
        public static final String nickName = "NickName";
        public static final String headImgUrl = "HeadImgUrl";
    }

    //id表示这个字段为主键
    @Id
    //表示自增列
    @GeneratedValue
    //对应列名 name = 表中的列名
    @Column(name = "CusUserID")
    private Long cusUserID;

    @Column(name = "Account")
    private String account;

    @Column(name = "CustomID")
    private String customID;

    @Column(name = "NickName")
    private String nickName;

    @Column(name = "HeadImgUrl")
    private String headImgUrl;
}
