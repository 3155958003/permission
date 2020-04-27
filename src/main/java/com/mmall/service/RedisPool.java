package com.mmall.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;

/**
 * @author hx
 * @create 2020-04-25 12:15
 *
 * Redis缓存池
 */

@Service("redisPool")
@Slf4j
public class RedisPool {

    @Resource(name = "shardedJedisPool")
    private ShardedJedisPool shardedJedisPool ;

    /**
     * 获取客户端值
     * @return
     *          返回值
     */
    public ShardedJedis instance() {
        return shardedJedisPool.getResource();
    }

    /**
     * 关闭客户端
     * @param shardedJedis
     *                     客户端值
     */
    public void safeClose(ShardedJedis shardedJedis) {
        try {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        } catch (Exception e) {
            log.error("return redis resource exception", e);
        }
    }


}
