package io.github.summer.boot.base;

/**
 * 标准错误码
 *
 * @author changebooks@qq.com
 */
public interface Code {
    /**
     * OK
     */
    int SUCCESS_NUM = 0;

    /**
     * -- 禁用，易误解成HttpStatus.OK --
     */
    int IGNORE_NUM = 200;

    /**
     * 无访问权限
     */
    int FORBIDDEN = 403;

    /**
     * 页面不存在
     */
    int PAGE_NOT_FOUND = 404;

    /**
     * 请求方式不支持
     */
    int METHOD_NOT_ALLOWED = 405;

    /**
     * 系统运行异常
     */
    int SYSTEM_RUN_ERR = 500;

    /**
     * 脚本运行失败
     */
    int SCRIPT_RUN_ERR = 501;

    /**
     * 幂等
     */
    int IDEMPOTENT = 1000;

    /**
     * 重复请求
     */
    int CONFLICT = 1001;

    /**
     * 缺少参数
     */
    int ARGS_REQUIRED = 1101;

    /**
     * 非法参数
     */
    int ARGS_ILLEGAL = 1102;

    /**
     * 参数错误
     */
    int ARGS_ERR = 1103;

    /**
     * 缺少结果
     */
    int RESULT_REQUIRED = 1111;

    /**
     * 非法结果
     */
    int RESULT_ILLEGAL = 1112;

    /**
     * 结果错误
     */
    int RESULT_ERR = 1113;

    /**
     * Json格式错误
     */
    int JSON_ERR = 1201;

    /**
     * Xml格式错误
     */
    int XML_ERR = 1202;

    /**
     * 缺少文件
     */
    int FILE_REQUIRED = 1211;

    /**
     * 非法文件
     */
    int FILE_ILLEGAL = 1212;

    /**
     * 文件错误
     */
    int FILE_ERR = 1213;

    /**
     * 缺少目录
     */
    int DIRECTORY_REQUIRED = 1221;

    /**
     * 非法目录
     */
    int DIRECTORY_ILLEGAL = 1222;

    /**
     * 目录错误
     */
    int DIRECTORY_ERR = 1223;

    /**
     * 缺少资源
     */
    int RESOURCE_REQUIRED = 1231;

    /**
     * 非法资源
     */
    int RESOURCE_ILLEGAL = 1232;

    /**
     * 资源错误
     */
    int RESOURCE_ERR = 1233;

    /**
     * 违反有效性约束
     */
    int CONSTRAINT_NOT_NULL = 1311;

    /**
     * 违反无符号约束
     */
    int CONSTRAINT_UNSIGNED = 1312;

    /**
     * 违反唯一性约束
     */
    int CONSTRAINT_UNIQUE = 1313;

    /**
     * 统计失败
     */
    int AGGREGATE_ERR = 1401;

    /**
     * 查询失败
     */
    int FIND_ERR = 1411;

    /**
     * 新增失败
     */
    int INSERT_ERR = 1412;

    /**
     * 修改失败
     */
    int UPDATE_ERR = 1413;

    /**
     * 删除失败
     */
    int DELETE_ERR = 1414;

    /**
     * 查询数据库失败
     */
    int DB_FIND_ERR = 1421;

    /**
     * 新增数据库失败
     */
    int DB_INSERT_ERR = 1422;

    /**
     * 修改数据库失败
     */
    int DB_UPDATE_ERR = 1423;

    /**
     * 删除数据库失败
     */
    int DB_DELETE_ERR = 1424;

    /**
     * 查询缓存失败
     */
    int CACHE_FIND_ERR = 1431;

    /**
     * 新增缓存失败
     */
    int CACHE_INSERT_ERR = 1432;

    /**
     * 修改缓存失败
     */
    int CACHE_UPDATE_ERR = 1433;

    /**
     * 删除缓存失败
     */
    int CACHE_DELETE_ERR = 1434;

    /**
     * 查询属性失败
     */
    int ATTRIBUTE_FIND_ERR = 1441;

