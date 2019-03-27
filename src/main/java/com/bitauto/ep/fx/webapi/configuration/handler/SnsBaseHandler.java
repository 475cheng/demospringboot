package com.bitauto.ep.fx.webapi.configuration.handler;

import com.bitauto.ep.fx.webapi.utils.cache.RedisUtils;
import com.bitauto.ep.fx.webapi.utils.common.CookieUtil;
import com.bitauto.ep.fx.webapi.utils.web.SpringContextUtil;
import com.bitauto.ep.fx.webapi.model.RedisKeyEnum;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 静默授权拦截器处理类
 */
public class SnsBaseHandler implements AuthorityHandler {

    @Override
    public boolean authorityAuthentication(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("静默--------------------------------------------------------");
        // 通过spring上下文 获取bean实体类
        RedisUtils redisUtils = SpringContextUtil.getBean("redisUtils");
        WxMpService wxMpService= SpringContextUtil.getBean("wxMpService");
        //1 cookie获取用户userId
        String openId = CookieUtil.getValueByCook(request,"userId");
        //2 判断用户是否登录
        String qywxLogin = redisUtils.stringGetStringByKey(RedisKeyEnum.GZWX_LOGIN_KEY+openId);
        if(!"Y".equals(qywxLogin)){  //没登录过
            String code = request.getParameter("code");
            if(code==null){
                //3 重定向到微信后台
                String requestURL = request.getRequestURL().toString();
                String href = wxMpService.oauth2buildAuthorizationUrl(requestURL,WxConsts.OAuth2Scope.SNSAPI_BASE , null);
                response.sendRedirect(href);
                return false;
            }else{
                //4 根据微信重定向携带的code 获取OAuth2AccessToken 再获取用户信息
                WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
                CookieUtil.set(response,"userId",wxMpOAuth2AccessToken.getOpenId(),3600);
                redisUtils.stringSetValueAndExpireTime(RedisKeyEnum.GZWX_LOGIN_KEY+":base"+openId,"Y",3600000);
            }
        }
        return true;
    }
}
