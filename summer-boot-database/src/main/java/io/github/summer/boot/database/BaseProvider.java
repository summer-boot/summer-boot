package io.github.summer.boot.database;

import io.github.summer.boot.database.sql.*;
import io.github.summer.boot.database.tag.TagResult;
import io.github.summer.boot.database.tag.TagResultFilter;
import io.github.summer.boot.database.tag.TagResultMap;
import io.github.summer.boot.database.tag.TagResultMapParser;
import org.apache.ibatis.jdbc.SQL;

import java.util.Collections;
import java.util.List;

/**
 * Base Provider
 *
 * @param <T> the type of the POJO
 * @author changebooks@qq.com
 */
public class BaseProvider<T> {
    /**
     * the bean of &#064;MybatisResultMap annotation
     */
    private final TagResultMap resultMap;

    /**
     * the table name
     * e.g. table_name, table_name_${tableNum}
     */
    protected String table;

    /**
     * the column list
     * e.g. [ {@link TagResult}, {@link TagResult} ]
     */
    protected List<TagResult> elements;

    /**
     * the id list
     * e.g. [ {@link TagResult}, {@link TagResult} ]
     */
    protected List<TagResult> ids;

    /**
     * the column names
     * e.g. [ column_name, column_name ]
     */
    protected String[] columns;

    /**
     * the id condition
     * e.g. [ column = #{property,jdbcType=JDBC_TYPE}, column = #{property} ]
     */
    protected String[] whereId;

    /**
     * the implements of the {@link SqlBuilder}
     */
    protected SqlBuilder.SqlSelect selectBuilder;
    protected SqlBuilder.SqlInsert insertBuilder;
    protected SqlBuilder.SqlUpdate<T> updateBuilder;
    protected SqlBuilder.SqlDelete deleteBuilder;

    /**
     * the sql caches
     */
    protected String selectOne;
    protected String insertOne;
    protected String deleteOne;

    /**
     * update lock
     * e.g. update_version = update_version + 1
     * e.g. update_version = #{updateVersion,jdbcType=INTEGER}
     */
    private List<String> lockExclude;
    private List<String> lockInclude;
    private List<String> lockWhere;

    /**
     * Initialize all properties
     * Initialize all sql builders
     * Initialize all sql caches
     */
    public BaseProvider() {
        this.resultMap = TagResultMapParser.parseGeneric(getClass());

        setProperties();
        setLockProperties();
        afterPropertiesSet();

        setBuilders();
        afterBuildersSet();

        setCaches();
        afterCachesSet();
    }

    /**
     * Initialize all properties
     *
     * @see #table
     * @see #elements
     * @see #ids
     * @see #columns
     * @see #whereId
     */
    public void setProperties() {
        this.table = resultMap.getTable();
        this.elements = resultMap.getElements();
        this.ids = TagResultFilter.filterId(elements);
        this.columns = SqlSegment.getColumns(elements);
        this.whereId = SqlSegment.getWhere(ids);
    }

    /**
     * Initialize update lock properties
     *
     * @see #lockExclude
     * @see #lockInclude
     * @see #lockWhere
     */
    public void setLockProperties() {
        String lock = getUpdateLock();
        if (lock == null) {
            return;
        }

        TagResult tagResult = TagResultFilter.filterColumnName(getElements(), lock);
        if (tagResult == null) {
            return;
        }

        String lockExclude = tagResult.getColumn();
        if (lockExclude != null) {
            this.lockExclude = Collections.singletonList(lockExclude);
        }

        String lockInclude = SqlSegment.getLockSet(tagResult);
        if (lockInclude != null) {
            this.lockInclude = Collections.singletonList(lockInclude);
        }

        String lockWhere = SqlSegment.joinKeyValue(tagResult);
        if (lockWhere != null) {
            this.lockWhere = Collections.singletonList(lockWhere);
        }
    }

    /**
     * Invoked by the containing code after it has set all properties
     */
    public void afterPropertiesSet() {
    }

    /**
     * Initialize all sql builders
     *
     * @see #setSelectBuilder
     * @see #setInsertBuilder
     * @see #setUpdateBuilder
     * @see #setDeleteBuilder
     */
    public void setBuilders() {
        setSelectBuilder();
        setInsertBuilder();
        setUpdateBuilder();
        setDeleteBuilder();
    }