    /**
     * 新增属性失败
     */
    int ATTRIBUTE_INSERT_ERR = 1442;

    /**
     * 修改属性失败
     */
    int ATTRIBUTE_UPDATE_ERR = 1443;

    /**
     * 删除属性失败
     */
    int ATTRIBUTE_DELETE_ERR = 1444;

    /**
     * 读文件失败
     */
    int FILE_READ_ERR = 1451;

    /**
     * 创建文件失败
     */
    int FILE_CREATE_ERR = 1452;

    /**
     * 写文件失败
     */
    int FILE_WRITE_ERR = 1453;

    /**
     * 删文件失败
     */
    int FILE_DELETE_ERR = 1454;

    /**
     * 读目录失败
     */
    int DIRECTORY_READ_ERR = 1461;

    /**
     * 创建目录失败
     */
    int DIRECTORY_CREATE_ERR = 1462;

    /**
     * 修改目录失败
     */
    int DIRECTORY_MODIFY_ERR = 1463;

    /**
     * 删目录失败
     */
    int DIRECTORY_DELETE_ERR = 1464;

    /**
     * 上传失败
     */
    int UPLOAD_ERR = 1501;

    /**
     * 下载失败
     */
    int DOWNLOAD_ERR = 1502;

    /**
     * HTTP请求失败
     */
    int HTTP_ERR = 1511;

    /**
     * RPC调用失败
     */
    int RPC_ERR = 1521;

    /**
     * 发消息失败
     */
    int SEND_MSG_ERR = 1531;

    /**
     * 缺少令牌
     */
    int TOKEN_REQUIRED = 1601;

    /**
     * 非法令牌
     */
    int TOKEN_ILLEGAL = 1602;

    /**
     * 令牌错误
     */
    int TOKEN_ERR = 1603;

    /**
     * 令牌是正整数
     */
    int TOKEN_POSITIVE = 1604;

    /**
     * 令牌是非负数
     */
    int TOKEN_NON_NEGATIVE = 1605;

    /**
     * 令牌不存在
     */
    int TOKEN_UNSUPPORTED = 1606;

    /**
     * 令牌重复
     */
    int TOKEN_CONFLICT = 1607;

    /**
     * 令牌过期
     */
    int TOKEN_EXPIRED = 1608;

    /**
     * 未知错误
     */
    int UNKNOWN_ERR = 2008;

    /**
     * 缺少时间
     */
    int TIME_REQUIRED = 10001;

    /**
     * 非法时间
     */
    int TIME_ILLEGAL = 10002;

    /**
     * 时间格式错误
     */
    int TIME_ERR = 10003;

    /**
     * 缺少日期
     */
    int DATE_REQUIRED = 10011;

    /**
     * 非法日期
     */
    int DATE_ILLEGAL = 10012;

    /**
     * 日期格式错误
     */
    int DATE_ERR = 10013;

    /**
     * 缺少排序
     */
    int SORT_REQUIRED = 10101;

    /**
     * 非法排序
     */
    int SORT_ILLEGAL = 10102;

    /**
     * 排序错误
     */
    int SORT_ERR = 10103;

    /**
     * 排序是正整数
     */
    int SORT_POSITIVE = 10104;

    /**
     * 排序是非负数
     */
    int SORT_NON_NEGATIVE = 10105;

    /**
     * 缺少进度
     */
    int PROGRESS_REQUIRED = 10111;

    /**
     * 非法进度
     */
    int PROGRESS_ILLEGAL = 10112;

    /**
     * 进度错误
     */
    int PROGRESS_ERR = 10113;

    /**
     * 进度是正整数
     */
    int PROGRESS_POSITIVE = 10114;

    /**
     * 进度是非负数
     */
    int PROGRESS_NON_NEGATIVE = 10115;

    /**
     * 缺少版本
     */
    int VERSION_REQUIRED = 10121;

    /**
     * 非法版本
     */
    int VERSION_ILLEGAL = 10122;

