package com.sun8min.product.mapper;

import com.sun8min.product.entity.Category;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class CategoryMapperTest {

    @Resource
    CategoryMapper categoryMapper;

    @Test
    public void test() {
        List<Category> categories = categoryMapper.selectList(null);
        categories.forEach(category -> log.info(category.getCategoryType() + ""));
    }
}