    /**
     * Invoked by the containing code after it has set all sql builders
     */
    public void afterBuildersSet() {
    }

    /**
     * Initialize {@link #selectBuilder}
     */
    public void setSelectBuilder() {
        this.selectBuilder = new SqlSelectBuilder(getTable(), getColumns(), getWhereId());
    }

    /**
     * Initialize {@link #insertBuilder}
     */
    public void setInsertBuilder() {
        List<TagResult> elements = TagResultFilter.removeAutoIncrement(getElements());
        String[] columns = SqlSegment.getIntoColumns(elements);
        String[] values = SqlSegment.getIntoValues(elements);

        this.insertBuilder = new SqlInsertBuilder(getTable(), columns, values);
    }

    /**
     * Initialize {@link #updateBuilder}
     */
    public void setUpdateBuilder() {
        List<TagResult> columns = TagResultFilter.removeId(getElements());

        this.updateBuilder = new SqlUpdateBuilder<>(getTable(), columns, getWhereId());
    }

    /**
     * Initialize {@link #deleteBuilder}
     */
    public void setDeleteBuilder() {
        this.deleteBuilder = new SqlDeleteBuilder(getTable(), getWhereId());
    }

    /**
     * Initialize all sql caches
     *
     * @see #setSelectOne
     * @see #setInsertOne
     * @see #setDeleteOne
     */
    public void setCaches() {
        setSelectOne();
        setInsertOne();
        setDeleteOne();
    }

    /**
     * Invoked by the containing code after it has set all sql caches
     */
    public void afterCachesSet() {
    }

    /**
     * Initialize {@link #selectOne}
     */
    public void setSelectOne() {
        this.selectOne = buildSelectOne().toString();
    }

    /**
     * Initialize {@link #insertOne}
     */
    public void setInsertOne() {
        this.insertOne = buildInsertOne().toString();
    }

    /**
     * Initialize {@link #deleteOne}
     */
    public void setDeleteOne() {
        this.deleteOne = buildDeleteOne().toString();
    }

    /**
     * SELECT LIST
     *
     * @param conditions the conditions
     * @param orders     ORDER BY column ASC, column DESC
     * @param startRow   LIMIT startRow, 10
     * @param pageSize   LIMIT 0, pageSize
     * @return SELECT column, column FROM table WHERE column = #{property} ORDER BY column ASC, column DESC LIMIT startRow, pageSize
     */
    public String selectList(String[] conditions, String[] orders, Long startRow, Integer pageSize) {
        return buildSelectList(conditions, orders, startRow, pageSize).toString();
    }

    /**
     * SELECT LIST
     *
     * @param conditions the conditions
     * @param orders     ORDER BY column ASC, column DESC
     * @param startRow   LIMIT startRow, 10
     * @param pageSize   LIMIT 0, pageSize
     * @return SELECT column, column FROM table WHERE column = #{property} ORDER BY column ASC, column DESC LIMIT startRow, pageSize
     * @see SqlBuilder.SqlSelect#selectList
     */
    public SQL buildSelectList(String[] conditions, String[] orders, Long startRow, Integer pageSize) {
        return selectBuilder.selectList(conditions, orders, startRow, pageSize);
    }

    /**
     * SELECT COUNT
     *
     * @param conditions the conditions
     * @return SELECT COUNT(*) AS aggregate FROM table WHERE column = #{property,jdbcType=JDBC_TYPE}
     */
    public String selectCount(String[] conditions) {
        return buildSelectCount(conditions).toString();
    }

    /**
     * SELECT COUNT
     *
     * @param conditions the conditions
     * @return SELECT COUNT(*) AS aggregate FROM table WHERE column = #{property,jdbcType=JDBC_TYPE}
     * @see SqlBuilder.SqlSelect#selectCount
     */
    public SQL buildSelectCount(String[] conditions) {
        return selectBuilder.selectCount(conditions);
    }

    /**
     * SELECT ONE
     *
     * @return SELECT column, column FROM table WHERE id = #{id,jdbcType=JDBC_TYPE}
     */
    public String selectOne() {
        return selectOne;
    }

