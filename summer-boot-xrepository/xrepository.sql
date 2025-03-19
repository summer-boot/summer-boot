DROP TABLE IF EXISTS xrepository_key;
CREATE TABLE xrepository_key
(
    table_name varchar(64) NOT NULL DEFAULT '' COMMENT '表名',
    key_name   varchar(64) NOT NULL DEFAULT '' COMMENT '键名',
    PRIMARY KEY (table_name)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='键名概要';

DROP TABLE IF EXISTS xrepository_cache;
CREATE TABLE xrepository_cache
(
    table_name varchar(64)      NOT NULL DEFAULT '' COMMENT '表名',
    cache_time int(10) unsigned NOT NULL DEFAULT '0' COMMENT '缓存秒数',
    PRIMARY KEY (table_name)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='缓存概要';

DROP TABLE IF EXISTS xrepository_sharding;
CREATE TABLE xrepository_sharding
(
    table_name varchar(64)      NOT NULL DEFAULT '' COMMENT '表名',
    table_size int(10) unsigned NOT NULL DEFAULT '0' COMMENT '分表表数',
    PRIMARY KEY (table_name)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='分表概要';
