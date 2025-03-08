# summer-boot-quartz

## yml

```
dsn:
  hostname: localhost
  port: 3306
  username: root
  password: 123456
  database: quartz
  server-time-zone: Asia/Shanghai

spring:
  quartz:
    job-store-type: jdbc
    overwrite-existing-jobs: true
    auto-startup: true
    properties:
      org:
        quartz:
          scheduler:
            instanceName: DefaultQuartzScheduler
            instanceId: AUTO
          jobStore:
            class: org.quartz.impl.jdbcjobstore.JobStoreTX
            driverDelegateClass: org.quartz.impl.jdbcjobstore.StdJDBCDelegate
            tablePrefix: QRTZ_
            isClustered: false
            useProperties: false
            dataSource: default
          dataSource:
            default:
              driver: com.mysql.cj.jdbc.Driver
              URL: jdbc:mysql://${dsn.hostname}:${dsn.port}/${dsn.database}?characterEncoding=utf-8&useSSL=false&useTimezone=true&serverTimezone=${dsn.server-time-zone}&useAffectedRows=true&allowMultiQueries=true&connectTimeout=3000&socketTimeout=10000
              user: ${dsn.username}
              password: ${dsn.password}
              maxConnections: 10
          threadPool:
            class: org.quartz.simpl.SimpleThreadPool
            threadCount: 20
            threadPriority: 5
            threadsInheritContextClassLoaderOfInitializingThread: true
```
