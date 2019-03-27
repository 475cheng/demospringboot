package com.bitauto.ep.fx.webapi.configuration.weixin;


import com.bitauto.ep.fx.webapi.utils.cache.RedisUtils;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;

/**
 *  通过redis 维护token 和 ticket 如果token过期定时任务没有执行，底层会刷新token存入redis
 */
public class WxMpInRedisConfigStorage extends WxMpInMemoryConfigStorage {
    private static final String ACCESS_TOKEN_KEY = "wechat_access_token_";
    private static final String JSAPI_TICKET_KEY = "wechat_jsapi_ticket_";
    private static final String CARDAPI_TICKET_KEY = "wechat_cardapi_ticket_";
    protected final RedisUtils redisUtils;
    private String accessTokenKey;
    private String jsapiTicketKey;
    private String cardapiTicketKey;

    public void setCardapiTicketKey(String cardapiTicketKey) {
        this.cardapiTicketKey = cardapiTicketKey;
    }

    public WxMpInRedisConfigStorage(RedisUtils redisUtils) {
        this.redisUtils= redisUtils;
    }

    public void setAppId(String appId) {
        super.setAppId(appId);
        this.accessTokenKey = "wechat_access_token_".concat(appId);
        this.jsapiTicketKey = "wechat_jsapi_ticket_".concat(appId);
        this.cardapiTicketKey = "wechat_cardapi_ticket_".concat(appId);
    }

    public String getAccessToken() {
        return redisUtils.stringGetStringByKey(this.accessTokenKey);
    }

    public boolean isAccessTokenExpired() {
        long ttl = redisUtils.ttl(this.accessTokenKey);
        return ttl < 2L;
    }

    public synchronized void updateAccessToken(String accessToken, int expiresInSeconds) {
        redisUtils.stringSetValueAndExpireTime(this.accessTokenKey,accessToken,(expiresInSeconds-200)*1000 );
    }

    public void expireAccessToken() {
        redisUtils.expire(this.accessTokenKey,0);
    }

    public String getJsapiTicket() {
        return redisUtils.stringGetStringByKey(this.jsapiTicketKey);
    }

    public boolean isJsapiTicketExpired() {
        return redisUtils.ttl(this.jsapiTicketKey) < 2L;
    }

    public synchronized void updateJsapiTicket(String jsapiTicket, int expiresInSeconds) {
        redisUtils.stringSetValueAndExpireTime(this.jsapiTicketKey,jsapiTicket,(expiresInSeconds-200)*1000);
    }

    public void expireJsapiTicket() {
        redisUtils.expire(this.jsapiTicketKey,0);
    }

    public String getCardApiTicket() {
        return redisUtils.stringGetStringByKey(this.cardapiTicketKey);
    }

    public boolean isCardApiTicketExpired() {
        return  redisUtils.ttl(this.cardapiTicketKey) <2L;
    }

    public synchronized void updateCardApiTicket(String cardApiTicket, int expiresInSeconds) {
        redisUtils.stringSetValueAndExpireTime(this.cardapiTicketKey,cardApiTicket,(expiresInSeconds-200)*1000 );
    }

    public void expireCardApiTicket() {
        redisUtils.expire(this.cardapiTicketKey,0);
    }

    public String getAccessTokenKey() {
        return accessTokenKey;
    }

    public void setAccessTokenKey(String accessTokenKey) {
        this.accessTokenKey = accessTokenKey;
    }

    public String getJsapiTicketKey() {
        return jsapiTicketKey;
    }

    public void setJsapiTicketKey(String jsapiTicketKey) {
        this.jsapiTicketKey = jsapiTicketKey;
    }

    public String getCardapiTicketKey() {
        return cardapiTicketKey;
    }
}
