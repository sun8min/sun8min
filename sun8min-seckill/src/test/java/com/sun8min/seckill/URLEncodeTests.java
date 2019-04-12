package com.sun8min.seckill;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class URLEncodeTests {

    @Test
    public void testEncode() throws UnsupportedEncodingException {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("version", 1);
        String jsonStr = JSON.toJSONString(paramsMap);
        log.info("jsonStr: {}" + jsonStr);

        String firstEncode = URLEncoder.encode(jsonStr, "UTF-8");
        log.info("firstEncode: {}" + firstEncode);

        String secondEncode = URLEncoder.encode(firstEncode, "UTF-8");
        log.info("secondEncode: {}" + secondEncode);
    }
}
