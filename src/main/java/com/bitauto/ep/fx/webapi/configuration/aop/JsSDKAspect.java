package com.bitauto.ep.fx.webapi.configuration.aop;

import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 过滤器 生成微信js的签名; 存入request作用域
 */
@Aspect
@Component
public class JsSDKAspect {

    private final Logger log = LoggerFactory.getLogger(JsSDKAspect.class);

    @Autowired
    private WxMpService wxMpService;


    @Before("@annotation(com.bitauto.ep.fx.webapi.configuration.annotation.JsSDKAnnotation)")
    public void doBefore(){
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url= request.getRequestURL().toString();
        WxJsapiSignature jsapiSignature = null;
        try {
            jsapiSignature = wxMpService.createJsapiSignature(url);
        } catch (WxErrorException e){
            log.info("获取jsapi出错：" , e.toString());
            e.printStackTrace();
        }
        request.setAttribute("jsapiSignature",jsapiSignature);
        System.out.println("-------------------------------------------------"+jsapiSignature);
    }
}
