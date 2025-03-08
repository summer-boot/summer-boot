package io.github.summer.boot.vo;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * 规范通用参数
 *
 * @author changebooks@qq.com
 */
public final class BaseRequestFormatter {
    /**
     * 接口版本，api version
     */
    public static final int AV = 1;

    /**
     * 输入内容编码，input encode
     */
    public static final String IE = StandardCharsets.UTF_8.name();

    /**
     * 输出语种，output language type
     */
    public static final String OL = "ZH-CN";

    /**
     * 输出数据格式，output data type
     */
    public static final String OD = "JSON";

    private BaseRequestFormatter() {
    }

    /**
     * 规范参数
     *
     * @param request {@link BaseRequest} 参数
     */
    public static void format(BaseRequest request) {
        if (request == null) {
            return;
        }

        Integer rawAv = request.getAv();
        String rawIe = request.getIe();
        String rawOl = request.getOl();
        String rawOd = request.getOd();

        int av = Optional.ofNullable(rawAv).orElse(AV);
        String ie = cleanProperty(rawIe);
        String ol = cleanProperty(rawOl);
        String od = cleanProperty(rawOd);

        if (av <= 0) {
            av = AV;
        }

        if (ie.isEmpty()) {
            ie = IE;
        }

        if (ol.isEmpty()) {
            ol = OL;
        }

        if (od.isEmpty()) {
            od = OD;
        }

        request.setAv(av);
        request.setIe(ie);
        request.setOl(ol);
        request.setOd(od);
    }

    /**
     * 清理一个参数
     *
     * @param value 清理前的参数值
     * @return 清理后的参数值
     */
    public static String cleanProperty(String value) {
        if (value != null) {
            return value.trim().toUpperCase();
        } else {
            return "";
        }
    }

}
