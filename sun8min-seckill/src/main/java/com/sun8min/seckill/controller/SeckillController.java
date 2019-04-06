package com.sun8min.seckill.controller;

import com.sun8min.account.api.AccountService;
import com.sun8min.base.util.EnumUtils;
import com.sun8min.order.api.OrderService;
import com.sun8min.order.entity.Order;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

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
    @Autowired
    PlaceOrderRepository placeOrderRepository;
    @Autowired
    SeckillService seckillService;

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
        return prefixPath + "confirmOrder";
    }

    /**
     * 下单
     *
     * @param productId 商品id
     * @return 重定向地址
     */
    @PostMapping("/placeOrder")
    public RedirectView placeOrder(@RequestParam BigInteger productId) {
        // TODO 暂时先定购买用户id为3，去购买其他两家的商品
        BigInteger userId = BigInteger.valueOf(3);
        PlaceOrderRequestDTO placeOrderRequestDTO = placeOrderRepository.buildQuestDTO(userId, productId);
        Order order = seckillService.handleSeckill(placeOrderRequestDTO);
        return new RedirectView("payresult/" + order.getTradeOrderNo());
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
        Order foundOrder = orderService.findByTradeOrderNo(tradeOrderNo);

        // 订单支付结果，null则返回"Unknown"
        String msg = EnumUtils.getEnumMsg(Order.OrderStatus.class, foundOrder.getOrderStatus());
        String payResultTip = Optional.ofNullable(msg).orElse("Unknown");
        // 账户余额
        BigDecimal accountAmount = accountService.findAmountByUserId(foundOrder.getFromUserId());

        map.addAttribute("payResultTip", payResultTip);
        map.addAttribute("accountAmount", accountAmount);

        map.addAttribute("prefix", prefix);
        map.addAttribute("title", "支付结果");
        return prefixPath + "payResult";
    }
}
