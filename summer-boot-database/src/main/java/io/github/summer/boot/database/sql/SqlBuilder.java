package io.github.summer.boot.database.sql;

import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * Build SQL
 *
 * @author changebooks@qq.com
 */
public final class SqlBuilder {
    /**
     * Build SELECT SQL
     */
    public interface SqlSelect {
        /**
         * SELECT COUNT(*) AS aggregate FROM table
         */
        String COUNT = "COUNT(*) AS aggregate";

        /**
         * SELECT LIST
         *
         * @param conditions the conditions
         * @param orders     ORDER BY column ASC, column DESC
         * @param startRow   LIMIT startRow, 10
         * @param pageSize   LIMIT 0, pageSize
         * @return SELECT column, column FROM table WHERE column = #{property} ORDER BY column ASC, column DESC LIMIT startRow, pageSize
         */
        SQL selectList(String[] conditions, String[] orders, Long startRow, Integer pageSize);

        /**
         * SELECT COUNT
         *
         * @param conditions the conditions
         * @return SELECT COUNT(*) AS aggregate FROM table WHERE column = #{property,jdbcType=JDBC_TYPE}
         */
        SQL selectCount(String[] conditions);

        /**
         * SELECT ONE
         *
         * @return SELECT column, column FROM table WHERE id = #{id,jdbcType=JDBC_TYPE}
         */
        SQL selectOne();

        /**
         * CHECK EXIST
         *
         * @param conditions the conditions
         * @return SELECT 1 FROM table WHERE column = #{property,jdbcType=JDBC_TYPE} LIMIT 1
         */
        SQL checkExist(String[] conditions);

    }

    /**
     * Build INSERT SQL
     */
    public interface SqlInsert {
        /**
         * INSERT ONE
         *
         * @return INSERT INTO table (column, column) VALUES (#{property,jdbcType=JDBC_TYPE}, #{property})
         */
        SQL insertOne();

    }

    /**
     * Build UPDATE SQL
     *
     * @param <T> the type of the POJO
     */
    public interface SqlUpdate<T> {
        /**
         * UPDATE ONE
         *
         * @param record  the POJO
         * @param exclude [ column, column ]
         * @return UPDATE table SET column = #{property,jdbcType=JDBC_TYPE}, column = #{property} WHERE id = #{id,jdbcType=JDBC_TYPE}
         */
        SQL updateOne(T record, List<String> exclude);

    }

    /**
     * Build DELETE SQL
     */
    public interface SqlDelete {
        /**
         * DELETE ONE
         *
         * @return DELETE FROM table WHERE id = #{id,jdbcType=JDBC_TYPE}
         */
        SQL deleteOne();

    }

}
