# AutoMeter

1.AutoMeter是一款针对分布式服务，微服务API做功能和性能一体的自动化测试平台，着力解决在系统，项目开发，迭代交付过程中
开发人员，测试人员针对系统提供的API做调试，回归测试，性能测试。自动化测试，本质上解决API测试的5大基本问题：

    1.由什么角色？
    2.在什么环境？
    3.针对什么目标？
    4.做什么样的测试？
    5.得到什么样的结果？

### 设计思路：
## AutoMeter测试平台定位是希望解决在使用分布式服务，微服务架构的个人或者公司，API的自动化测试工作
    1.对于测试整体活动围绕人员(测试人员，开发人员)，机器(服务器，移动设备)，用例(功能，性能)，可以认为这些是个人或者公司核心的测试资产
    2.有了上述的测试资产，来开展测试活动，比如说使用服务器来搭建测试环境，在测试环境上执行测试用例，获取响应的测试反馈报告
### 开展具体的测试活动，需要解决4个问题
    1.运行测试的环境如何定义？
    2.针对什么来做测试？
    3.怎么运行测试用例？
    4.获得什么样的反馈报告？

### 1.运行测试的环境如何定义？
一般个人，公司在使用分布式，微服务架构，从开发到发布上线可能会经过多套环境测试验证，比如开发环境，测试环境，准生产环境，生产环境，其中测试环境又可能分为多套功能测试环境和性能测试环境，多套环境分开管理，可以有序而不相互干扰进行测试工作
每套环境由开发的发布单元(服务，站点，应用各个公司叫法不一样)，即提供api服务能力的实体，中间件(数据库，nosql，web服务器等等)这些元素组成
对于测试来说以上的元素我们需要部署到指定的服务器或者容器中整体来作为一套环境做测试工作
### 2.针对什么来做测试？
针对具体开发的服务(发布单元，应用，站点)，既提供API的实体，这边我们命名为发布单元，可以定义访问此服务的协议，端口。
此发布单元包含了若干个API，每个API会有对应的参数需要维护，这其实也是个人或者公司提供对内对外api能力的定义
### 3.怎么运行测试用例？
从个人或者公司的角度看，用例的数量和类型来决定需要做怎么样的执行，如果用例数量庞大，并且需要快速得到结果，本质上我们需要拆分用例由多机并行执行满足需求，也就是多点执行，如果需要性能的测试，执行性能的机器我们可以是低性能的多台机器发起或者是高性能的少量机器发起，所以说怎么运行是根据需要来定制执行用例的类型和机器数量
### 4.获得什么样的反馈报告？
对于用例执行完，我们希望看到什么反馈，对于开发，测试，或者其他技术人员，我们希望看到运行的用例详细信息：结果状态，运行时间，API的具体响应，我的期望，断言的详细信息，以及用例运行时的信息，对应性能来说，我们还希望能得到统计的信息，比如整体性能的时间，tps，响应时间，99%pct等

## 架构