    /**
     * 版本错误
     */
    int VERSION_ERR = 10123;

    /**
     * 版本是正整数
     */
    int VERSION_POSITIVE = 10124;

    /**
     * 版本是非负数
     */
    int VERSION_NON_NEGATIVE = 10125;

    /**
     * 版本不存在
     */
    int VERSION_UNSUPPORTED = 10126;

    /**
     * 缺少类型
     */
    int TYPE_REQUIRED = 10201;

    /**
     * 非法类型
     */
    int TYPE_ILLEGAL = 10202;

    /**
     * 类型错误
     */
    int TYPE_ERR = 10203;

    /**
     * 类型是正整数
     */
    int TYPE_POSITIVE = 10204;

    /**
     * 类型是非负数
     */
    int TYPE_NON_NEGATIVE = 10205;

    /**
     * 类型不存在
     */
    int TYPE_UNSUPPORTED = 10206;

    /**
     * 缺少Jdbc类型
     */
    int JDBC_TYPE_REQUIRED = 10211;

    /**
     * 非法Jdbc类型
     */
    int JDBC_TYPE_ILLEGAL = 10212;

    /**
     * Jdbc类型错误
     */
    int JDBC_TYPE_ERR = 10213;

    /**
     * Jdbc类型是正整数
     */
    int JDBC_TYPE_POSITIVE = 10214;

    /**
     * Jdbc类型是非负数
     */
    int JDBC_TYPE_NON_NEGATIVE = 10215;

    /**
     * Jdbc类型不存在
     */
    int JDBC_TYPE_UNSUPPORTED = 10216;

    /**
     * 缺少Java类型
     */
    int JAVA_TYPE_REQUIRED = 10221;

    /**
     * 非法Java类型
     */
    int JAVA_TYPE_ILLEGAL = 10222;

    /**
     * Java类型错误
     */
    int JAVA_TYPE_ERR = 10223;

    /**
     * Java类型是正整数
     */
    int JAVA_TYPE_POSITIVE = 10224;

    /**
     * Java类型是非负数
     */
    int JAVA_TYPE_NON_NEGATIVE = 10225;

    /**
     * Java类型不存在
     */
    int JAVA_TYPE_UNSUPPORTED = 10226;

    /**
     * 缺少主键
     */
    int ID_REQUIRED = 10401;

    /**
     * 非法主键
     */
    int ID_ILLEGAL = 10402;

    /**
     * 主键错误
     */
    int ID_ERR = 10403;

    /**
     * 主键是正整数
     */
    int ID_POSITIVE = 10404;

    /**
     * 主键是非负数
     */
    int ID_NON_NEGATIVE = 10405;

    /**
     * 主键不存在
     */
    int ID_UNSUPPORTED = 10406;

    /**
     * 主键重复
     */
    int ID_CONFLICT = 10407;

    /**
     * 缺少表主键
     */
    int TABLE_ID_REQUIRED = 10411;

    /**
     * 非法表主键
     */
    int TABLE_ID_ILLEGAL = 10412;

    /**
     * 表主键错误
     */
    int TABLE_ID_ERR = 10413;

    /**
     * 表主键是正整数
     */
    int TABLE_ID_POSITIVE = 10414;

    /**
     * 表主键是非负数
     */
    int TABLE_ID_NON_NEGATIVE = 10415;

    /**
     * 表主键不存在
     */
    int TABLE_ID_UNSUPPORTED = 10416;

    /**
     * 表主键重复
     */
    int TABLE_ID_CONFLICT = 10417;

    /**
     * 缺少属性主键
     */
    int ATTRIBUTE_ID_REQUIRED = 10421;

    /**
     * 非法属性主键
     */
    int ATTRIBUTE_ID_ILLEGAL = 10422;

    /**
     * 属性主键错误
     */
    int ATTRIBUTE_ID_ERR = 10423;

    /**
     * 属性主键是正整数
     */
    int ATTRIBUTE_ID_POSITIVE = 10424;

