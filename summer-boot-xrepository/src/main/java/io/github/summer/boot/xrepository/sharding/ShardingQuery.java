package io.github.summer.boot.xrepository.sharding;

import io.github.summer.boot.sql.Preconditions;
import jakarta.validation.constraints.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 查询分表配置
 *
 * @author changebooks@qq.com
 */
public class ShardingQuery {
    /**
     * the {@link JdbcTemplate} instance
     */
    private final JdbcTemplate jdbcTemplate;

    public ShardingQuery(JdbcTemplate jdbcTemplate) {
        Preconditions.requireNonNull(jdbcTemplate, "jdbcTemplate must not be null");

        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * SELECT ALL
     *
     * @return [ Table Name : Table Size ]
     */
    public Map<String, Integer> selectAll() {
        return jdbcTemplate.query
                (
                        "SELECT table_name, table_size FROM xrepository_sharding",
                        rs -> {
                            Map<String, Integer> data = new HashMap<>();

                            while (rs.next()) {
                                String rawTableName = rs.getString("table_name");
                                if (rawTableName == null) {
                                    continue;
                                }

                                String tableName = rawTableName.trim();
                                if (tableName.isEmpty()) {
                                    continue;
                                }

                                int tableSize = rs.getInt("table_size");
                                data.put(tableName, tableSize);
                            }

                            return data;
                        }
                );
    }

    /**
     * SELECT ONE
     *
     * @param tableName Table Name
     * @return Table Size
     */
    public Integer selectOne(@NotNull String tableName) {
        return jdbcTemplate.query
                (
                        "SELECT table_size FROM xrepository_sharding WHERE table_name = ?",
                        ps -> ps.setString(1, tableName),
                        rs -> {
                            if (rs.next()) {
                                return rs.getInt("table_size");
                            } else {
                                return null;
                            }
                        }
                );
    }

    @NotNull
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

}
