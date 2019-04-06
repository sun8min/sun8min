package com.sun8min.account.mapper;

import com.sun8min.account.entity.Account;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class AccountMapperTest {

    @Autowired
    AccountMapper accountMapper;

    @Test
    @Transactional
    @Rollback
    public void crud() {
        Optional.ofNullable(accountMapper.selectById(1)).map(Account::getGmtCreate).ifPresent(time -> log.info(time.toString()));
    }

}