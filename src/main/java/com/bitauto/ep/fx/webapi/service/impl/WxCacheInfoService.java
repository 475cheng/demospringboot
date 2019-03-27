package com.bitauto.ep.fx.webapi.service.impl;

import com.bitauto.ep.fx.webapi.dao.WxCacheInfoRepository;
import com.bitauto.ep.fx.webapi.model.WxCacheInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by CodeGenerator on 2018/04/27.
 */
@Service
public class WxCacheInfoService {

    @Autowired
    private WxCacheInfoRepository wxCacheInfoRepository;

    public String getAccessToken(String key){
        WxCacheInfo wxCacheInfo = wxCacheInfoRepository.findOne(key);
        if( null != wxCacheInfo){
            return wxCacheInfo.getCacheValue();
        }
        return "";
    }
}
