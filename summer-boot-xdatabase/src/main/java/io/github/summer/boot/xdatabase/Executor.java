package io.github.summer.boot.xdatabase;

import io.github.summer.boot.sql.Preconditions;
import io.github.summer.boot.sql.SqlParameter;
import io.github.summer.boot.value.Value;
import io.github.summer.boot.xdatabase.logger.LogExecutor;
import io.github.summer.boot.xdatabase.value.ValueGetter;
import io.github.summer.boot.xdatabase.value.ValueSetter;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Executor
 *
 * @author changebooks@qq.com
 */
public class Executor {
    /**
     * the {@link JdbcTemplate} instance
     */
    private final JdbcTemplate jdbcTemplate;

    /**
     * the {@link LogExecutor} instance
     */
    private LogExecutor logWriter;

    public Executor(JdbcTemplate jdbcTemplate) {
        Preconditions.requireNonNull(jdbcTemplate, "jdbcTemplate must not be null");

        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * SELECT LIST
     *
     * @param sqlParameter the {@link SqlParameter} instance
     * @param columnNames  [ Column Name ]
     * @param valueTypes   [ Column Name : Value Type ]
     * @return [ [ Column Name : Column Value ] ]
     */
    public List<Map<String, Value>> selectList(@NotNull SqlParameter sqlParameter,
                                               @NotNull List<String> columnNames, @NotNull Map<String, Integer> valueTypes) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();

        String sql = sqlParameter.getSql();
        List<String> parameterNames = sqlParameter.getParameterNames();
        Map<String, Value> parameters = sqlParameter.getParameters();

        List<Map<String, Value>> result = jdbcTemplate.query
                (
                        sql,
                        ps -> {
                            if (parameterNames != null) {
                                ValueSetter.setValues(ps, parameterNames, parameters);
                            }
                        },
                        rs -> {
                            List<Map<String, Value>> data = new ArrayList<>();

                            while (rs.next()) {
                                Map<String, Value> values = ValueGetter.getValues(rs, columnNames, valueTypes);
                                data.add(values);
                            }

                            return data;
                        }
                );

        writeLogSelectList(sqlParameter, columnNames, valueTypes, result);
        return result;
    }

    /**
     * SELECT ONE
     *
     * @param sqlParameter the {@link SqlParameter} instance
     * @param columnNames  [ Column Name ]
     * @param valueTypes   [ Column Name : Value Type ]
     * @return [ Column Name : Column Value ]
     */
    public Map<String, Value> selectOne(@NotNull SqlParameter sqlParameter,
                                        @NotNull List<String> columnNames, @NotNull Map<String, Integer> valueTypes) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();

        String sql = sqlParameter.getSql();
        List<String> parameterNames = sqlParameter.getParameterNames();
        Map<String, Value> parameters = sqlParameter.getParameters();

        Map<String, Value> result = jdbcTemplate.query
                (
                        sql,
                        ps -> {
                            if (parameterNames != null) {
                                ValueSetter.setValues(ps, parameterNames, parameters);
                            }
                        },
                        rs -> {
                            if (rs.next()) {
                                return ValueGetter.getValues(rs, columnNames, valueTypes);
                            } else {
                                return null;
                            }
                        }
                );

