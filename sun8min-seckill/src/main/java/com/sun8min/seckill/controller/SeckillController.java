package com.sun8min.seckill.controller;

import com.sun8min.capital.api.CapitalService;
import com.sun8min.capital.api.CapitalTradeOrderService;
import com.sun8min.order.api.OrderService;
import com.sun8min.order.entity.Order;
import com.sun8min.redpacket.api.RedpacketService;
import com.sun8min.redpacket.api.RedpacketTradeOrderService;
import com.sun8min.seckill.service.PlaceOrderRepository;
import com.sun8min.seckill.vo.PlaceOrderRequest;
import com.sun8min.shop.api.ProductService;
import com.sun8min.shop.api.ShopService;
import com.sun8min.shop.entity.Product;
import com.sun8min.shop.entity.Shop;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.math.BigDecimal;
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
    CapitalService capitalService;
    @Reference(version = "${service.version}")
    RedpacketService redpacketService;
    @Reference(version = "${service.version}")
    OrderService orderService;
    @Reference(version = "${service.version}")
    RedpacketTradeOrderService redpacketTradeOrderService;
    @Reference(version = "${service.version}")
    CapitalTradeOrderService capitalTradeOrderService;
    @Autowired
    PlaceOrderRepository placeOrderRepository;

    private static final String prefix = "seckill";
    private static final String prefixPath = prefix + "\\";

    /**
     * 商家列表
     * @param map
     * @return
     */
    @GetMapping("/shops")
    public String findShops(ModelMap map) {
        List<Shop> shops = shopService.selectAll();
        map.addAttribute("prefix", prefix);
        map.addAttribute("title", "商家列表");
        map.addAttribute("shops", shops);
        return prefixPath + "shops";
    }

    /**
     * 商品列表
     * @param shopId 商家id
     * @param map
     * @return
     */
    @GetMapping("/shop/{shopId}")
    public String findProductsByShopId(@PathVariable long shopId, ModelMap map) {
        List<Product> products = productService.findByShopId(shopId);
        map.addAttribute("prefix", prefix);
        map.addAttribute("title", "商品列表");
        map.addAttribute("products", products);
        return prefixPath + "products";
    }

    /**
     * 确认下单
     * @param productId 商品id
     * @param map
     * @return
     */
    @GetMapping("/product/{productId}/confirm")
    public String confirm(@PathVariable long productId, ModelMap map) {
        // TODO 暂时先定购买用户id为3，去购买其他两家的商品
        long userId = 3L;
        BigDecimal capitalAmount = capitalService.findAmountByUserId(userId);
        BigDecimal redpacketAmount = redpacketService.findAmountByUserId(userId);
        Product product = productService.selectByPrimaryKey(productId);

        map.addAttribute("prefix", prefix);
        map.addAttribute("title", "确认下单");
        map.addAttribute("product", product);
        map.addAttribute("capitalAmount", capitalAmount);
        map.addAttribute("redpacketAmount", redpacketAmount);
        return prefixPath + "confirmOrder";
    }

    /**
     * 下单
     * @param productId 商品id
     * @param redpacketPayAmountStr 用户所输入的红包交易额字符串
     * @return 重定向地址
     */
    @PostMapping("/placeOrder")
    public RedirectView placeOrder(@RequestParam long productId,
                                   @RequestParam String redpacketPayAmountStr) {
        // TODO 暂时先定购买用户id为3，去购买其他两家的商品
        long userId = 3L;
        PlaceOrderRequest placeOrderRequest = placeOrderRepository.buildQuest(userId, productId, redpacketPayAmountStr);
        // TODO 分布式事务
        Order order = null;
        try {
            // 下单
            order = orderService.placeOrder(
                    placeOrderRequest.getFromUserId(),
                    placeOrderRequest.getToUserId(),
                    placeOrderRequest.getProductQuantitiesList(),
                    placeOrderRequest.getRedpacketPayAmount()
            );
            // 红包交易
            // 如果交易金额<=0，则不记录
            if (order.getRedpacketTradeAmount().compareTo(BigDecimal.ZERO) > 0) {
                redpacketTradeOrderService.trade(
                        order.getTradeOrderNo(),
                        placeOrderRequest.getFromUserId(),
                        placeOrderRequest.getToUserId(),
                        order.getRedpacketTradeAmount()
                );
            }
            // 账户交易
            // 如果交易金额<=0，则不记录
            if (order.getCapitalTradeAmount().compareTo(BigDecimal.ZERO) > 0) {
                capitalTradeOrderService.trade(
                        order.getTradeOrderNo(),
                        placeOrderRequest.getFromUserId(),
                        placeOrderRequest.getToUserId(),
                        order.getCapitalTradeAmount()
                );
            }
            // TODO 交易完成，订单修改为支付成功
        } catch (Exception e) {
            e.printStackTrace();
            // TODO 交易失败，订单修改为支付失败
        }
        return new RedirectView("payresult/" + order.getTradeOrderNo());
    }

    /**
     * 支付结果
     * @param tradeOrderNo
     * @param map
     * @return
     */
    @GetMapping("/payresult/{tradeOrderNo}")
    public String payResult(@PathVariable String tradeOrderNo, ModelMap map) {
        Order foundOrder = orderService.findByTradeOrderNo(tradeOrderNo);

        // 订单支付结果，null则返回"Unknown"
        String descByValue = Order.OrderStatus.getDescByValue(foundOrder.getOrderStatus());
        String payResultTip = Optional.ofNullable(descByValue).orElse("Unknown");
        // 账户余额
        BigDecimal capitalAmount = capitalService.findAmountByUserId(foundOrder.getFromUserId());
        // 红包余额
        BigDecimal redPacketAmount = redpacketService.findAmountByUserId(foundOrder.getFromUserId());

        map.addAttribute("payResult", payResultTip);
        map.addAttribute("capitalAmount", capitalAmount);
        map.addAttribute("redPacketAmount", redPacketAmount);

        map.addAttribute("prefix", prefix);
        map.addAttribute("title", "支付结果");
        return prefixPath + "payResult";
    }
}
