package com.chou.securityDemo.inf.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;
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
    public String getSerialNumber(String key, String prefix, Integer length) {
        // TODO 加锁防止在高并发场景下生成重复编码
        //RLock rLock = redissonClient.getLock(key);
        String formatDay = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        // 计算key的过期时间
        long endOfDay = DateUtil.endOfDay(new DateTime()).getTime();
        // 失效时间
        long ttlTime = endOfDay - DateUtil.date().getTime();
        // 获取自增序列
        RAtomicLong rAtomicLong = redissonClient.getAtomicLong(key);
        // 设置超时时间
        long currentValue = rAtomicLong.get();
        long incremented = rAtomicLong.incrementAndGet();
        if (currentValue == 0) {
            // 需要提前初始化，才能设置超时时间
            rAtomicLong.expire(Duration.ofMillis(ttlTime));
        }
        return prefix + formatDay + getSequence(incremented, length);
    }

    public static void main(String[] args) {
        long endOfDay = DateUtil.endOfDay(new DateTime()).getTime();
        System.out.println("endOfDay >>>>" + endOfDay);
        long time = DateUtil.date().getTime();
        System.out.println("time >>>>" + time);
        System.out.println("System.currentTimeMillis()" + System.currentTimeMillis());
    }


    /**
     * 补全自增的数据
     *
     * @param seq
     * @param length
     * @return String
     */
    private String getSequence(long seq, int length) {
        String str = String.valueOf(seq);
        int len = str.length();
        // 取决于业务规模,应该不会到达4
        if (len >= length) {
            return str;
        }
        int rest = length - len;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rest; i++) {
            sb.append('0');
        }
        sb.append(str);
        return sb.toString();
    }
}
