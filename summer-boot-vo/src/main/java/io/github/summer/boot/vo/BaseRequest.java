package io.github.summer.boot.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * 通用参数
 *
 * @author changebooks@qq.com
 */
@Schema
public class BaseRequest implements Serializable {

    @Schema(description = "接口版本，api version", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "1", hidden = true)
    private Integer av;

    @Schema(description = "输入内容编码，input encode", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "UTF-8", hidden = true)
    private String ie;

    @Schema(description = "输出 语种，output language type", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "ZH-CN", hidden = true)
    private String ol;

    @Schema(description = "输出数据格式，output data type", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "JSON", hidden = true)
    private String od;

    public Integer getAv() {
        return av;
    }

    public void setAv(Integer av) {
        this.av = av;
    }

    public String getIe() {
        return ie;
    }

    public void setIe(String ie) {
        this.ie = ie;
    }

    public String getOl() {
        return ol;
    }

    public void setOl(String ol) {
        this.ol = ol;
    }

    public String getOd() {
        return od;
    }

    public void setOd(String od) {
        this.od = od;
    }

}
