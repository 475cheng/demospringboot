package com.bitauto.ep.fx.webapi.utils.web;

import com.alibaba.fastjson.JSON;
import com.github.rholder.retry.*;
import com.google.common.base.Predicates;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.rmi.RemoteException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**

 * Http请求类，用于请求外部HTTP服务调用

 * @author zhanglei13

 * @Time 2018-1-17

 *

 */
public class HttpUtils {

    public  HttpUtils(RestTemplate restapi){
        this.restTemplate = restapi;
    }

    public  HttpUtils(){
        this.restTemplate = new RestTemplate();
    }
    //Spring Boot中的Rest api 访问对象
    private RestTemplate restTemplate;

    /**
     * 请求URI地址，返回JSON处理后的实体
     * @param url URI
     * @param postData Post请求的内容
     * @param clazz JSON返回的对应实体类型
     * @param <T>
     * @return T实体
     * @throws ExecutionException
     * @throws RetryException
     */
    public <T> T Request(String url, MultiValueMap<String, String> postData, Class<T> clazz) throws ExecutionException, RetryException {
        //StringBuilder urlBuilder = new StringBuilder(url);
        //urlBuilder.append(Token).append("/").append(Md5);
        T result = null;
        Retryer<String> retryHelper = RetryerBuilder.<String>newBuilder()
                //抛出runtime异常、checked异常时都会重试，但是抛出error不会重试。
                .retryIfException()
                //返回false也需要重试
                .retryIfResult(Predicates.isNull())
                //重调策略
                .withWaitStrategy(WaitStrategies.fixedWait(500, TimeUnit.MILLISECONDS))
                //尝试次数
                .withStopStrategy(StopStrategies.stopAfterAttempt(2))
                .build();

        String html = retryHelper.call(()->{
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(postData, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
            String content = response.getBody();
            if(StringUtils.isEmpty(content))
                throw new RemoteException("http调用失败");
            return content;
        });

        result = JSON.parseObject(html, clazz);

        return result;
    }

    /**
     * 请求URI地址，返回JSON处理后的实体
     * @param url URI
     * @param postData Post请求的内容
     * @return T实体
     * @throws ExecutionException
     * @throws RetryException
     */
    public String requestContent(String url, String postData) throws ExecutionException, RetryException {
        Retryer<String> retryHelper = RetryerBuilder.<String>newBuilder()
                //抛出runtime异常、checked异常时都会重试，但是抛出error不会重试。
                .retryIfException()
                //返回false也需要重试
                .retryIfResult(Predicates.isNull())
                //重调策略
                .withWaitStrategy(WaitStrategies.fixedWait(500, TimeUnit.MILLISECONDS))
                //尝试次数
                .withStopStrategy(StopStrategies.stopAfterAttempt(2))
                .build();

        String html = retryHelper.call(()->{
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<String> requestEntity = new HttpEntity<>(postData, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
            String content = response.getBody();
            if(StringUtils.isEmpty(content))
                throw new RemoteException("http调用失败");
            return content;
        });
        return html;
    }

    /**
     * 请求URI地址，返回JSON处理后的实体
     * @param url URI
     * @param clazz JSON返回的对应实体类型
     * @param <T>
     * @return T实体
     * @throws ExecutionException
     * @throws RetryException
     */
    public <T> T Get(String url,Integer timeout, Class<T> clazz) throws ExecutionException, RetryException {
        T result = null;
        Retryer<String> retryHelper = RetryerBuilder.<String>newBuilder()
                //抛出runtime异常、checked异常时都会重试，但是抛出error不会重试。
                .retryIfException()
                //返回false也需要重试
                .retryIfResult(Predicates.isNull())
                //重调策略
                .withWaitStrategy(WaitStrategies.fixedWait(timeout, TimeUnit.MILLISECONDS))
                //尝试次数
                .withStopStrategy(StopStrategies.stopAfterAttempt(2))
                .build();

        String html = retryHelper.call(()->{
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            String content = response.getBody();
            if(StringUtils.isEmpty(content))
                throw new RemoteException("http调用失败");
            return content;
        });

        result = JSON.parseObject(html, clazz);

        return result;
    }

    /**
     * 请求URI地址，返回JSON处理后的实体
     * @param url URI
     * @param clazz JSON返回的对应实体类型
     * @param <T>
     * @return T实体
     * @throws ExecutionException
     * @throws RetryException
     */
    public <T> T Get(String url,Integer timeout, Class<T> clazz, int num) throws ExecutionException, RetryException {
        T result = null;
        Retryer<String> retryHelper = RetryerBuilder.<String>newBuilder()
                //抛出runtime异常、checked异常时都会重试，但是抛出error不会重试。
                .retryIfException()
                //返回false也需要重试
                .retryIfResult(Predicates.isNull())
                //重调策略
                .withWaitStrategy(WaitStrategies.fixedWait(timeout, TimeUnit.MILLISECONDS))
                //尝试次数
                .withStopStrategy(StopStrategies.stopAfterAttempt(num))
                .build();

        String html = retryHelper.call(()->{
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            String content = response.getBody();
            if(StringUtils.isEmpty(content))
                throw new RemoteException("http调用失败");
            return content;
        });

        result = JSON.parseObject(html, clazz);

        return result;
    }
}
