package io.github.summer.boot.vo;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 通过主键，修改多个排序
 *
 * @author changebooks@qq.com
 */
@Schema
public class UpdateSortRequestL extends BaseRequest {

    @Schema(description = "主键id", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "123456789012345678")
    private Long id;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "10000")
    private Integer sort;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

}
