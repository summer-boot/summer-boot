package io.github.summer.boot.worksheet;

/**
 * 终止任务
 *
 * @author changebooks@qq.com
 */
public class StopException extends RuntimeException {

    public StopException() {
        super("stop the read");
    }

}
