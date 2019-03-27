package com.bitauto.ep.fx.webapi.configuration.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用于处理 权限认证相关的接口
 */
public interface AuthorityHandler {
    /**
     *  不同权限认证规则 实现不同的方法
     * @param request
     * @param response
     * @param handler
     * @return  返回false 表示拦截请求，true 表示放行
     * @throws Exception
     */
    boolean authorityAuthentication(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;
}
