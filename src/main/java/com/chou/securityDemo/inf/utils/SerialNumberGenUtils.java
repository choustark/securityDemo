package com.chou.securityDemo.inf.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLock;
import org.redisson.api.RLongAdder;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 编号生成工具类
 *
 * @author by Axel
 * @since 2024/5/19 下午12:51
 */
@Slf4j
@Component(value = "serialNumberGenUtils")
public class SerialNumberGenUtils {

    @Resource
    private RedissonClient redissonClient;

    /**
     * 利用redis 自增键来实现生成自增id
     * 前缀+日期+每天的自增编号
     * @param prefix 前缀
     * @param key key值
     * @param length 编号长度
     * @return 编号
     */
    public String getSerialNumber(String key,String prefix,Integer length) {
        RLock rLock = redissonClient.getLock(key);
        String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        // 计算key的过期时间
        long endOfDay = DateUtil.endOfDay(new DateTime()).getTime();
        // 失效时间
        long ttlTime = endOfDay-DateUtil.date().getTime();
        RAtomicLong rAtomicLong = redissonClient.getAtomicLong(key);
        // 设置超市时间
        boolean expire = rAtomicLong.expire(Instant.ofEpochSecond(ttlTime));
        long incremented = rAtomicLong.incrementAndGet();
        return null;
    }

    public static void main(String[] args) {
        long endOfDay = DateUtil.endOfDay(new DateTime()).getTime();
        System.out.println("endOfDay >>>>" + endOfDay);
        long time = DateUtil.date().getTime();
        System.out.println("time >>>>" + time);
        System.out.println("System.currentTimeMillis()" + System.currentTimeMillis());
    }
}
