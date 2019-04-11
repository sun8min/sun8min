package com.sun8min.pay.provider.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayRequest;
import com.alipay.api.internal.util.AlipaySignature;
import com.sun8min.pay.provider.alipay.client.MyAlipayClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AlipayService {

    @Autowired
    MyAlipayClient myAlipayClient;

    // out_trade_no	String	必选	64	商户订单号,64个字符以内、可包含字母、数字、下划线；需保证在商户端不重复	20150320010101001
    // product_code	String	必选	64	销售产品码，商家和支付宝签约的产品码。pc端固定值：FAST_INSTANT_TRADE_PAY，手机端固定值：QUICK_WAP_WAY
    // total_amount	Price	必选	9	订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]	88.88
    // subject	String	必选	256	商品的标题/交易标题/订单标题/订单关键字等。	Iphone6 16G
    // body	String	可选	128	对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。	Iphone6 16G
    // time_expire	String	可选	32	绝对超时时间，格式为yyyy-MM-dd HH:mm。 注：1）以支付宝系统时间为准；2）如果和timeout_express参数同时传入，以time_expire为准。	2016-12-31 10:05
    // passback_params	String	可选	512	公用回传参数，如果请求时传递了该参数，则返回给商户时会回传该参数。支付宝只会在同步返回（包括跳转回商户网站）和异步通知时将该参数原样返回。本参数必须进行UrlEncode之后才可以发送给支付宝。	merchantBizType%3d3C%26merchantBizNo%3d2016010101111
    // goods_type	String	可选	2	商品主类型：0—虚拟类商品，1—实物类商品。注：虚拟类商品不支持使用花呗渠道	0
    // timeout_express	String	可选	6	该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。注：若为空，则默认为15d。	90m

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

    /**
     * 验签
     *
     * @param paramsMap
     * @return
     */
    public Boolean AlipayTradePagePay(Map<String, String> paramsMap) {
        //调用SDK验证签名
        boolean signVerified = false;
        try {
            signVerified = AlipaySignature.rsaCheckV1(
                    paramsMap,
                    myAlipayClient.getAlipayPublicKey(),
                    myAlipayClient.getCharset(),
                    myAlipayClient.getSignType());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return signVerified;
    }
}
