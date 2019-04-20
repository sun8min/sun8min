package com.sun8min.pay.provider;

import com.alipay.api.AlipayObject;
import com.alipay.api.AlipayRequest;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.sun8min.pay.api.PayService;
import com.sun8min.pay.provider.alipay.AlipayService;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-06
 */
@Service(version = "${service.version}")
public class PayServiceImpl implements PayService {

    @Resource
    AlipayService alipayService;

    @Override
    public String tradePagePay(AlipayObject bizModel, String returnUrl, String notifyUrl) {
        AlipayRequest request;
        // PC下单请求model
        if (bizModel instanceof AlipayTradePagePayModel) {
            request = new AlipayTradePagePayRequest();
        }
        // 手机下单请求model
        else if (bizModel instanceof AlipayTradeWapPayModel) {
            request = new AlipayTradeWapPayRequest();
        }
        // 其他未知model
        else {
            throw new RuntimeException("请求实体错误");
        }
        request.setBizModel(bizModel);
        request.setReturnUrl(returnUrl);
        request.setNotifyUrl(notifyUrl);
        return alipayService.alipayTradePagePay(request);
    }

    @Override
    public String tradeRefund(String tradeOrderNo) {
        return null;
    }

    @Override
    public String tradeFastpayRefundQuery(String tradeOrderNo) {
        return null;
    }

    @Override
    public String tradeQuery(String tradeOrderNo) {
        return null;
    }

    @Override
    public Boolean tradeClose(String tradeOrderNo) {
        return null;
    }

    @Override
    public String billDownload(Date billDate) {
        return null;
    }

    @Override
    public Boolean alipaySignCheck(Map<String, String> paramsMap) {
        return alipayService.alipaySignCheck(paramsMap);
    }
}
