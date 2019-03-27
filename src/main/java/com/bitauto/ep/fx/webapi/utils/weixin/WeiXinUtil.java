package com.bitauto.ep.fx.webapi.utils.weixin;

import com.bitauto.ep.fx.webapi.utils.web.HttpUtils;
import com.github.rholder.retry.RetryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class WeiXinUtil {

	private static Logger log = LoggerFactory.getLogger(WeiXinUtil.class);  
	//微信的请求url
	//重定向到登录页面
	public final static String REDIRECT_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={{appid}}&redirect_uri={{redirectUrl}}&response_type=code&scope=snsapi_base&agentid={{agentid}}&state={{state}}#wechat_redirect";
	//获取成员UserID 和 成员票据
	public final static String USER_ACCESS_TOKEN= "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token={{accessToken}}&code={{code}";
	public final static String QYWX_KEY="qywxLogin:";
	/**
	 *  重定向企业微信 登录
	 * @param request
	 * @param response
	 * @param appid	  配置文件的appid
	 * @param agentid  配置文件的agentid
	 * @throws IOException
	 */
	public static void redirectUrl(HttpServletRequest request,HttpServletResponse response,String appid,String agentid) throws IOException {
		String requestURL = request.getRequestURL().toString();
		String encodeUrl = URLEncoder.encode(requestURL.substring(7,requestURL.length()),"utf-8");
		String replace = REDIRECT_URL.replace("{{appid}}",appid).replace("{{redirectUrl}}",encodeUrl).replace("{{agentid}}",agentid).replace("{{state}}","state");
		response.sendRedirect(replace);
	}

	/**
	 * 获取登录用户的id 和 票据
	 * @param accessToken  从数据库查询的tonken
	 * @param code 登录用户携带的code
	 * @return {
	 *    "errcode": 0,
	 *    "errmsg": "ok",
	 *    "UserId":"USERID",
	 *    "DeviceId":"DEVICEID",
	 *    "user_ticket": "USER_TICKET"，
	 *    "expires_in":7200
	 * }
	 * @throws Exception
	 */
	public static Map getUserIdAndTicket(String accessToken,String code) throws ExecutionException, RetryException {
		String replace = USER_ACCESS_TOKEN.replace("{{accessToken}}", accessToken).replace("{{code}", code);
		HttpUtils httpUtils=new HttpUtils();
		Map map = httpUtils.Get(replace, 1000, Map.class);
		return  map;
	}
}