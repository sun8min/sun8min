package com.sun8min.seckill.controller;

import com.sun.xml.internal.bind.v2.TODO;
import com.sun8min.capital.api.CapitalService;
import com.sun8min.redpacket.api.RedpacketService;
import com.sun8min.shop.api.ProductService;
import com.sun8min.shop.api.ShopService;
import com.sun8min.shop.entity.Product;
import com.sun8min.shop.entity.Shop;
import com.sun8min.user.api.UserService;
import com.sun8min.user.entity.User;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.List;

// consumer://172.20.10.5/com.sun8min.user.api.gege.userService2?application=consumer&category=consumers&check=false&dubbo=2.5.3&interface=com.sun8min.user.api.gege.userService2&methods=selectAll&pid=2385&revision=1.0.0&side=consumer&timestamp=1553422997200&version=1.0.0
@Controller
public class SeckillController {

//    @Autowired
//    TestFeign testFeign;

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

    @GetMapping("/")
    public String index(String name){
//        List<User> users = testFeign.selectAll();
//        users.forEach(System.out::println);

        List<User> users = userService.selectAll();
        users.forEach(System.out::println);
        System.out.println();

        return "hello "+name+"，this is first messge";
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
        Long capitalAmount = capitalService.findAmountByUserId(userId);
        Long redpacketAmount = redpacketService.findAmountByUserId(userId);
        Product product = productService.selectByPrimaryKey(productId);

        map.addAttribute("title", "确认下单");
        map.addAttribute("product", product);
        map.addAttribute("capitalAmount", capitalAmount);
        map.addAttribute("redpacketAmount", redpacketAmount);
        return "confirmOrder";
    }

    @PostMapping("/placeOrder")
    public String placeOrder(@RequestParam long productId,
                             @RequestParam String redPacketPayAmount,
                             ModelMap map){
        // TODO 暂时先定购买用户id为3，去购买其他两家的商品
        long userId = 3L;
        // 参数校验
        checkParam(productId, userId, redPacketPayAmount);
        // TODO 分布式事务
        // 生成订单
        // 生成红包订单
        // 生成账户订单
        map.addAttribute("title", "支付结果");
        return "payResult";
    }

    private void checkParam(long productId, long payerUserId, String redPacketPayAmount) {
        // 用户输入的支付所用红包余额
        BigDecimal redPacketPayAmountInBigDecimal = new BigDecimal(redPacketPayAmount);
        // 商品价格
        BigDecimal productPrice = new BigDecimal(productService.selectByPrimaryKey(productId).getProductPrice());
        // 校验合法性
        boolean smallThanZero = redPacketPayAmountInBigDecimal.compareTo(BigDecimal.ZERO) < 0;
        boolean bigThanProductPrice = redPacketPayAmountInBigDecimal.compareTo(productPrice) > 0;
        if (smallThanZero || bigThanProductPrice)
            throw new InvalidParameterException("非法红包输入 :" + redPacketPayAmount);
        // 校验余额是否充足
        BigDecimal redpacketAmount = new BigDecimal(redpacketService.findAmountByUserId(payerUserId));
        if (redPacketPayAmountInBigDecimal.compareTo(redpacketAmount) > 0)
            throw new InvalidParameterException("红包余额不足 :" + redPacketPayAmount);
    }

}
