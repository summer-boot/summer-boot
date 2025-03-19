package io.github.summer.boot.xrepository.key;

import io.github.summer.boot.sql.Preconditions;
import jakarta.validation.constraints.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 查询键名配置
 *
 * @author changebooks@qq.com
 */
public class KeyQuery {
    /**
     * the {@link JdbcTemplate} instance
     */
    private final JdbcTemplate jdbcTemplate;

    public KeyQuery(JdbcTemplate jdbcTemplate) {
        Preconditions.requireNonNull(jdbcTemplate, "jdbcTemplate must not be null");

        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * SELECT ALL
     *
     * @return [ Table Name : Key Name ]
     */
    public Map<String, String> selectAll() {
        return jdbcTemplate.query
                (
                        "SELECT table_name, key_name FROM xrepository_key",
                        rs -> {
                            Map<String, String> data = new HashMap<>();

                            while (rs.next()) {
                                String rawTableName = rs.getString("table_name");
                                if (rawTableName == null) {
                                    continue;
                                }

                                String tableName = rawTableName.trim();
                                if (tableName.isEmpty()) {
                                    continue;
                                }

                                String keyName = rs.getString("key_name");
                                data.put(tableName, keyName);
                            }

                            return data;
                        }
                );
    }

    /**
     * SELECT ONE
     *
     * @param tableName Table Name
     * @return Key Name
     */
    public String selectOne(@NotNull String tableName) {
        return jdbcTemplate.query
                (
                        "SELECT key_name FROM xrepository_key WHERE table_name = ?",
                        ps -> ps.setString(1, tableName),
                        rs -> {
                            if (rs.next()) {
                                return rs.getString("key_name");
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
