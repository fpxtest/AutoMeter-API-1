# AutoMeter

1.AutoMeter是一款针对分布式服务，微服务API做功能和性能一体的自动化测试平台，着力解决在系统，项目开发，迭代交付过程中
开发人员，测试人员针对系统提供的API做调试，回归测试，性能测试。自动化测试，本质上解决API测试的5大基本问题：

    1.由什么角色？
    2.在什么环境？
    3.针对什么目标？
    4.做什么样的测试？
    5.得到什么样的结果？

## 架构



## 技术
    1.springboot
    2.vue
    3.mysql
    4.redis
    5.Jmeter
    

## 部署
1.基础环境：

    1.mysql   5.7   
    2.redis   5.0.8
    3.jdk     1.8
    4.nodejs  8.12.0
    5.npm     6.4.1
    6.nginx   1.9.9
    
2.应用配置：

    1.testcenterservice （测试中心服务）
     配置：
        1.applicaton.yml中配置mysql连接字(spring-datasource:url,username,password),redis连接字(spring-redis:host,port)
        spring:
          devtools:
            restart:
              # 修改代码后自动重启
              enabled: true
          # 数据源（应该全部加密）
          datasource:
            url: jdbc:mysql://127.0.0.1:3306/testcenter?useUnicode=true&useSSL=false&allowMultiQueries=true&characterEncoding=utf-8&useLegacyDatetimeCode=false&serverTimezone=UTC
            # 用户名 test
            username: MyEnc({Qjn8FaKnAiApiz6g3RM5Ow==})
            # 密码 test
            password: MyEnc({w5CeK9tnNUROTUGdRRmd1Q==})
          redis:
            # 数据库索引（默认为0）
            database: 0
            # 服务器地址
            host: 127.0.0.1
            # 服务器连接端口
            port: 6379
    2.slaverservice  （执行服务）
        配置和testcenterservice一样
    3.dispatchservice （调度服务）
        配置和testcenterservice一样
    4.admin（后台）
        在目录admin/config/prod.env.js中修改配置BASE_API项，为调用testcenterservice的ip和端口，或者使用域名
        module.exports = {
          NODE_ENV: '"production"',
          BASE_API: '"http://xxxxxxxxxx"'
        }
    5.api-jmeter-autotest (编写测试用例类)
        配置src/resource/app.properties,数据库访问连接字
        mysql.host=jdbc:mysql://127.0.0.1:3306/testcenter?useUnicode=true&useSSL=false&allowMultiQueries=true&characterEncoding=utf-8&useLegacyDatetimeCode=false&serverTimezone=UTC
        username=xxxxxx
        password=xxxxxx
        
    6.系统字典表配置
        如果需要部署多个slaver集群运行测试，则需要在字典表中配置调度服务的访问地址
        增加字典项：调度服务，字典编码：dispatchservice，字典项名：调度服务器地址,字典项值：替换成你的调度服务的访问地址

3.初始化数据库

    mysql下运行init.sql

4.启动顺序

    建议使用jenkins来编译，打包，发布以下应用服务
    1.testcenterservice
    2.slaverservice
    3.dispatchservice(单个slaverservice可以不部署dispatchservice)
    4.admin （建议nginx部署npm build出来的静态文件）
    
## 系统模块操作介绍
    
    1.系统管理：
        用户，角色，的创建
        角色权限分配
        字典表的配置
    2.资产管理：在公司的角度定义测试资产有什么
        测试服务器，用来部署应用，服务，发布单元，执行测试
        测试用例库，技术人员针对API设计的功能，性能用例
        前后置条件，测试用例运行前后可能需要先做一些初始化的操作，以及用例运行完成后需要做一些清理相关操作
    3.环境管理：可以来管理测试工作需要哪些环境，可以按需要定义多套环境，比如功能测试环境，性能测试环境
        环境服务器，每套环境规划由不同的服务器来组成
        环境组件，每套环境里面除了部署应用，服务，发布单元，还需要部署中间件资源，比如mysql，redis
        组件部署，对指定的环境和服务器绑定组件或者发布单元
    4.发布单元，管理公司开发的服务单元
        API管理，在具体某个服务单元下的API定义，维护
        API参数，针对API的参数定义，维护
    5.调度中心，统一管理有多少个执行测试的slaver，以及当前每个salver执行测试用例的状态
        节点管理，slaverservice启动后会注册进来，可以查看当前有多少执行机在工作
        调度管理，对于当前系统执行的用例在不同的执行机上的执行状态
    6.执行中心，测试用例执行的入口
        执行计划，对于需要执行的测试用例的一个归类到某个计划中，执行计划既执行计划中包含的测试用例
    7.报告中心，提供功能和性能的测试报告，分为明细和统计分析两大类
        功能测试报告，功能用例的执行结果明细
        功能报告分析，功能用例执行的整体统计结果分析
        性能测试明细，性能测试多线程运行的所有用例明细结果
        性能测试统计，性能测试完成后统计用例的性能指标，例如tps，成功次数，失败次数，总耗时，99%响应时间分布

[My Awesome Wiki](../../wiki)



