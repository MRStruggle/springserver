# springserver

采用spring 4.3.9 版本  纯注解方式实现 web rest api 的服务接口

持久层采用 mybatis

数据库采用 mysql  


###
为了实现多数据源配置，使用了aop， 

必须在controller 控制器 方法上打上注解  @DataSource(value="值")
值 =  Config.java 中 getDynamicDataSource() 方法里面 map的key，

Config.java 中 getDynamicDataSource() 方法里面  需要 什么数据库  这里面  就配置ip  username password

业务层实现类 和 dao接口类 上 打上事物注解   @Transactional  实现事物

controller 控制器中 一定要去掉@Transactional 这个注解



定时调度任务 

@Scheduled(fixedRate = 1000 * 10,initialDelay = 1000 * 5)



具体细节欢迎指导 交流
