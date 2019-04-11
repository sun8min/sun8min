package com.sun8min.seckill.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayObject;
import com.sun8min.account.api.AccountService;
import com.sun8min.base.util.EnumUtils;
import com.sun8min.order.api.OrderService;
import com.sun8min.order.entity.Order;
import com.sun8min.pay.api.PayService;
import com.sun8min.product.api.ProductService;
import com.sun8min.product.api.ProductSnapshotService;
import com.sun8min.product.api.ShopService;
import com.sun8min.product.entity.Product;
import com.sun8min.product.entity.ProductSnapshot;
import com.sun8min.product.entity.Shop;
import com.sun8min.seckill.dto.PlaceOrderRequestDTO;
import com.sun8min.seckill.repository.SeckillOrderRepository;
import com.sun8min.seckill.service.SeckillService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 秒杀
 */
// consumer://172.20.10.5/com.sun8min.user.api.gege.userService2?application=consumer&category=consumers&check=false&dubbo=2.5.3&interface=com.sun8min.user.api.gege.userService2&methods=selectAll&pid=2385&revision=1.0.0&side=consumer&timestamp=1553422997200&version=1.0.0
@Controller
@RequestMapping("/seckill")
public class SeckillController {

    @Reference(version = "${service.version}")
    ShopService shopService;
    @Reference(version = "${service.version}")
    ProductService productService;
    @Reference(version = "${service.version}")
    ProductSnapshotService productSnapshotService;
    @Reference(version = "${service.version}")
    AccountService accountService;
    @Reference(version = "${service.version}")
    OrderService orderService;
    @Reference(version = "${service.version}")
    PayService payService;
    @Autowired
    SeckillOrderRepository seckillOrderRepository;
    @Autowired
    SeckillService seckillService;
    @Autowired
    HttpServletRequest httpServletRequest;

    private static final String prefix = "seckill";
    private static final String prefixPath = prefix + "/";

    /**
     * 商家列表
     *
     * @param map
     * @return
     */
    @GetMapping("/shops")
    public String findShops(ModelMap map) {
        List<Shop> shops = shopService.list();
        map.addAttribute("prefix", prefix);
        map.addAttribute("title", "商家列表");
        map.addAttribute("shops", shops);
        return prefixPath + "shops";
    }

    /**
     * 商品列表
     *
     * @param shopId 商家id
     * @param map
     * @return
     */
    @GetMapping("/shop/{shopId}")
    public String findProductsByShopId(@PathVariable BigInteger shopId, ModelMap map) {
        List<Product> products = productService.findByShopId(shopId);
        map.addAttribute("prefix", prefix);
        map.addAttribute("title", "商品列表");
        map.addAttribute("products", products);
        return prefixPath + "products";
    }

    /**
     * 确认下单
     *
     * @param productId 商品id
     * @param map
     * @return
     */
    @GetMapping("/product/{productId}/confirm")
    public String confirm(@PathVariable BigInteger productId, ModelMap map) {
        // TODO 暂时先定购买用户id为3，去购买其他两家的商品
        BigInteger userId = BigInteger.valueOf(3);
        BigDecimal accountAmount = accountService.findAmountByUserId(userId);
        ProductSnapshot productSnapshot = productSnapshotService.findByProductId(productId);

        map.addAttribute("prefix", prefix);
        map.addAttribute("title", "确认下单");
        map.addAttribute("productSnapshot", productSnapshot);
        map.addAttribute("accountAmount", accountAmount);
        map.addAttribute("payChannelCodes", Order.OrderPayChannel.values());
        return prefixPath + "confirmOrder";
    }

