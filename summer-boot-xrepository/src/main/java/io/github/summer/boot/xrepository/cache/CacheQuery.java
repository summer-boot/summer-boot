package io.github.summer.boot.xrepository.cache;

import io.github.summer.boot.sql.Preconditions;
import jakarta.validation.constraints.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 查询缓存配置
 *
 * @author changebooks@qq.com
 */
public class CacheQuery {
    /**
     * the {@link JdbcTemplate} instance
     */
    private final JdbcTemplate jdbcTemplate;

    public CacheQuery(JdbcTemplate jdbcTemplate) {
        Preconditions.requireNonNull(jdbcTemplate, "jdbcTemplate must not be null");

        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * SELECT ALL
     *
     * @return [ Table Name : Cache Time ]
     */
    public Map<String, Integer> selectAll() {
        return jdbcTemplate.query
                (
                        "SELECT table_name, cache_time FROM xrepository_cache",
                        rs -> {
                            Map<String, Integer> data = new HashMap<>();

                            while (rs.next()) {
                                String tableName = rs.getString("table_name");
                                if (tableName == null) {
                                    continue;
                                }

                                int cacheTime = rs.getInt("cache_time");
                                data.put(tableName, cacheTime);
                            }

                            return data;
                        }
                );
    }

    /**
     * SELECT ONE
     *
     * @param tableName Table Name
     * @return Cache Time
     */
    public Integer selectOne(@NotNull String tableName) {
        return jdbcTemplate.query
                (
                        "SELECT cache_time FROM xrepository_cache WHERE table_name = ?",
                        ps -> ps.setString(1, tableName),
                        rs -> {
                            if (rs.next()) {
                                return rs.getInt("cache_time");
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
