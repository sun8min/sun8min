package com.sun8min.seckill;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class PrimeTests {

    @Test
    public void test(){
        long num = 4263431L;
        log.info(num + ": {}" + isPrime(num));
    }

    public static boolean isPrime(long num) {
        if (num == 1) return false;
        //两个较小数另外处理
        if (num == 2 || num == 3) return true;
        //不在6的倍数两侧的一定不是质数
        if (num % 6 != 1 && num % 6 != 5) return false;
        long tmp = (long) Math.sqrt(num);
        //在6的倍数两侧的也可能不是质数
        for (int i = 5; i <= tmp; i += 6) {
            if (num % i == 0 || num % (i + 2) == 0) return false;
        }
        //排除所有，剩余的是质数
        return true;
    }
}
