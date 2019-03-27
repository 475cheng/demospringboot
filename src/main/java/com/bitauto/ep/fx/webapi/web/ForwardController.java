package com.bitauto.ep.fx.webapi.web;

import com.bitauto.ep.fx.jdbcx.Query.EntityQueryBuilder;
import com.bitauto.ep.fx.jdbcx.Query.QueryBuilder;
import com.bitauto.ep.fx.jdbcx.Query.QueryCondition;
import com.bitauto.ep.fx.webapi.configuration.annotation.AuthorInterceptor;
import com.bitauto.ep.fx.webapi.configuration.handler.PermissionHandler;
import com.bitauto.ep.fx.webapi.configuration.handler.SnsUserInfoHandler;
import com.bitauto.ep.fx.webapi.configuration.mq.Sender;

import com.bitauto.ep.fx.webapi.dao.UserBeanRepository;
import com.bitauto.ep.fx.webapi.model.UserInfoEntity;
import org.apache.kafka.common.network.Send;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/vue")
public class ForwardController {
    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(ForwardController.class);

    /*@Autowired
    private Sender sender;*/
    @Autowired
    private UserBeanRepository userBeanRepository;

    @AuthorInterceptor( clazz = PermissionHandler.class)  //接口权限拦截
    @RequestMapping("/logins")
    public String logins(HttpServletRequest request){
        //获取本仓库查询类
        //QueryBuilder queryBuilder = userBeanRepository.createQuery();
        QueryBuilder queryBuilder = new EntityQueryBuilder<>(UserInfoEntity.class);

        queryBuilder.Where()
                .And(QueryCondition.LE(UserInfoEntity.Columns.customID,101))
                .And(QueryCondition.EQ(UserInfoEntity.Columns.nickName,"1"));
        //查询本类对应实体
        List<UserInfoEntity> users1 = userBeanRepository.queryForList(queryBuilder);
        System.out.println(users1);

        return "bootstrap/login";
    }
    @AuthorInterceptor( clazz = SnsUserInfoHandler.class)  //微信非静默授权拦截
    @RequestMapping("/login")
    public String login(HttpServletRequest request){
        return "bootstrap/login";
    }
    @RequestMapping("/testForward")
    public String testForward(Model model){
        model.addAttribute("app","modelParam");
        return "index";
    }
    @RequestMapping("/goBootstrap")
    public String goBootstrap(){
        return "bootstrap/index";
    }
    @RequestMapping("/testParam")
    public String testParam(HttpServletRequest request){
        /*List<UserInfo> list=new ArrayList<>();
        for(int i=0;i<10;i++){
            UserInfo userInfo=new UserInfo();
            userInfo.setId(i);
            userInfo.setUserName("用户名"+i);
            list.add(userInfo);
        }*/
        return "user/user";
    }
}
