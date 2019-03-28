package com.sun8min.seckill.controller;

import com.sun8min.capital.api.CapitalService;
import com.sun8min.order.api.OrderLineService;
import com.sun8min.order.api.OrderService;
import com.sun8min.order.api.PlaceOrderService;
import com.sun8min.order.entity.Order;
import com.sun8min.order.entity.OrderLine;
import com.sun8min.redpacket.api.RedpacketService;
import com.sun8min.shop.api.ProductService;
import com.sun8min.shop.api.ShopService;
import com.sun8min.shop.entity.Product;
import com.sun8min.shop.entity.Shop;
import com.sun8min.user.api.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
    PlaceOrderService placeOrderService;


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
    public String placeOrder(@RequestParam long productId,
                             @RequestParam BigDecimal redPacketPayAmount,
                             ModelMap map) throws Exception {
        // TODO 暂时先定购买用户id为3，去购买其他两家的商品
        long userId = 3L;
        Product product = productService.selectByPrimaryKey(productId);
        // 参数校验
        checkParam(product, userId, redPacketPayAmount);
        // TODO 分布式事务
        // 生成订单
        String tradeOrderNo = UUID.randomUUID().toString().replace("-", "");
        // 商品价格
        BigDecimal productPrice = product.getProductPrice();
        long shopId = product.getShopId();
        // 付款人
        long fromUserId = userId;
        // 收款人
        long toUserId = shopService.selectByPrimaryKey(shopId).getUserId();
        BigDecimal capitalAmount = capitalService.findAmountByUserId(userId);
        // 支付账户余额
        BigDecimal capitalPayAmount = productPrice.subtract(redPacketPayAmount);
        if (capitalPayAmount.compareTo(capitalAmount) > 0) throw new InvalidParameterException("账户余额不足");
        // 订单支付状态
        int orderStatus = 0;
        // 订单
        Order order = new Order();
        order.setFromUserId(fromUserId);
        order.setToUserId(toUserId);
        order.setCapitalTradeAmount(capitalPayAmount);
        order.setRedpacketTradeAmount(redPacketPayAmount);
        order.setOrderStatus(orderStatus);
        order.setTradeOrderNo(tradeOrderNo);
        // 订单项
        OrderLine orderLine = new OrderLine();
        orderLine.setProductId(productId);
        orderLine.setProductPrice(productPrice);
        orderLine.setProductQuantity(1L);
        orderLine.setTradeOrderNo(tradeOrderNo);
        Boolean placeOrderRes = placeOrderService.placeOrder(order, Arrays.asList(orderLine));
        if (placeOrderRes == null || !placeOrderRes) throw new Exception("下单失败");

        // 生成红包订单
        // 生成账户订单
        map.addAttribute("title", "支付结果");
        return "payResult";
    }

    private void checkParam(Product product, long payerUserId, BigDecimal redPacketPayAmount) {
        // 商品价格
        BigDecimal productPrice = product.getProductPrice();
        // 校验合法性
        boolean smallThanZero = redPacketPayAmount.compareTo(BigDecimal.ZERO) < 0;
        boolean bigThanProductPrice = redPacketPayAmount.compareTo(productPrice) > 0;
        if (smallThanZero) throw new InvalidParameterException("红包金额不能小于0");
        if (bigThanProductPrice) throw new InvalidParameterException("红包金额不能大于商品价格");
        // 校验余额是否充足
        BigDecimal redpacketAmount = redpacketService.findAmountByUserId(payerUserId);
        boolean bigThanRedpacketAmount = redPacketPayAmount.compareTo(redpacketAmount) > 0;
        if (bigThanRedpacketAmount) throw new InvalidParameterException("输入金额不能大于红包余额");
    }

}
