package com.chou.securityDemo;

import cn.hutool.core.lang.UUID;
import com.chou.securityDemo.domain.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.RedissonSet;
import org.redisson.api.*;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.Charset;
import java.time.Duration;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest
class SecurityDemoApplicationTests {

	@Autowired
	private RedissonClient redissonClient;

	@Test
	void testRedisson(){
		//setType();
		//stringType();
		//mapType();
		//zSetType();
		//listType();
		increm();
	}

	private void increm() {
		long incremented = redissonClient.getAtomicLong("User").incrementAndGet();
		log.info(">>>>>>>>>>>>>>>>>>>>>>> {}", incremented);
	}

	private void listType() {
		RLiveObjectService liveObjectService = redissonClient.getLiveObjectService();
		liveObjectService.attach(new User());

		final RList<Object> rList = redissonClient.getList("l1");
		rList.add("tom");
		rList.add("king");
		rList.add("jack");
		rList.expire(500,TimeUnit.MILLISECONDS);
		String lValue = (String) rList.get(1);

	}

	private void zSetType() {
		final RScoredSortedSet<Object> rScoredSortedSet = redissonClient.getScoredSortedSet("zs1");
		rScoredSortedSet.addScore("tom",3.0);
		rScoredSortedSet.addScore("king",3.5);
		//通过key获取所有的value
		redissonClient.getScoredSortedSet("zs1");

	}

	private void stringType() {
		//RBucket<Object> rBucket = redissonClient.getBucket("test");
		//String name = (String) rBucket.get();
		//log.info("com.chou.securityDemo.SecurityDemoApplicationTests.testRedisson#redissonClient.getBucket >>>> {}",name);
		RBucket<Object> clientBucket = redissonClient.getBucket("token");
		//clientBucket.set("new Object");
		String token = (String) clientBucket.get();
		log.info("com.chou.securityDemo.SecurityDemoApplicationTests.testRedisson#redissonClient.getBucket >>>> {}",token);

	}

	private  void mapType(){
		final RMap<Object, Object> rMap = redissonClient.getMap("m1");
		rMap.put("id","1");
		rMap.put("name","jk");
		rMap.put("aka","zhoucj");
		rMap.expire(Duration.ofSeconds(30));
		String mValue = (String) rMap.get("name");
		//RMap<Object, Object> map = redissonClient.getMap("m1");
		//String aka = (String) map.get("aka");
		log.info("com.chou.securityDemo.SecurityDemoApplicationTests.testRedisson#redissonClient.rMap >>>> {}",rMap);
		log.info("com.chou.securityDemo.SecurityDemoApplicationTests.testRedisson#redissonClient.getSet >>>> {}",mValue);

	}

	private void setType() {
		RSet<Object> clientSet = redissonClient.getSet("myName");
		clientSet.add("李四");
		clientSet.add("张三");
		clientSet.add("王五");
		RedissonSet<Object> myName = (RedissonSet<Object>) redissonClient.getSet("myName");
		Iterator<Object> iterator = myName.iterator();
		while (iterator.hasNext()){
			String next = (String) iterator.next();
			log.info("com.chou.securityDemo.SecurityDemoApplicationTests.testRedisson#redissonClient.getSet >>>> {}",next);
		}
	}
}
