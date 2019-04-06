package com.sun8min.product.mapper;

import com.sun8min.base.util.EnumUtils;
import com.sun8min.product.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ProductMapperTest {

    @Autowired
    ProductMapper productMapper;

    @Test
    @Transactional
    @Rollback
    public void crud() {
        productMapper.findListByShopId(BigInteger.valueOf(1)).forEach(product -> Optional.ofNullable(product).map(Product::getGmtCreate).ifPresent(time -> log.info(time.toString())));
    }

    @Test
    public void em() {
        log.info(EnumUtils.getEnumMsg(Product.ProductActivityFlag.class, 1));
    }

}