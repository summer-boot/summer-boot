DROP TABLE IF EXISTS xrepository_schema;
CREATE TABLE xrepository_schema
(
    table_name varchar(64)      NOT NULL DEFAULT '' COMMENT '表名',
    key_name   varchar(64)      NOT NULL DEFAULT '' COMMENT '键名',
    table_size int(10) unsigned NOT NULL DEFAULT '0' COMMENT '分表表数',
    cache_time int(10) unsigned NOT NULL DEFAULT '0' COMMENT '缓存秒数',
    PRIMARY KEY (table_name)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='数仓概要';
