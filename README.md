# AutoMeter

1.AutoMeter是一款针对分布式服务，微服务API做功能和性能一体的自动化测试平台，着力解决在系统，项目开发，迭代交付过程中
开发人员，测试人员针对系统提供的API做调试，回归测试，性能测试。自动化测试，本质上解决API测试的5大基本问题：

    1.由什么角色？
    2.在什么环境？
    3.针对什么目标？
    4.做什么样的测试？
    5.得到什么样的结果？

## 系统操作指南

   1.[测试平台定位和使用思路](../../wiki/测试平台定位和使用思路)

## 架构

## demo
http://centeradmin.cdmtzz.com/#/login
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
       
       
