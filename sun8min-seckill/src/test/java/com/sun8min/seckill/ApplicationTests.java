package com.sun8min.seckill;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void test() {
		Object a = "1";
		if (a instanceof Integer) {
			log.info("integer: {}" + a);
		}
		if (a instanceof String) {
			log.info("String: {}" + a);
		}

	}

}
