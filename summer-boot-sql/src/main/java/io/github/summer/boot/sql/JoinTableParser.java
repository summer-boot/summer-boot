package io.github.summer.boot.sql;

import java.util.List;

/**
 * 连表
 *
 * @author changebooks@qq.com
 */
public interface JoinTableParser {
    /**
     * 解析列表
     *
     * @param list [ the {@link JoinTable} instance ]
     * @return FROM table1 LEFT JOIN table2 ON table1.column1 = table2.column1 AND table1.column2 = table2.column2 LEFT JOIN table3 ON table1.column1 = table3.column1 AND table1.column2 = table3.column2
     */
    String parseJoinTable(List<JoinTable> list);

    /**
     * 解析
     *
     * @param joinTable the {@link JoinTable} instance
     * @return FROM table1 LEFT JOIN table2 ON table1.column1 = table2.column1 AND table1.column2 = table2.column2
     */
    String parseJoinTable(JoinTable joinTable);

}
