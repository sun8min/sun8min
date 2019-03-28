package com.sun8min.seckill.controller;

import com.google.common.base.Strings;
import com.sun8min.capital.api.CapitalService;
import com.sun8min.capital.api.CapitalTradeOrderService;
import com.sun8min.order.api.OrderLineService;
import com.sun8min.order.api.OrderService;
import com.sun8min.order.entity.Order;
import com.sun8min.redpacket.api.RedpacketService;
import com.sun8min.redpacket.api.RedpacketTradeOrderService;
import com.sun8min.shop.api.ProductService;
import com.sun8min.shop.api.ShopService;
import com.sun8min.shop.entity.Product;
import com.sun8min.shop.entity.Shop;
import com.sun8min.user.api.UserService;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.*;

// consumer://172.20.10.5/com.sun8min.user.api.gege.userService2?application=consumer&category=consumers&check=false&dubbo=2.5.3&interface=com.sun8min.user.api.gege.userService2&methods=selectAll&pid=2385&revision=1.0.0&side=consumer&timestamp=1553422997200&version=1.0.0
@Controller
public class SeckillController {

    @Reference(version = "${service.version}")
    UserService userService;
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
    OrderLineService orderLineService;
    @Reference(version = "${service.version}")
    RedpacketTradeOrderService redpacketTradeOrderService;
    @Reference(version = "${service.version}")
    CapitalTradeOrderService capitalTradeOrderService;


    @GetMapping("/")
    public String index(String name){
        return "hello " + name + "，this is first messge";
    }

    @PostMapping("/login")
    public String login(String username, String password){
        // TODO 用户登陆，用于购买商品等操作
//        userService.
        return null;
    }

    @GetMapping("/shops")
    public String findShops(ModelMap map){
        List<Shop> shops = shopService.selectAll();
        map.addAttribute("title", "商家列表");
        map.addAttribute("shops", shops);
        return "shops";
    }

    @GetMapping("/shop/{shopId}")
    public String findProductsByShopId(@PathVariable long shopId, ModelMap map){
        List<Product> products = productService.findByShopId(shopId);
        map.addAttribute("title", "商品列表");
        map.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/product/{productId}/confirm")
    public String confirm(@PathVariable long productId, ModelMap map){
        // TODO 暂时先定购买用户id为3，去购买其他两家的商品
        long userId = 3L;
        BigDecimal capitalAmount = capitalService.findAmountByUserId(userId);
        BigDecimal redpacketAmount = redpacketService.findAmountByUserId(userId);
        Product product = productService.selectByPrimaryKey(productId);

        map.addAttribute("title", "确认下单");
        map.addAttribute("product", product);
        map.addAttribute("capitalAmount", capitalAmount);
        map.addAttribute("redpacketAmount", redpacketAmount);
        return "confirmOrder";
    }

    @PostMapping("/placeOrder")
    public RedirectView placeOrder(@RequestParam long productId,
                                   @RequestParam  String redpacketPayAmountStr)
                                    throws Exception {
        // TODO 暂时先定购买用户id为3，去购买其他两家的商品
        long userId = 3L;
        PlaceOrderRequest placeOrderRequest = new PlaceOrderRequest(productId, redpacketPayAmountStr, userId).invoke();
        BigDecimal redpacketPayAmount = placeOrderRequest.getRedpacketPayAmount();
        List<Pair<Product, Integer>> productQuantitiesList = placeOrderRequest.getProductQuantitiesList();
        long fromUserId = placeOrderRequest.getFromUserId();
        long toUserId = placeOrderRequest.getToUserId();
        // TODO 分布式事务
        // 下单
        Order order = orderService.placeOrder(fromUserId, toUserId, productQuantitiesList, redpacketPayAmount);

        // 红包交易
        redpacketTradeOrderService.trade(order.getTradeOrderNo(), fromUserId, toUserId, redpacketPayAmount);

        // 账户交易
        capitalTradeOrderService.trade(order.getTradeOrderNo(), fromUserId, toUserId, order.getCapitalTradeAmount());

        return new RedirectView("/payresult/" + order.getTradeOrderNo());
    }

    @GetMapping("/payresult/{tradeOrderNo}")
    public String payResult(@PathVariable long tradeOrderNo, ModelMap map){
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

        map.addAttribute("title", "支付结果");
        return "payResult";
    }

    private class PlaceOrderRequest {
        private long productId;
        private String redpacketPayAmountStr;
        private long userId;
        private BigDecimal redpacketPayAmount;
        private List<Pair<Product, Integer>> productQuantitiesList;
        private long fromUserId;
        private long toUserId;

        public PlaceOrderRequest(long productId, String redpacketPayAmountStr, long userId) {
            this.productId = productId;
            this.redpacketPayAmountStr = redpacketPayAmountStr;
            this.userId = userId;
        }

        public BigDecimal getRedpacketPayAmount() {
            return redpacketPayAmount;
        }

        public List<Pair<Product, Integer>> getProductQuantitiesList() {
            return productQuantitiesList;
        }

        public long getFromUserId() {
            return fromUserId;
        }

        public long getToUserId() {
            return toUserId;
        }

        public PlaceOrderRequest invoke() {
            Product product = productService.selectByPrimaryKey(productId);
            redpacketPayAmount = Strings.isNullOrEmpty(redpacketPayAmountStr) ? BigDecimal.ZERO : new BigDecimal(redpacketPayAmountStr);
            // 参数校验
            checkParam(product, userId, redpacketPayAmount);
            // 订单商品列表
            productQuantitiesList = new ArrayList<>();
            productQuantitiesList.add(new ImmutablePair<>(product, 1));
            // 付款人
            fromUserId = userId;
            // 收款人
            toUserId = shopService.selectByPrimaryKey(product.getShopId()).getUserId();
            return this;
        }

        private void checkParam(Product product, long payerUserId, BigDecimal redpacketPayAmount) {
            // 商品价格
            BigDecimal productPrice = product.getProductPrice();
            // 校验合法性
            boolean smallThanZero = redpacketPayAmount.compareTo(BigDecimal.ZERO) < 0;
            boolean bigThanProductPrice = redpacketPayAmount.compareTo(productPrice) > 0;
            if (smallThanZero) throw new InvalidParameterException("红包金额不能小于0");
            if (bigThanProductPrice) throw new InvalidParameterException("红包金额不能大于商品价格");
        }
    }
}
