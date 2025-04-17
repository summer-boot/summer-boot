package io.github.summer.boot.sql;

import java.util.List;

/**
 * 连表
 *
 * @author changebooks@qq.com
 */
public interface TableParser {
    /**
     * 解析列表
     *
     * @param list [ the {@link Table} instance ]
     * @return LEFT JOIN table2 ON table1.column1 = table2.column2 LEFT JOIN table3 ON table1.column1 = table3.column3
     */
    String parse(List<Table> list);

    /**
     * 解析
     *
     * @param table the {@link Table} instance
     * @return LEFT JOIN table2 ON table1.column1 = table2.column1 AND table1.column2 = table2.column2
     */
    String parse(Table table);

}
