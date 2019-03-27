package com.bitauto.ep.fx.webapi.dao;


import com.bitauto.ep.fx.webapi.model.WxCacheInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: ShenYC
 * @Describation:
 * @Date: Created in 15:39 2018/4/20
 */
@Repository
public interface WxCacheInfoRepository extends JpaRepository<WxCacheInfo, String> {

    @Query(value = "SELECT * FROM dbo.WxCacheInfo WHERE CacheKey=:cacheKey",nativeQuery = true)
    WxCacheInfo getCacheValue(@Param("cacheKey") String cacheKey);
}