        writeLogSelectOne(sqlParameter, columnNames, valueTypes, result);
        return result;
    }

    /**
     * GET ONE
     *
     * @param sqlParameter the {@link SqlParameter} instance
     * @param valueType    Value Type
     * @return Column Value
     */
    public Value getOne(@NotNull SqlParameter sqlParameter, int valueType) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();

        String sql = sqlParameter.getSql();
        List<String> parameterNames = sqlParameter.getParameterNames();
        Map<String, Value> parameters = sqlParameter.getParameters();

        Value result = jdbcTemplate.query
                (
                        sql,
                        ps -> {
                            if (parameterNames != null) {
                                ValueSetter.setValues(ps, parameterNames, parameters);
                            }
                        },
                        rs -> {
                            if (rs.next()) {
                                return ValueGetter.getValue(rs, valueType);
                            } else {
                                return null;
                            }
                        }
                );

        writeLogGetOne(sqlParameter, valueType, result);
        return result;
    }

    /**
     * UPDATE
     *
     * @param sqlParameter the {@link SqlParameter} instance
     * @return AFFECTED ROWS
     */
    public int update(@NotNull SqlParameter sqlParameter) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();

        String sql = sqlParameter.getSql();
        List<String> parameterNames = sqlParameter.getParameterNames();
        Map<String, Value> parameters = sqlParameter.getParameters();

        int result = jdbcTemplate.update
                (
                        sql,
                        ps -> {
                            if (parameterNames != null) {
                                ValueSetter.setValues(ps, parameterNames, parameters);
                            }
                        }
                );

        writeLogUpdate(sqlParameter, result);
        return result;
    }

    /**
     * UPDATE LIST
     *
     * @param sqlParameter the {@link SqlParameter} instance
     * @return AFFECTED ROWS
     */
    public int updateList(@NotNull SqlParameter sqlParameter) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();

        String sql = sqlParameter.getSql();
        List<String> parameterNames = sqlParameter.getParameterNames();
        List<Map<String, Value>> parametersList = sqlParameter.getParametersList();

        int result = jdbcTemplate.update
                (
                        sql,
                        ps -> {
                            if (parameterNames != null) {
                                ValueSetter.setValues(ps, parameterNames, parametersList);
                            }
                        }
                );

        writeLogUpdateList(sqlParameter, result);
        return result;
    }

    /**
     * BATCH UPDATE
     *
     * @param sqlParameter the {@link SqlParameter} instance
     * @return AFFECTED ROWS
     */
    public int[] batchUpdate(@NotNull SqlParameter sqlParameter) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();

        String sql = sqlParameter.getSql();
        List<String> parameterNames = sqlParameter.getParameterNames();
        List<Map<String, Value>> list = sqlParameter.getParametersList();

        int[] result = jdbcTemplate.batchUpdate
                (
                        sql,
                        new BatchPreparedStatementSetter() {
                            @Override
                            public void setValues(PreparedStatement ps, int i) throws SQLException {
                                if (parameterNames == null) {
                                    return;
                                }

                                if (list == null) {
                                    return;
                                }

                                Map<String, Value> parameters = list.get(i);
                                ValueSetter.setValues(ps, parameterNames, parameters);
                            }

                            @Override
                            public int getBatchSize() {
                                return list != null ? list.size() : 0;
                            }
                        }
                );

        writeLogBatchUpdate(sqlParameter, result);
        return result;
    }

    @NotNull
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    protected void writeLogSelectList(SqlParameter sqlParameter, List<String> columnNames, Map<String, Integer> valueTypes, List<Map<String, Value>> result) {
        try {
            LogExecutor logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.selectList(sqlParameter, columnNames, valueTypes, result);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogSelectOne(SqlParameter sqlParameter, List<String> columnNames, Map<String, Integer> valueTypes, Map<String, Value> result) {
        try {
            LogExecutor logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.selectOne(sqlParameter, columnNames, valueTypes, result);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogGetOne(SqlParameter sqlParameter, int valueType, Value result) {
        try {
            LogExecutor logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.getOne(sqlParameter, valueType, result);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogUpdate(SqlParameter sqlParameter, int result) {
        try {
            LogExecutor logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.update(sqlParameter, result);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogUpdateList(SqlParameter sqlParameter, int result) {
        try {
            LogExecutor logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.updateList(sqlParameter, result);
            }
        } catch (Throwable ignored) {
        }
    }

    protected void writeLogBatchUpdate(SqlParameter sqlParameter, int[] result) {
        try {
            LogExecutor logWriter = getLogWriter();
            if (logWriter != null) {
                logWriter.batchUpdate(sqlParameter, result);
            }
        } catch (Throwable ignored) {
        }
    }

    @Nullable
    public LogExecutor getLogWriter() {
        return logWriter;
    }

    public void setLogWriter(@Nullable LogExecutor logWriter) {
        this.logWriter = logWriter;
    }

}
