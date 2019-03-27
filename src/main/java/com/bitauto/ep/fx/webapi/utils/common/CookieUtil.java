package com.bitauto.ep.fx.webapi.utils.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 根据key 获取cookie的value
 */
public class CookieUtil {

    /**
     *  根据key  获取cookie的值
     * @param request
     * @param key     cookie的key
     * @return
     */
    public static String getValueByCook(HttpServletRequest request,String key){
        Cookie[] cookies = request.getCookies();
        String userId="";
        if(cookies!=null){
            for (Cookie cookie:cookies){
                if(key.equals(cookie.getName())){
                    userId= cookie.getValue();
                    break;
                }
            }
        }
        return userId;
    }
    /**
     * 设置cookie
     * @param response
     * @param name
     * @param value
     * @param maxAge
     */
    public static void set(HttpServletResponse response,
                           String name,
                           String value,
                           int maxAge){
        Cookie cookie = new Cookie(name, value); //设置cookie的key和value值
        cookie.setMaxAge(maxAge);   //过期时间
        response.addCookie(cookie); //添加cookie
    }

    /**
     * 获取cookie
     * @param request
     * @param name
     * @return
     */
    public static Cookie get(HttpServletRequest request,
                             String name){
        Map<String, Cookie> cookieMap = readCookieMap(request);
        if(cookieMap.containsKey(name)){  //判断cookieMap是否含有该key
            return cookieMap.get(name);
        }else{
            return null;
        }

    }

    /**
     * 将cookie封装成map
     * @param request
     * @return
     */
    private static Map<String, Cookie> readCookieMap(HttpServletRequest request){
        Map<String, Cookie> cookieMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();        //获取所有的cookie值
        if(cookies != null){
            for (Cookie cookie : cookies){
                cookieMap.put(cookie.getName(),cookie);
            }
        }
        return cookieMap;
    }
}
