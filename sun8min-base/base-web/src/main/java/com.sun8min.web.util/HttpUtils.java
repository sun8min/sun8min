package com.sun8min.web.util;

import javax.servlet.http.HttpServletRequest;
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
}
