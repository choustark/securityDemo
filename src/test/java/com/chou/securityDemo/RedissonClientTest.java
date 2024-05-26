package com.chou.securityDemo;

import com.chou.securityDemo.inf.utils.SerialNumberGenUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * redis 相关测试类
 *
 * @author by Axel
 * @since 2024/5/25 下午11:08
 */
@Slf4j
@SpringBootTest
public class RedissonClientTest {

    @Resource
    private SerialNumberGenUtils serialNumberGenUtils;


    @Test
    public void serialNumberGenUtilsTest(){
        String userNo = serialNumberGenUtils.getSerialNumber("USER", "U", 4);
        log.info(userNo);
    }


}
