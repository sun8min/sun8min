package com.sun8min.pay.provider.alipay.client;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MyAlipayClient {

    /**
     * APPID 即创建应用后生成
     */
    @Value("${pay.alipay.app_id}")
    private String APP_ID;

    /**
     * 开发者私钥，由开发者自己生成
     */
    @Value("${pay.alipay.app_private_key}")
    private String APP_PRIVATE_KEY;

    /**
     * 支付宝公钥，由支付宝生成
     */
    @Value("${pay.alipay.alipay_public_key}")
    private String ALIPAY_PUBLIC_KEY;

    /**
     * 编码集，支持GBK/UTF-8
     */
    @Value("${pay.alipay.charset}")
    private String CHARSET;

    // 以下参数不需要修改
    /**
     * 支付宝网关（固定）
     */
    private final String SERVER_URL = "https://openapi.alipaydev.com/gateway.do";
    /**
     * 参数返回格式，只支持json
     */
    public final String FORMAT = "json";
    /**
     * 商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2
     */
    private final String SIGN_TYPE = "RSA2";

    /**
     * 获取实例
     *
     * @return
     */
    public AlipayClient instance() {
        return new DefaultAlipayClient(SERVER_URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
    }

    public String getAlipayPublicKey() {
        return ALIPAY_PUBLIC_KEY;
    }

    public String getCharset() {
        return CHARSET;
    }

    public String getSignType() {
        return SIGN_TYPE;
    }
}
