package com.sun8min.web.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpUtils {

    /**
     * 检测是否为移动端设备访问
     */
    public static boolean isMobile(HttpServletRequest request) {
        boolean isMobile = false;
        // \b 是单词边界(连着的两个(字母字符 与 非字母字符) 之间的逻辑上的间隔),
        // 字符串在编译时会被转码一次,所以是 "\\b"
        // \B 是单词内部逻辑间隔(连着的两个字母字符之间的逻辑上的间隔)
        String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i"
                + "|windows (phone|ce)|blackberry"
                + "|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp"
                + "|laystation portable)|nokia|fennec|htc[-_]"
                + "|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
        String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser"
                + "|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";

        //移动设备正则匹配：手机端、平板
        Pattern phonePat = Pattern.compile(phoneReg, Pattern.CASE_INSENSITIVE);
        Pattern tablePat = Pattern.compile(tableReg, Pattern.CASE_INSENSITIVE);

        // 匹配
        String userAgent = request.getHeader("User-Agent");
        if (userAgent != null) {
            Matcher matcherPhone = phonePat.matcher(userAgent.toLowerCase());
            Matcher matcherTable = tablePat.matcher(userAgent.toLowerCase());
            if (matcherPhone.find() || matcherTable.find()) {
                isMobile = true;
            }
        }
        return isMobile;
    }

    /**
     * 检测是否为PC端设备访问
     */
    public static boolean isPC(HttpServletRequest request) {
        return !isMobile(request);
    }

    /**
     * 检测是否为ajax请求
     */
    public static boolean isAjax(HttpServletRequest request) {
        String contentTypeHeader = request.getHeader("Content-Type");
        String acceptHeader = request.getHeader("Accept");
        String xRequestedWith = request.getHeader("X-Requested-With");
        return ((contentTypeHeader != null && contentTypeHeader.contains("application/json"))
                || (acceptHeader != null && acceptHeader.contains("application/json"))
                || "XMLHttpRequest".equalsIgnoreCase(xRequestedWith));
    }

    /**
     * 检测是否不为ajax请求
     */
    public static boolean isNotAjax(HttpServletRequest request) {
        return !isAjax(request);
    }

    /**
     * 将url转换成map
     *
     * <pre>
     * HttpUtils.urlToMap(array=123,456&kv=kv&list=aaa,bbb)  =  {list=aaa,bbb, array=123,456, kv=kv}
     * </pre>
     *
     * @param param
     * @return
     */
    public static Map<String, String> urlToMap(String param) {
        Map<String, String> map = new HashMap<>(0);
        if (StringUtils.isBlank(param)) {
            return map;
        }
        String[] params = param.split("&");
        for (int i = 0; i < params.length; i++) {
            String[] p = params[i].split("=");
            if (p.length == 2) {
                map.put(p[0], p[1]);
            }
        }
        return map;
    }

    /**
     * 将map转换成url
     *
     * <pre>
     * // 给map设值
     * Map<String,Object> map = new HashedMap();
     * map.put("list", Arrays.asList("aaa", "bbb"));
     * map.put("array", new String[]{"123","456"});
     * map.put("kv", "kv");
     * // 此时map状态
     * map : {list=[aaa, bbb], kv=kv, array=[Ljava.lang.String;@6f79caec}
     * // 转换
     * HttpUtils.mapToUrl(map)    =    array=123,456&kv=kv&list=aaa,bbb
     * </pre>
     *
     * @param map
     * @return
     */
    public static String mapToUrl(Map map) {
        // 使用fastjson先处理一下获取数据，比如数组之类的，否则需要自己处理
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(map));
        StringBuffer sb = new StringBuffer();
        jsonObject.entrySet().forEach(entry -> {
            // []、"处理
            String value = Optional.ofNullable(entry.getValue())
                    .map(o -> o.toString()
                            .replace("[", "")
                            .replace("]", "")
                            .replace("\"", ""))
                    .orElse("");
            sb.append(entry.getKey() + "=" + value);
            sb.append("&");
        });
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = StringUtils.substringBeforeLast(s, "&");
        }
        return s;
    }
}
