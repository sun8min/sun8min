package com.sun8min.pay.provider.alipay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.sun8min.pay.provider.alipay.client.MyAlipayClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PublicTest {

    private static final Logger log = LoggerFactory.getLogger(PublicTest.class);

    @Autowired
    MyAlipayClient myAlipayClient;

    /**
     * 验签
     *
     * @throws AlipayApiException
     */
    @Test
    public void checkSign() throws AlipayApiException {
        JSONObject jsonObject = JSON.parseObject("{\"gmt_create\":\"2019-04-11 06:43:54\",\"charset\":\"UTF-8\",\"gmt_payment\":\"2019-04-11 06:43:58\",\"notify_time\":\"2019-04-11 06:43:59\",\"subject\":\"jiji\",\"sign\":\"XFowhvmTjWJdG/777C3JOqK5x6VTlYW29aafgiiqh7lhrTYBR5saAwlFyE1KwIL7umpyvj1/qW46lG1rfju8o4684lj2EtNBMf8FkYJ6ddLaIw1PcbQGo+UAJE6PYiZ0qgced4WkkgdXMoVsUJCe9zesQSNFLALpU8LDc+7Pv73zlOxEOWD3R1aN03RttWlRhOnaY88qBM/lKjmD8zAe64mHo483HivZDb9m8Q+LHMi64C1GUPvruExtZ3faXm8VPUxqAKTEGmEpVYFVaFC6A0L6gJQjZYPRWZhdpRsldDjnbV8/1Y/4xJxz76mr6Q1jCHk8KuiFgUeOFa5lYBlDsg==\",\"buyer_id\":\"2088102177870861\",\"passback_params\":\"%7B%22version%22%3A0%7D\",\"invoice_amount\":\"100.00\",\"version\":\"1.0\",\"notify_id\":\"2019041100222064358070861000534948\",\"fund_bill_list\":\"[{\\\"amount\\\":\\\"100.00\\\",\\\"fundChannel\\\":\\\"ALIPAYACCOUNT\\\"}]\",\"notify_type\":\"trade_status_sync\",\"out_trade_no\":\"5686bebb64ca4d6caadd08c2a533a7ca\",\"total_amount\":\"100.00\",\"trade_status\":\"TRADE_SUCCESS\",\"trade_no\":\"2019041122001470861000684737\",\"auth_app_id\":\"2016092600599125\",\"receipt_amount\":\"100.00\",\"point_amount\":\"0.00\",\"app_id\":\"2016092600599125\",\"buyer_pay_amount\":\"100.00\",\"sign_type\":\"RSA2\",\"seller_id\":\"2088102177321264\"}");
        Map<String, String> paramsMap = new HashMap<>();
        jsonObject.getInnerMap().entrySet().forEach(item -> paramsMap.put(item.getKey(), item.getValue().toString()));

        boolean signVerified = AlipaySignature.rsaCheckV1(
                paramsMap,
                myAlipayClient.getAlipayPublicKey(),
                myAlipayClient.getCharset(),
                myAlipayClient.getSignType());
        log.info("signVerified: {}" + (signVerified ? "success" : "failure"));
    }

}
