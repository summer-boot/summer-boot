package io.github.summer.boot.binlog;

/**
 * 操作方式编码
 *
 * @author changebooks@qq.com
 */
public interface BinlogOperationCode {
    /**
     * 未知
     */
    int NULL = 0;

    /**
     * 查询
     */
    int SELECT = 1;

    /**
     * 新增
     */
    int INSERT = 2;

    /**
     * 修改
     */
    int UPDATE = 3;

    /**
     * 删除
     */
    int DELETE = 4;

}
