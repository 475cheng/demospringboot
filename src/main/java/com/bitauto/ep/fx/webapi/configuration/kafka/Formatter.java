package com.bitauto.ep.fx.webapi.configuration.kafka;

import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * 定义格式化接口
 *
 */
public interface Formatter {
    String format(ILoggingEvent event);
}
