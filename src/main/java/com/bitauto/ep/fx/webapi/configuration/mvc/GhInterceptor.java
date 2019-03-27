package com.bitauto.ep.fx.webapi.configuration.mvc;

import com.bitauto.ep.fx.webapi.configuration.annotation.AuthorInterceptor;
import com.bitauto.ep.fx.webapi.configuration.handler.AuthorityHandler;
import com.bitauto.ep.fx.webapi.utils.cache.RedisUtils;
import com.bitauto.ep.fx.webapi.service.impl.WxCacheInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;


public class GhInterceptor implements HandlerInterceptor {

    private final Logger log = LoggerFactory.getLogger(GhInterceptor.class);

    @Value("${qiyeweixin.appid}")
    private String appid;
    @Value("${qiyeweixin.agentid}")
    private String agentid;
    @Value("${qiyeweixin.accessTokenKey}")
    private String accessTokenKey;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private WxCacheInfoService wxCacheInfo;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            if(handler instanceof HandlerMethod){
                HandlerMethod myHandlerMethod = (HandlerMethod) handler;
                Method method = myHandlerMethod.getMethod();
                Annotation methodAnnotation = method.getAnnotation(AuthorInterceptor.class);
                if(methodAnnotation != null){
                    Class clazz = ((AuthorInterceptor) methodAnnotation).clazz();
                    AuthorityHandler authorityHandler = (AuthorityHandler) clazz.newInstance();
                    authorityHandler.authorityAuthentication(request,response,handler);
                }
            }
        } catch (Exception e) {
            log.info("拦截器处理异常："+e.toString());
            e.printStackTrace();

        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("视图渲染后："+handler.toString());
    }

}
