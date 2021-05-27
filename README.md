# AutoMeter

1.AutoMeter是一款针对分布式服务，微服务API做功能和性能一体的自动化测试平台。针对接口支持分布式功能和性能测试，极大的提高了技术人员的接口测试需求

![image](https://github.com/AutoMeter/testplantform/blob/master/img/dashboard.jpg)

着力解决在系统，项目开发，迭代交付过程中开发人员，测试人员针对系统提供的API做调试，回归测试，性能测试。自动化测试，本质上解决API测试的5大基本问题：
  
    1.由什么角色？
    2.在什么环境？
    3.针对什么目标？
    4.做什么样的测试？
    5.得到什么样的结果？

### 设计思路：
    1.对于测试整体活动围绕人员(测试人员，开发人员)，机器(服务器，移动设备)，用例(功能，性能)，可以认为这些是个人或者公司核心的测试资产
    2.有了上述的测试资产，来开展测试活动，比如说使用服务器来搭建测试环境，在测试环境上执行测试用例，获取响应的测试反馈报告
### 开展具体的测试活动，需要解决4个问题
    1.运行测试的环境如何定义？
    2.针对什么来做测试？
    3.怎么运行测试用例？
    4.获得什么样的反馈报告？

### 1.运行测试的环境如何定义？

![image](https://github.com/AutoMeter/testplantform/blob/master/img/%E7%8E%AF%E5%A2%83.png)

一般个人，公司在使用分布式，微服务架构，从开发到发布上线可能会经过多套环境测试验证，比如开发环境，测试环境，准生产环境，生产环境，其中测试环境又可能分为多套功能测试环境和性能测试环境，多套环境分开管理，可以有序而不相互干扰进行测试工作
每套环境由开发的发布单元(服务，站点，应用各个公司叫法不一样)，即提供api服务能力的实体，中间件(数据库，nosql，web服务器等等)这些元素组成
对于测试来说以上的元素我们需要部署到指定的服务器或者容器中整体来作为一套环境做测试工作
### 2.针对什么来做测试？

![image](https://github.com/AutoMeter/testplantform/blob/master/img/deployunit.jpg)

针对具体开发的服务(发布单元，应用，站点)，既提供API的实体，这边我们命名为发布单元，可以定义访问此服务的协议，端口。
此发布单元包含了若干个API，每个API会有对应的参数需要维护，这其实也是个人或者公司提供对内对外api能力的定义
### 3.怎么运行测试用例？

![image](https://github.com/AutoMeter/testplantform/blob/master/img/plan.jpg)

从个人或者公司的角度看，用例的数量和类型来决定需要做怎么样的执行，如果用例数量庞大，并且需要快速得到结果，本质上我们需要拆分用例由多机并行执行满足需求，也就是多点执行，如果需要性能的测试，执行性能的机器我们可以是低性能的多台机器发起或者是高性能的少量机器发起，所以说怎么运行是根据需要来定制执行用例的类型和机器数量
### 4.获得什么样的反馈报告？

![image](https://github.com/AutoMeter/testplantform/blob/master/img/report.jpg)

对于用例执行完，我们希望看到什么反馈，对于开发，测试，或者其他技术人员，我们希望看到运行的用例详细信息：结果状态，运行时间，API的具体响应，我的期望，断言的详细信息，以及用例运行时的信息，对应性能来说，我们还希望能得到统计的信息，比如整体性能的时间，tps，响应时间，99%pct等

## 架构

![image](https://github.com/AutoMeter/testplantform/blob/master/img/%E6%9E%B6%E6%9E%84%E5%9B%BE.jpg)


## 技术
    1.springboot
    2.vue
    3.mysql
    4.redis
    5.Jmeter
    

## 部署
Release/Beta版本为编译好的包，可以根据以下来部署发布

1.基础环境：

    1.mysql   5.7   
    2.redis   5.0.8
    3.jdk     1.8
    4.nodejs  8.12.0
    5.npm     6.4.1
    6.nginx   1.9.9
    
2.应用配置：

    1.testcenterservice（测试中心服务），slaverservice（执行服务），dispatchservice（调度服务）三个服务配置相同
     配置：
        applicaton.yml中配置mysql连接字(spring-datasource:url,username,password),redis连接字(spring-redis:host,port)
        applicaton.yml中spring.domain.allowdomain 配置admin的访问地址，解决跨域
        applicaton.yml中spring.logging配置日志路径path
    2.testcenterapp（后台）
     配置：
        在目录admin/static/config.js中修改配置SERVER_URL，为调用testcenterservice的ip和端口，或者使用域名  
    3.系统字典表配置
        如果需要部署多个slaver集群运行测试，则需要在字典表中配置调度服务的访问地址
        增加字典项：调度服务，字典编码：dispatchservice，字典项名：调度服务器地址,字典项值：替换成你的调度服务的访问地址
    4.nginx配置
        nginx.conf中的http.server.location.root配置/app/AutoMeter/dist/  dist为admin打包出来的静态文件目录

        
3.初始化数据库

    mysql下新建数据库testcenter运行testcenter.sql

4.启动顺序

    建议使用jenkins来编译，打包，发布以下应用服务
    1.testcenterservice
    2.slaverservice
    3.dispatchservice(单个slaverservice可以不部署dispatchservice)
    4.admin （建议nginx部署vue -npm build出来的静态文件目录dist）
    
## 部署完成后，如何开始第一个用例

   一，系统配置：
   
       1.新建测试服务器，在测试资产-服务器管理中新增一个测试服务器
       2.定义环境，在环境管理-环境管理中新增一个环境
       3.环境和服务器绑定，在环境管理-环境服务器中新增绑定
       4.定义发布单元，在发布单元-发布单元中新建一个测试目标服务
       5.服务器部署发布单元，在环境管理-组件部署中新增在某个环境的服务器下部署某个发布单元
       6.定义api，在发布单元-API下新建属于发布单元的api
       7.定义api参数，在发布单元-API参数新增参数
       8.设置运行用例的执行节点，在调度中心-执行节点中设置执行机的类型是功能还是性能,以及访问的端口（slaverservice启动后会自动注册上来）
       9.新建用例，在测试资产-用例库新增用例，定义用例是测试哪个api，期望值是什么，以及用例类型是功能还是性能，其中Jmeter-Class(后面描述）
       10.设置用例数据，在新建好的用例列表管理列中，点击参数值，设置用例的数据
       11.执行计划，在执行中心-执行计划中新建执行计划，定义计划类型是功能还是性能
       12.计划用例，新建好的执行计划中可以装载需要运行的用例，在执行计划页面管理列中-装载用例，勾选需要运行的用例
       13.运行执行计划，执行中心-勾选执行计划，点运行，设置当前运行的批次号，保存即开始运行
       14.报告中心，报告中心选择功能或者性能的报告页面查看用例的执行结果明细和统计结果
