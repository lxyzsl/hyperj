package com.hyperj.common.utils.redis;

import com.hyperj.common.enums.CacheType;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * redis字符串缓存工具类
 */
@Component
@Data
public class RedisStringCache {

    private static RedisStringCache redisStringCache;

    private RedisStringCache(){};

    @Autowired
    private StringRedisTemplate template;

    @Value("${cacheExpire.captcha}")
    private int captchaExpireTime;


    @PostConstruct
    public void init() {
        redisStringCache = new RedisStringCache();
        redisStringCache.setTemplate(template);
        redisStringCache.setCaptchaExpireTime(captchaExpireTime);
    }

    /**
     * 增加缓存
     * @param key
     * @param value
     * @param cacheType
     */
    public static void cache(String key, String value, CacheType cacheType){

        int expireTime;
        switch(cacheType){
            case CAPTCHA:
                expireTime = redisStringCache.getCaptchaExpireTime();
                break;
            default:
                expireTime = 10;
        }

        redisStringCache.getTemplate().opsForValue().set(cacheType.type() + key,value,expireTime, TimeUnit.SECONDS);
    }

    /**
     * 查询缓存
     * @param key
     * @param cacheType
     * @return
     */
    public static String  get(String key,CacheType cacheType){
        return redisStringCache.getTemplate().opsForValue().get(cacheType.type() + key);

    }

    /**
     * 删除缓存
     * @param key
     * @param cacheType
     */
    public static void remove(String key,CacheType cacheType){
        redisStringCache.getTemplate().delete(cacheType.type() + key);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     */
    public <T> void setCacheObject(final String key, final T value)
    {
        redisStringCache.getTemplate().opsForValue().set(key, (String) value);
    }

    public static <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit)
    {
        redisStringCache.getTemplate().opsForValue().set(key, String.valueOf(value), timeout, timeUnit);
    }

    /**
     * 设置有效时间
     *
     * @param key Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout)
    {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key Redis键
     * @param timeout 超时时间
     * @param unit 时间单位
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit)
    {
        return redisStringCache.getTemplate().expire(key, timeout, unit);
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public String getCacheObject(final String key)
    {
        ValueOperations<String, String> operation =  redisStringCache.getTemplate().opsForValue();
        return operation.get(key);
    }


    /**
     * 删除单个对象
     *
     * @param key
     */
    public boolean delete(final String key)
    {
        return redisStringCache.getTemplate().delete(key);
    }

    public Boolean hasKey(final String key){
        return redisStringCache.getTemplate().hasKey(key);
    }

}