## demo

    2. MyBatis（[官方中文文档](http://www.mybatis.org/mybatis-3/zh/index.html)）
    [Demo]: http://centeradmin.cdmtzz.com/#/login
    账号hellotest 密码：123456

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
        配置src/resource/app.properties中配置mysql.host，username，password
        
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
    
## 部署完成后，如何开始第一个用例
   一，系统配置：
   
       1.新建测试服务器，在测试资产-服务器管理中新增一个测试服务器
       2.定义环境，在环境管理-环境管理中新增一个环境
       3.环境和服务器绑定，在环境管理-环境服务器中新增绑定
       4.定义发布单元，在发布单元-发布单元中新建一个测试目标服务
       5.服务器部署发布单元，在环境管理-组件部署中新增在某个环境的服务器下部署某个发布单元
       6.定义api，在发布单元-API下新建属于发布单元的api
       7.定义api参数，在发布单元-API参数新增参数
       8.设置运行用例的执行节点，在调度中心-执行节点中设置执行机的类型是功能还是性能（slaverservice启动后会自动注册上来）
       9.新建用例，在测试资产-用例库新增用例，定义用例是测试哪个api，期望值是什么，以及用例类型是功能还是性能，其中Jmeter-Class(后面描述）
       10.设置用例数据，在新建好的用例列表管理列中，点击参数值，设置用例的数据
       11.执行计划，在执行中心-执行计划中新建执行计划，定义计划类型是功能还是性能
       12.装载用例，新建好的执行计划中可以装载需要运行的用例，在执行计划页面管理列中-装载用例，勾选需要运行的用例
       13.运行执行计划，执行中心-勾选执行计划，点运行，设置当前运行的批次号，保存即开始运行
       14.报告中心，报告中心选择功能或者性能的报告页面查看用例的执行结果
       
       
   二，用例开发步骤
   
       用例的开发是基于Jmeter的java-Sample来运行用例的，所以我们在平台上在创建用例的时候，我们需要设置Jmeter-Class，在Jmeter的Java工程中一个class对应一个用例的测试，平台是通过：发布单元名+Jmeter-Class类的方式找到具体的Java用例类来运行

       1.用例的开发是一个java工程，使用maven来打包成api-jmeter-autotest-1.0.jar，打包后放到jmeter的lib/exts/目录下后，平台执行会通过Jmeter-Class名来调用用例类
       2.java工程中的main/java/test/目录下面创建和平台相同的发布单元名的目录，假设我们在平台上要测试的发布单元为helloworldservice，则在main/java/test/下也创建helloworldservice
       3.在第二步创建发布单元目录下创建用例类，只需要拷贝main/java/test/helloworldservice/目录下的HelloWorld例子类，改名成新的用例类名，然后在新的用例类中编写断言
       
       
       
       具体测试用例类的编写断言方法：
       
       1.在新建的用例类中，在方法runTest中的“用例断言区”按照例子编写断言:
       2.actualResult=sendCaseRequest(),actualResult为用例请求返回的结果，根据返回类型自己解析，例子为json，转换为JSONObject解析
       3.getCaseExpectValue方法获取在平台上编写用例的期望字段值，例如code=100001,getCaseExpectValue("code")既可以获取值100001
       4.获取到实际值和期望值后，再使用AssertEqual(expect,actual)做断言，并且返回断言的结果，用例支持多字段断言，例如断言中先判断code是否正确，再判断status是否正确，再判断其他

    helloworld代码例子：
    
    public SampleResult runTest(JavaSamplerContext ctx) {
        SampleResult results = new SampleResult();
        //Jmeter java实例开始执行
        results.sampleStart();
        //用例多次断言信息汇总
        String assertInfo = "";
        //断言对象
        TestAssert testAssert = new TestAssert();
        try {
            // 初始化用例数据
            initalTestData(ctx);
            // 发送用例请求，并返回结果
            actualResult=sendCaseRequest();
            // ===========================用例断言区，新开发一个用例，需要在此编写用例断言======================================
            // 此例子返回类型为json格式，把请求返回值actualResult转换成JSONObject对象，新的用例开发根据实际返回类型做相应断言处理
            JSONObject actualResultObject = JSONObject.parseObject(actualResult);
            // ---------------断言status步骤开始-------------------------------------
            //获取期望值的status结果
            String expectStatus = getCaseExpectValue("status");
            //获取实际值status结果
            String actualStatus = actualResultObject.get("status").toString();
            //日志记录实际值
            getLogger().info(TestCore.logplannameandcasename + "actualStatus is:" + actualStatus);
            // 完成期望值status和实际值status的比较，并且收集断言结果到assertInfo中
            assertInfo = testAssert.AssertEqual(expectStatus, actualStatus);
            // ---------------断言status步骤结束-------------------------------------

            // ---------------断言msg步骤开始----------------------------------------
            //获取期望值的msg结果
            String expectMsg = getCaseExpectValue("msg");
            //获取实际值msg结果
            String actualMsg = actualResultObject.get("msg").toString();
            //日志记录实际值
            getLogger().info(TestCore.logplannameandcasename + "actualMsg is:" + actualMsg);
            // 完成期望值和实际值msg的比较，并且收集断言结果到assertInfo中
            assertInfo = testAssert.AssertEqual(expectMsg, actualMsg);
            // ---------------断言msg步骤结束----------------------------------------
            // ===========================用例断言区========================================================================
        } catch (Exception ex) {
            caseException(results, testAssert, ex.getMessage());
        } finally {
            // 保存用例运行结果，Jmeter的sample运行结果
            caseFinish(results, testAssert, assertInfo);
        }
        //Jmeter事务，表示这是事务的结束点
        results.sampleEnd();
        return results;
    }
       
       
