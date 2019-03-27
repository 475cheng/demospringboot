package com.bitauto.ep.fx.webapi.configuration.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 接口权限认证
 */
public class PermissionHandler implements AuthorityHandler {

    @Override
    public boolean authorityAuthentication(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        String header = request.getHeader("x-app");
        if(null != handler && "wechat".equals(header) ){
            return true;
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        Map mpa=new HashMap<>();
        mpa.put("213",null);
        mpa.put("321","123");
        out.append(JSON.toJSONString(mpa, SerializerFeature.WriteMapNullValue));
        return false;
    }
}
