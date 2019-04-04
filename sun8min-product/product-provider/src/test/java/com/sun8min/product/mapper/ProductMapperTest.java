package com.sun8min.product.mapper;

import com.sun8min.product.entity.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductMapperTest {

    @Autowired
    ProductMapper productMapper;

    @Test
    @Transactional
    @Rollback
    public void test() {
        Product product = productMapper.selectById(3);
        assertEquals(product.getDeleted(), false);

        productMapper.updateById(product.setDeleted(true));

        Product product2 = productMapper.selectById(3);
        assertEquals(product2.getDeleted(), true);
    }

}