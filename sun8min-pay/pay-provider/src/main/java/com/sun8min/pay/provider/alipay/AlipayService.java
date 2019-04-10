package com.sun8min.pay.provider.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayRequest;
import com.sun8min.pay.provider.alipay.client.MyAlipayClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlipayService {

    @Autowired
    MyAlipayClient myAlipayClient;

    /**
     * 下单并支付页面
     */
    public String AlipayTradePagePay(AlipayRequest request) {
        AlipayClient alipayClient = myAlipayClient.instance();
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return form;
    }
}
