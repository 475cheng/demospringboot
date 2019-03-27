package com.bitauto.ep.fx.webapi.configuration.weixin;



import com.bitauto.ep.fx.webapi.utils.cache.RedisUtils;
import com.bitauto.ep.fx.webapi.configuration.weixin.hadler.*;
import me.chanjar.weixin.mp.api.*;
import me.chanjar.weixin.mp.api.impl.WxMpCardServiceImpl;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.constant.WxMpEventConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static me.chanjar.weixin.common.api.WxConsts.*;

/**
 * 微信公众号配置类
 *
 */
@Configuration
@EnableConfigurationProperties(WechatMpProperties.class)  //@ConfigurationProperties 这个注解在 WechatMpProperties 类上生效 才能获取bean 注入
public class WechatMpConfiguration {
  @Autowired
  protected LogHandler logHandler;
  @Autowired
  protected NullHandler nullHandler;
  @Autowired
  protected KfSessionHandler kfSessionHandler;
  @Autowired
  protected StoreCheckNotifyHandler storeCheckNotifyHandler;
  @Autowired
  private WechatMpProperties properties;
  @Autowired
  private LocationHandler locationHandler;
  @Autowired
  private MenuHandler menuHandler;
  @Autowired
  private MsgHandler msgHandler;
  @Autowired
  private UnsubscribeHandler unsubscribeHandler;
  @Autowired
  private SubscribeHandler subscribeHandler;
  @Autowired
  private RedisUtils redisUtils;
  private WxMpService wxMpService;
  @Bean
  @ConditionalOnMissingBean   // WxMpConfigStorage类存在 不在创建新的bean   单例
  public WxMpConfigStorage configStorage() {
    WxMpInMemoryConfigStorage configStorage = new WxMpInRedisConfigStorage(redisUtils);
    configStorage.setAppId(this.properties.getAppId());
    configStorage.setSecret(this.properties.getSecret());
    configStorage.setToken(this.properties.getToken());
    configStorage.setAesKey(this.properties.getAesKey());
    return configStorage;
  }

  @Bean
  //@ConditionalOnClass(WxMpConfigStorage.class) 当WxMpConfigStorage 存在类路径上时 才加载WxMpService
  @ConditionalOnMissingBean
  public WxMpService wxMpService(WxMpConfigStorage configStorage) {
//        WxMpService wxMpService = new me.chanjar.weixin.mp.api.impl.okhttp.WxMpServiceImpl();
//        WxMpService wxMpService = new me.chanjar.weixin.mp.api.impl.jodd.WxMpServiceImpl();
//        WxMpService wxMpService = new me.chanjar.weixin.mp.api.impl.apache.WxMpServiceImpl();
    WxMpService wxMpService = new WxMpServiceImpl();
    wxMpService.setWxMpConfigStorage(configStorage);
    this.wxMpService=wxMpService;
    return wxMpService;
  }
  @Bean
  //@ConditionalOnClass(WxMpService.class)
  @ConditionalOnMissingBean
  public WxMpCardService wxMpCardService() {
    return  new WxMpCardServiceImpl(this.wxMpService);
  }

  @Bean
  public WxMpMessageRouter router(WxMpService wxMpService) {
    final WxMpMessageRouter newRouter = new WxMpMessageRouter(wxMpService);

    // 记录所有事件的日志 （异步执行）
    newRouter.rule().handler(this.logHandler).next();

    // 接收客服会话管理事件
    newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
        .event(WxMpEventConstants.CustomerService.KF_CREATE_SESSION)
        .handler(this.kfSessionHandler).end();
    newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
        .event(WxMpEventConstants.CustomerService.KF_CLOSE_SESSION)
        .handler(this.kfSessionHandler)
        .end();
    newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
        .event(WxMpEventConstants.CustomerService.KF_SWITCH_SESSION)
        .handler(this.kfSessionHandler).end();

    // 门店审核事件
    newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
        .event(WxMpEventConstants.POI_CHECK_NOTIFY)
        .handler(this.storeCheckNotifyHandler).end();

    // 自定义菜单事件
    newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
        .event(MenuButtonType.CLICK).handler(this.getMenuHandler()).end();

    // 点击菜单连接事件
    newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
        .event(MenuButtonType.VIEW).handler(this.nullHandler).end();

    // 关注事件
    newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
        .event(EventType.SUBSCRIBE).handler(this.getSubscribeHandler())
        .end();

    // 取消关注事件
    newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
        .event(EventType.UNSUBSCRIBE)
        .handler(this.getUnsubscribeHandler()).end();

    // 上报地理位置事件
    newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
        .event(EventType.LOCATION).handler(this.getLocationHandler())
        .end();

    // 接收地理位置消息
    newRouter.rule().async(false).msgType(XmlMsgType.LOCATION)
        .handler(this.getLocationHandler()).end();

    // 扫码事件
    newRouter.rule().async(false).msgType(XmlMsgType.EVENT)
        .event(EventType.SCAN).handler(this.getScanHandler()).end();

    // 默认
    newRouter.rule().async(false).handler(this.getMsgHandler()).end();

    return newRouter;
  }

  protected MenuHandler getMenuHandler() {
    return this.menuHandler;
  }

  protected SubscribeHandler getSubscribeHandler() {
    return this.subscribeHandler;
  }

  protected UnsubscribeHandler getUnsubscribeHandler() {
    return this.unsubscribeHandler;
  }

  protected AbstractHandler getLocationHandler() {
    return this.locationHandler;
  }

  protected MsgHandler getMsgHandler() {
    return this.msgHandler;
  }

  protected AbstractHandler getScanHandler() {
    return null;
  }

}
