package io.github.summer.boot.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Optional;

/**
 * 分页条件
 *
 * @author changebooks@qq.com
 */
@Schema
public class PageRequest extends BaseRequest {
    /**
     * 默认当前页码和每页行数
     */
    private static final int PAGE_NUM = 1;
    private static final int PAGE_SIZE = 10;

    @Schema(description = "当前页码", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "1")
    private Integer pageNum;

    @Schema(description = "每页行数", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "10")
    private Integer pageSize;

    /**
     * 计算开始行数
     *
     * @return 开始索引
     */
    @Schema(hidden = true)
    public long getStartRow() {
        int pageNum = getPageNum();
        pageNum = Math.max(pageNum, 1);

        int pageSize = getPageSize();
        pageSize = Math.max(pageSize, 1);

        return (long) (pageNum - 1) * pageSize;
    }

    public int getPageNum() {
        return Optional.ofNullable(pageNum).orElse(PAGE_NUM);
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return Optional.ofNullable(pageSize).orElse(PAGE_SIZE);
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
