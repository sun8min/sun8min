package com.sun8min.seckill;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 位操作
 * 左移( << )、右移( >> ) 、无符号右移( >>> ) 、位与( & ) 、位或( | )、位非( ~ )、位异或( ^ )，除了位非( ~ )是一元操作符外，其它的都是二元操作符
 * 1、
 * a & a = a
 * a | a = a
 * a ^ a = 0
 * <p>
 * 2、
 * a & 0 = 0
 * a | 0 = a
 * a ^ 0 = a
 * <p>
 * 3、
 * a | ( a & b ) = a
 * a & ( a | b ) = a
 * <p>
 * 4、交换值
 * a ^= b;
 * b ^= a;
 * a ^= b;
 * <p>
 * 5、判断奇偶(取出最后一位)
 * a & 1 等价于 a % 2(结果等于,位运算效率高)
 * <p>
 * <p>
 * 6、比较两值是否相等
 * a ^ b ==0
 * <p>
 * 7、i+1位 置1
 * a |=1<<i
 * <p>
 * 8、i+1位 置0
 * a &=~(1<<i)
 * <p>
 * 9、取出i+1位(联系第5点)
 * a & (1<<i)
 * <p>
 * 10、在对应i+1位，插入b的对应位；
 * a |=1<<i; （a的bit位置1）
 * a & (b & 1<<i) （与b的bit位相与）
 * <p>
 * 11、删除最后的1；
 * a & (a-1)
 * <p>
 * 12、负数
 * -a = ~a+1
 * <p>
 * 13、仅保留最后一个1;
 * a&(-a)
 * <p>
 * 14、得到全1
 * ~0
 * <p>
 * 15、保留最后i-1位；
 * a & ((1<<i)-1)
 * <p>
 * 16、清零最后i-1位；
 * a & ~((1<<i)-1)
 * <p>
 * 17、判断最高位是否为1
 * a<0
 * <p>
 * 18、得到最高位的1；
 * a = a |(a>>1);
 * a = a |(a>>2);
 * a = a |(a>>4);
 * a = a |(a>>8);
 * a = a |(a>>16);
 * return (a+1)>>1;
 */
@Slf4j
public class BitManipulationTests {

    @Test
    public void test() {
        // 原先flag标示
        long flag = 0;
        log(flag);

        // 开1
        flag = openActivity(flag, 1);
        log(flag);

        // 开3
        flag = openActivity(flag, 3);
        log(flag);

        // 关1
        flag = closeActivity(flag, 1);
        log(flag);

        // 关2
        flag = closeActivity(flag, 2);
        log(flag);

        // 关3
        flag = closeActivity(flag, 3);
        log(flag);

        // 开2
        flag = openActivity(flag, 2);
        log(flag);

        for (int i = 1; i <= 3; i++) {
            log.info(isActivityOpen(flag, i) ? "开启" : "关闭");
        }
    }

    /**
     * 关闭活动
     *
     * @param flag 原先flag标示
     * @param n    第n个活动,从右往左数,从1开始
     * @return 新flag标示
     */
    private long closeActivity(long flag, int n) {
        return flag & ~(1 << (n - 1));
    }

    /**
     * 开启活动
     *
     * @param flag 原先flag标示
     * @param n    第n个活动,从右往左数,从1开始
     * @return 新flag标示
     */
    private long openActivity(long flag, int n) {
        return flag | 1 << (n - 1);
    }

    /**
     * 活动是否开启（true 开启，false 关闭）
     *
     * @param flag 原先flag标示
     * @param n    第n个活动,从右往左数,从1开始
     * @return 是否开启
     */
    private boolean isActivityOpen(long flag, int n) {
        return (flag & (1 << (n - 1))) != 0;
    }

    /**
     * 打印
     *
     * @param num
     */
    private void log(long num) {
        log.info("十进制数 ： " + num + ", 二进制数 ： " + Long.toBinaryString(num));
    }
}