    /**
     * 下单
     *
     * @param productSnapshotId 商品快照id
     * @return 重定向地址
     */
    @PostMapping("/placeOrder")
    public String placeOrder(@RequestParam BigInteger productSnapshotId,
                             @RequestParam Integer payChannelCode,
                             ModelMap map) {
        String view = null;
        // TODO 暂时先定购买用户id为3，去购买其他两家的商品
        BigInteger userId = BigInteger.valueOf(3);

        // 1.1 构建下单请求
        PlaceOrderRequestDTO placeOrderRequestDTO = seckillOrderRepository.buildQuestDTO(userId, productSnapshotId);
        // 1.2 生成订单
        Order order = seckillService.placeOrder(placeOrderRequestDTO);

        // 2 交易支付方式：账户、支付宝、微信
        Order.OrderPayChannel payChannel = EnumUtils.getEnum(Order.OrderPayChannel.class, payChannelCode);
        // 支付结果跳转界面
        String redirectUrl = "payResult/" + order.getTradeOrderNo();
        // 账户支付
        if (payChannel == Order.OrderPayChannel.ACCOUNT) {
            seckillService.payOrder(order);
            view = "redirect:" + redirectUrl;
        }
        // 支付宝支付
        else if (payChannel == Order.OrderPayChannel.AILPAY) {
            AlipayObject bizModel = seckillOrderRepository.buildAlipayRequest(order);
            // 回跳页面地址
            String baseUrl = httpServletRequest.getRequestURL().toString().replace(httpServletRequest.getRequestURI(), "/") + prefixPath;
            String returnUrl = baseUrl + redirectUrl;
            // 支付结果异步通知地址
            String notifyUrl = baseUrl + "alipayBack/" + order.getTradeOrderNo();
            map.addAttribute("msg", payService.tradePagePay(bizModel, returnUrl, notifyUrl));
            map.addAttribute("title", "支付界面");
            view = "pay";
        }
        // 微信支付
        else if (payChannel == Order.OrderPayChannel.WECHAT) {
            // 微信沙箱需要真实商户，暂搁
        }
        // 其他未知类型
        else {
            // TODO 全局错误异常处理与页面
            throw new RuntimeException("支付类型错误");
        }
        return view;
    }

    /**
     * 支付结果
     *
     * @param tradeOrderNo
     * @param map
     * @return
     */
    @GetMapping("/payResult/{tradeOrderNo}")
    public String payResult(@PathVariable String tradeOrderNo, ModelMap map) {
        // TODO 暂时先定购买用户id为3，去购买其他两家的商品
        BigInteger userId = BigInteger.valueOf(3);

        // 订单支付结果
        String payResultTip = Optional.ofNullable(orderService.findByTradeOrderNo(tradeOrderNo))
                .map(Order::getOrderStatus)
                .map(status -> EnumUtils.getEnumMsg(Order.OrderStatus.class, status))
                .orElse("Unknown");

        // 账户余额
        BigDecimal accountAmount = accountService.findAmountByUserId(userId);

        map.addAttribute("payResultTip", payResultTip);
        map.addAttribute("accountAmount", accountAmount);

        map.addAttribute("prefix", prefix);
        map.addAttribute("title", "支付结果");
        return prefixPath + "payResult";
    }

    /**
     * 支付宝支付回调
     *
     * @param tradeOrderNo
     */
    @ResponseBody
    @PostMapping("/alipayBack/{tradeOrderNo}")
    public String alipayBack(@PathVariable String tradeOrderNo){
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        //将异步通知中收到的所有参数都存放到map中
        Map<String, String> paramsMap = new HashMap<>();
        parameterMap.entrySet().forEach(entry -> {
            String[] values = entry.getValue();
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr += values[i] + (i == values.length - 1 ? "" : ",");
            }
            //乱码解决，这段代码在出现乱码时使用
            try {
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                throw new RuntimeException("异步通知参数编码转换异常");
            }
            paramsMap.put(entry.getKey(), valueStr);
        });

        Boolean signVerified = payService.alipaySignCheck(paramsMap);
        if (signVerified) {
            // TODO 验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
            // 获取额外参数对象
            JSONObject passbackParams;
            try {
                passbackParams = JSON.parseObject(URLDecoder.decode(paramsMap.get("passback_params"), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                throw new RuntimeException("额外参数使用URL编码异常");
            }
            seckillService.paySuccess(tradeOrderNo, passbackParams.getLong("version"));
        } else {
            // TODO 验签失败则记录异常日志，并在response中返回failure.
        }
        return signVerified ? "success" : "failure";
    }
}
