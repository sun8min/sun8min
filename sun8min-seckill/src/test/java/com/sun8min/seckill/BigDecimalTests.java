package com.sun8min.seckill;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.math.BigDecimal;

@Slf4j
public class BigDecimalTests {

	@Test
	public void test(){
		BigDecimal bigDecimal = new BigDecimal("-0333000.0001");
		// 相反数
		log(bigDecimal.negate());
		// 本身
		log(bigDecimal.plus());
		// 从左数第一个不是0的数字个数
		log(bigDecimal.precision());
		// 小数位数（包括0）
		log(bigDecimal.scale());
		// 正负号（1：正，-1：负）
		log(bigDecimal.signum());
		// 精度（类似0.001）
		log(bigDecimal.ulp());
		// 去掉尾数的0
		log(bigDecimal.stripTrailingZeros());
		// 转字符串
		log(bigDecimal.toPlainString());
		// 去掉精度后的数值，即去掉小数点
		log(bigDecimal.unscaledValue());
	}

	private void log(Object obj) {
		log.info(obj + "");
	}
}
