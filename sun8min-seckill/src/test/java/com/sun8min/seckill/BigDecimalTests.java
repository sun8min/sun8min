package com.sun8min.seckill;

import lombok.extern.java.Log;
import org.junit.Test;

import java.math.BigDecimal;

@Log
public class BigDecimalTests {

	@Test
	public void test(){
		BigDecimal bigDecimal = new BigDecimal("-0333000.0001");
		// 相反数
		System.out.println(bigDecimal.negate());
		// 本身
		System.out.println(bigDecimal.plus());
		// 从左数第一个不是0的数字个数
		System.out.println(bigDecimal.precision());
		// 小数位数（包括0）
		System.out.println(bigDecimal.scale());
		// 正负号（1：正，-1：负）
		System.out.println(bigDecimal.signum());
		// 精度（类似0.001）
		System.out.println(bigDecimal.ulp());
		// 去掉尾数的0
		System.out.println(bigDecimal.stripTrailingZeros());
		// 转字符串
		System.out.println(bigDecimal.toPlainString());
		// 去掉精度后的数值，即去掉小数点
		System.out.println(bigDecimal.unscaledValue());
	}

}
