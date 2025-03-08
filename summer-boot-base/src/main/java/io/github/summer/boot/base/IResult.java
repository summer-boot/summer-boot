package io.github.summer.boot.base;

/**
 * 标准的数据传输接口
 *
 * @author changebooks@qq.com
 */
public interface IResult {
    /**
     * 错误码
     *
     * @return 0-正确、gt 0-错误、lt 0-弃用
     */
    int getCode();

    /**
     * 错误信息
     *
     * @return ok-正确、!ok-错误
     */
    String getMessage();

}
