package io.github.summer.boot.web;

import io.github.summer.boot.util.IpUtils;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 客户端ip
 *
 * @author changebooks@qq.com
 */
public final class ClientIp {

    private ClientIp() {
    }

    /**
     * 获取ip
     *
     * @param request Http Request
     * @return ip地址
     */
    public static String getIp(HttpServletRequest request) {
        if (request == null) {
            return null;
        }

        String result = doGetIp(request, IpUtils.X_FORWARDED_FOR);
        if (result != null) {
            return result;
        }

        result = doGetIp(request, IpUtils.X_REAL_IP);
        if (result != null) {
            return result;
        }

        result = doGetIp(request, IpUtils.PROXY_CLIENT_IP);
        if (result != null) {
            return result;
        }

        result = doGetIp(request, IpUtils.WL_PROXY_CLIENT_IP);
        if (result != null) {
            return result;
        }

        result = doGetIp(request, IpUtils.HTTP_CLIENT_IP);
        if (result != null) {
            return result;
        }

        result = doGetIp(request, IpUtils.HTTP_X_FORWARDED_FOR);
        if (result != null) {
            return result;
        }

        return doGetIp(request);
    }

    /**
     * 获取ip
     *
     * @param request    Http Request
     * @param headerName Http Header Name
     * @return ip地址
     */
    private static String doGetIp(HttpServletRequest request, String headerName) {
        String rawIp = request.getHeader(headerName);
        return IpUtils.parseIp(rawIp);
    }

    /**
     * 获取ip
     *
     * @param request Http Request
     * @return ip地址
     */
    private static String doGetIp(HttpServletRequest request) {
        String rawIp = request.getRemoteAddr();
        return IpUtils.parseIp(rawIp);
    }

}