    /**
     * SELECT ONE
     *
     * @return SELECT column, column FROM table WHERE id = #{id,jdbcType=JDBC_TYPE}
     * @see SqlBuilder.SqlSelect#selectOne
     */
    public SQL buildSelectOne() {
        return selectBuilder.selectOne();
    }

    /**
     * CHECK EXIST
     *
     * @param conditions the conditions
     * @return SELECT 1 FROM table WHERE column = #{property,jdbcType=JDBC_TYPE} LIMIT 1
     */
    public String checkExist(String[] conditions) {
        return buildCheckExist(conditions).toString();
    }

    /**
     * CHECK EXIST
     *
     * @param conditions the conditions
     * @return SELECT 1 FROM table WHERE column = #{property,jdbcType=JDBC_TYPE} LIMIT 1
     * @see SqlBuilder.SqlSelect#checkExist
     */
    public SQL buildCheckExist(String[] conditions) {
        return selectBuilder.checkExist(conditions);
    }

    /**
     * INSERT ONE
     *
     * @return INSERT INTO table (column, column) VALUES (#{property,jdbcType=JDBC_TYPE}, #{property})
     */
    public String insertOne() {
        return insertOne;
    }

    /**
     * INSERT ONE
     *
     * @return INSERT INTO table (column, column) VALUES (#{property,jdbcType=JDBC_TYPE}, #{property})
     * @see SqlBuilder.SqlInsert#insertOne
     */
    public SQL buildInsertOne() {
        return insertBuilder.insertOne();
    }

    /**
     * UPDATE ONE
     *
     * @param record the POJO
     * @return UPDATE table SET column = #{property,jdbcType=JDBC_TYPE}, column = #{property} WHERE id = #{id,jdbcType=JDBC_TYPE}
     */
    public String updateOne(T record) {
        return buildUpdateOne(record).toString();
    }

    /**
     * UPDATE ONE
     *
     * @param record the POJO
     * @return UPDATE table SET column = #{property,jdbcType=JDBC_TYPE}, column = #{property} WHERE id = #{id,jdbcType=JDBC_TYPE}
     * @see SqlBuilder.SqlUpdate#updateOne
     */
    public SQL buildUpdateOne(T record) {
        List<String> excludeList = getUpdateExclude();
        SQL sql = updateBuilder.updateOne(record, excludeList);

        List<String> includeList = getUpdateInclude();
        if (includeList != null) {
            for (String include : includeList) {
                sql = sql.SET(include);
            }
        }

        List<String> whereList = getUpdateWhere();
        if (whereList != null) {
            for (String where : whereList) {
                sql = sql.WHERE(where);
            }
        }

        return sql;
    }

    /**
     * DELETE ONE
     *
     * @return DELETE FROM table WHERE id = #{id,jdbcType=JDBC_TYPE}
     */
    public String deleteOne() {
        return deleteOne;
    }

    /**
     * DELETE ONE
     *
     * @return DELETE FROM table WHERE id = #{id,jdbcType=JDBC_TYPE}
     * @see SqlBuilder.SqlDelete#deleteOne
     */
    public SQL buildDeleteOne() {
        return deleteBuilder.deleteOne();
    }

    /**
     * UPDATE Exclude SET List
     *
     * @return [ column, column ]
     */
    public List<String> getUpdateExclude() {
        return lockExclude;
    }

    /**
     * UPDATE Include SET List
     *
     * @return [ column = column + 1, column = #{property,jdbcType=JDBC_TYPE} ]
     */
    public List<String> getUpdateInclude() {
        return lockInclude;
    }

    /**
     * UPDATE WHERE List
     *
     * @return [ column = #{property,jdbcType=JDBC_TYPE}, column = #{property,jdbcType=JDBC_TYPE} ]
     */
    public List<String> getUpdateWhere() {
        return lockWhere;
    }

    /**
     * UPDATE constraint lock name
     * e.g. update_version
     *
     * @return the column name
     */
    public String getUpdateLock() {
        return null;
    }

    public TagResultMap getResultMap() {
        return resultMap;
    }

    public String getTable() {
        return table;
    }

    public List<TagResult> getElements() {
        return elements;
    }

    public List<TagResult> getIds() {
        return ids;
    }

    public String[] getColumns() {
        return columns;
    }

    public String[] getWhereId() {
        return whereId;
    }

}