    /**
     * 属性主键是非负数
     */
    int ATTRIBUTE_ID_NON_NEGATIVE = 10425;

    /**
     * 属性主键不存在
     */
    int ATTRIBUTE_ID_UNSUPPORTED = 10426;

    /**
     * 属性主键重复
     */
    int ATTRIBUTE_ID_CONFLICT = 10427;

    /**
     * 缺少业务主键
     */
    int BIZ_ID_REQUIRED = 10431;

    /**
     * 非法业务主键
     */
    int BIZ_ID_ILLEGAL = 10432;

    /**
     * 业务主键错误
     */
    int BIZ_ID_ERR = 10433;

    /**
     * 业务主键是正整数
     */
    int BIZ_ID_POSITIVE = 10434;

    /**
     * 业务主键是非负数
     */
    int BIZ_ID_NON_NEGATIVE = 10435;

    /**
     * 业务主键不存在
     */
    int BIZ_ID_UNSUPPORTED = 10436;

    /**
     * 业务主键重复
     */
    int BIZ_ID_CONFLICT = 10437;

    /**
     * 缺少流水id
     */
    int FLOW_ID_REQUIRED = 10441;

    /**
     * 非法流水id
     */
    int FLOW_ID_ILLEGAL = 10442;

    /**
     * 流水id错误
     */
    int FLOW_ID_ERR = 10443;

    /**
     * 流水id是正整数
     */
    int FLOW_ID_POSITIVE = 10444;

    /**
     * 流水id是非负数
     */
    int FLOW_ID_NON_NEGATIVE = 10445;

    /**
     * 流水id不存在
     */
    int FLOW_ID_UNSUPPORTED = 10446;

    /**
     * 流水id重复
     */
    int FLOW_ID_CONFLICT = 10447;

    /**
     * 缺少用户id
     */
    int USER_ID_REQUIRED = 10451;

    /**
     * 非法用户id
     */
    int USER_ID_ILLEGAL = 10452;

    /**
     * 用户id错误
     */
    int USER_ID_ERR = 10453;

    /**
     * 用户id是正整数
     */
    int USER_ID_POSITIVE = 10454;

    /**
     * 用户id是非负数
     */
    int USER_ID_NON_NEGATIVE = 10455;

    /**
     * 用户id不存在
     */
    int USER_ID_UNSUPPORTED = 10456;

    /**
     * 用户id重复
     */
    int USER_ID_CONFLICT = 10457;

    /**
     * 缺少平台id
     */
    int PLATFORM_ID_REQUIRED = 10461;

    /**
     * 非法平台id
     */
    int PLATFORM_ID_ILLEGAL = 10462;

    /**
     * 平台id错误
     */
    int PLATFORM_ID_ERR = 10463;

    /**
     * 平台id是正整数
     */
    int PLATFORM_ID_POSITIVE = 10464;

    /**
     * 平台id是非负数
     */
    int PLATFORM_ID_NON_NEGATIVE = 10465;

    /**
     * 平台id不存在
     */
    int PLATFORM_ID_UNSUPPORTED = 10466;

    /**
     * 平台id重复
     */
    int PLATFORM_ID_CONFLICT = 10467;

    /**
     * 缺少店铺id
     */
    int SHOP_ID_REQUIRED = 10471;

    /**
     * 非法店铺id
     */
    int SHOP_ID_ILLEGAL = 10472;

    /**
     * 店铺id错误
     */
    int SHOP_ID_ERR = 10473;

    /**
     * 店铺id是正整数
     */
    int SHOP_ID_POSITIVE = 10474;

    /**
     * 店铺id是非负数
     */
    int SHOP_ID_NON_NEGATIVE = 10475;

    /**
     * 店铺id不存在
     */
    int SHOP_ID_UNSUPPORTED = 10476;

    /**
     * 店铺id重复
     */
    int SHOP_ID_CONFLICT = 10477;

}
