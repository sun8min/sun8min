package com.sun8min.pay.api;

import com.alipay.api.AlipayObject;

import java.util.Date;

public interface PayService {

    /**
     * 下单并支付页面
     * <p>
     * 支付宝pc网站下单，https://docs.open.alipay.com/api_1/alipay.trade.page.pay/
     * 支付宝手机网页下单，https://docs.open.alipay.com/203/107090/
     *
     * @param bizModel  业务实体
     * @param returnUrl 回跳页面地址
     * @param notifyUrl 支付结果异步通知地址
     * @return
     */
    String tradePagePay(AlipayObject bizModel, String returnUrl, String notifyUrl);

    /**
     * 交易退款
     *
     * @param tradeOrderNo 交易单号
     * @return
     */
    String tradeRefund(String tradeOrderNo);

    /**
     * 交易退款查询
     *
     * @param tradeOrderNo 交易单号
     * @return
     */
    String tradeFastpayRefundQuery(String tradeOrderNo);

    /**
     * 线下交易查询
     *
     * @param tradeOrderNo 交易单号
     * @return
     */
    String tradeQuery(String tradeOrderNo);

    /**
     * 交易关闭
     *
     * @param tradeOrderNo 交易单号
     * @return
     */
    Boolean tradeClose(String tradeOrderNo);

    /**
     * 对账单下载
     *
     * @param billDate 交易日期
     * @return
     */
    String billDownload(Date billDate);

}
