package io.github.summer.boot.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Optional;

/**
 * 滚屏分页
 *
 * @author changebooks@qq.com
 */
@Schema
public class ScrollRequest extends BaseRequest {
    /**
     * 默认当前页码和每页行数
     */
    private static final int START_ID = 0;
    private static final int PAGE_SIZE = 10;

    @Schema(description = "开始索引", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "0")
    private Integer startId;

    @Schema(description = "每页行数", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "10")
    private Integer pageSize;

    public int getStartId() {
        return Optional.ofNullable(startId).orElse(START_ID);
    }

    public void setStartId(Integer startId) {
        this.startId = startId;
    }

    public int getPageSize() {
        return Optional.ofNullable(pageSize).orElse(PAGE_SIZE);
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
