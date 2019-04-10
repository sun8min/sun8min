package com.sun8min.seckill.controller;

import com.alipay.api.AlipayObject;
import com.sun8min.account.api.AccountService;
import com.sun8min.base.util.EnumUtils;
import com.sun8min.order.api.OrderService;
import com.sun8min.order.entity.Order;
import com.sun8min.pay.api.PayService;
import com.sun8min.product.api.ProductService;
import com.sun8min.product.api.ShopService;
import com.sun8min.product.entity.Product;
import com.sun8min.product.entity.Shop;
import com.sun8min.seckill.dto.PlaceOrderRequestDTO;
import com.sun8min.seckill.repository.PlaceOrderRepository;
import com.sun8min.seckill.service.SeckillService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
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
    AccountService accountService;
    @Reference(version = "${service.version}")
    OrderService orderService;
    @Reference(version = "${service.version}")
    PayService payService;
    @Autowired
    PlaceOrderRepository placeOrderRepository;
    @Autowired
    SeckillService seckillService;
    @Autowired
    HttpServletRequest httpServletRequest;

    private static final String prefix = "seckill";
    private static final String prefixPath = prefix + "\\";

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
        Product product = productService.getById(productId);

        map.addAttribute("prefix", prefix);
        map.addAttribute("title", "确认下单");
        map.addAttribute("product", product);
        map.addAttribute("accountAmount", accountAmount);
        map.addAttribute("payChannelCodes", Order.OrderPayChannel.values());
        return prefixPath + "confirmOrder";
    }

    /**
     * 下单
     *
     * @param productId 商品id
     * @return 重定向地址
     */
    @PostMapping("/placeOrder")
    public String placeOrder(BigInteger productId, Integer payChannelCode, ModelMap map) {
        String view = null;
        // TODO 暂时先定购买用户id为3，去购买其他两家的商品
        BigInteger userId = BigInteger.valueOf(3);

        // 构建下单请求
        PlaceOrderRequestDTO placeOrderRequestDTO = placeOrderRepository.buildQuestDTO(userId, productId);
        // 生成订单
        Order order = seckillService.handleSeckill(placeOrderRequestDTO);

        Order.OrderPayChannel payChannel = EnumUtils.getEnum(Order.OrderPayChannel.class, payChannelCode);
        // 账户支付
        if (payChannel == Order.OrderPayChannel.ACCOUNT) {
            String redirectUrl = "payresult/" + order.getTradeOrderNo();
            view = "redirect:" + redirectUrl;
        }
        // 支付宝支付
        else if (payChannel == Order.OrderPayChannel.AILPAY) {
            AlipayObject bizModel = placeOrderRepository.buildAlipayRequest(order);
            // 回跳页面地址
            String returnUrl = "";
            // 支付结果异步通知地址
            String notifyUrl = "";
            map.addAttribute("msg", payService.tradePagePay(bizModel, returnUrl, notifyUrl));
            map.addAttribute("title", "支付界面");
            view = "pay";
        }
        // 微信支付
        else {
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
    @GetMapping("/payresult/{tradeOrderNo}")
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
}
