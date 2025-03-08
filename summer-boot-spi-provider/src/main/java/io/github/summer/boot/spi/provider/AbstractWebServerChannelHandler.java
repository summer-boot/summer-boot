package io.github.summer.boot.spi.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 管理服务生命周期基类
 *
 * @author changebooks@qq.com
 */
public abstract class AbstractWebServerChannelHandler implements WebServerChannelHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractWebServerChannelHandler.class);

    /**
     * 处理下个流程
     */
    private final WebServerChannelHandler nextHandler;

    public AbstractWebServerChannelHandler(WebServerChannelHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void handle() {
        String handlerName = getHandlerName();

        LOGGER.info("AbstractWebServerChannelHandler notice, handlerName: {}, handle start", handlerName);

        try {
            LOGGER.info("AbstractWebServerChannelHandler notice, handlerName: {}, handle notice, " +
                    "doHandle start", handlerName);
            boolean continueNext = doHandle();
            LOGGER.info("AbstractWebServerChannelHandler notice, handlerName: {}, handle notice, " +
                    "doHandle stop, continueNext: {}", handlerName, continueNext);

            if (continueNext) {
                LOGGER.info("AbstractWebServerChannelHandler notice, handlerName: {}, handle notice, " +
                        "doNextHandle start", handlerName);
                doNextHandle();
                LOGGER.info("AbstractWebServerChannelHandler notice, handlerName: {}, handle notice, " +
                        "doNextHandle stop", handlerName);
            } else {
                LOGGER.warn("AbstractWebServerChannelHandler warning, handlerName: {}, handle warning, " +
                        "doNextHandle abort", handlerName);
            }
        } catch (Throwable ex) {
            LOGGER.error("AbstractWebServerChannelHandler failed, handlerName: {}, handle failed, throwable: ", handlerName, ex);
        }

        LOGGER.info("AbstractWebServerChannelHandler notice, handlerName: {}, handle stop", handlerName);
    }

    /**
     * 处理当前流程
     *
     * @return 继续处理下个流程？
     */
    public abstract boolean doHandle();

    /**
     * 处理下个流程
     */
    public void doNextHandle() {
        WebServerChannelHandler nextHandler = getNextHandler();

        if (nextHandler != null) {
            nextHandler.handle();
        } else {
            String handlerName = getHandlerName();
            LOGGER.info("AbstractWebServerChannelHandler notice, handlerName: {}, doNextHandle ignored, " +
                    "nextHandler is null", handlerName);
        }
    }

    /**
     * 获取当前流程
     *
     * @return 当前流程名
     */
    public String getHandlerName() {
        return getClass().getSimpleName();
    }

    public WebServerChannelHandler getNextHandler() {
        return nextHandler;
    }

}
