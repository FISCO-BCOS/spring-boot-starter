![](https://github.com/FISCO-BCOS/FISCO-BCOS/raw/master/docs/images/FISCO_BCOS_Logo.svg?sanitize=true)

[English](../README.md) / 中文

# Spring Boot Starter
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com)
[![Build Status](https://travis-ci.org/FISCO-BCOS/spring-boot-starter.svg?branch=master)](https://travis-ci.org/FISCO-BCOS/spring-boot-starter)
[![CodeFactor](https://www.codefactor.io/repository/github/fisco-bcos/spring-boot-starter/badge)](https://www.codefactor.io/repository/github/fisco-bcos/spring-boot-starter)
---

该项目是基于[Web3SDK](https://fisco-bcos-documentation.readthedocs.io/zh_CN/latest/docs/sdk/sdk.html)的spring boot版本的示例项目。提供FISCO BCOS区块链应用开发的基本框架和基本的测试案例，帮助开发者基于FISCO BCOS区块链快速进行应用开发。**此版本只支持**[FISCO BCOS 2.0+](https://fisco-bcos-documentation.readthedocs.io/zh_CN/latest/docs/introduction.html)。

## 快速启动

### 前置条件
搭建FISCO BCOS区块链，具体步骤[参考这里](https://fisco-bcos-documentation.readthedocs.io/zh_CN/latest/docs/installation.html)。

### 获取源码

```
$ git clone https://github.com/FISCO-BCOS/spring-boot-starter.git
```

#### 节点证书配置
将节点所在目录`nodes/${ip}/sdk`下的`ca.crt`、`sdk.crt`和`sdk.key`文件拷贝到项目的`src/main/resources/conf`目录下供SDK使用(FISCO BCOS 2.1以前，证书为`ca.crt`、`node.crt`和`node.key`)。

### 配置文件设置

spring boot项目的配置文件application.yml如下图所示，其中加了注释的内容根据区块链节点配置做相应修改。
  
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans-4.0.xsd">
	<bean id="defaultConfigProperty" class="org.fisco.bcos.sdk.config.model.ConfigProperty">
		<property name="cryptoMaterial">
			<map>
			<entry key="certPath" value="conf" />
			<!-- SSL certificate configuration -->
			<!-- entry key="caCert" value="conf/ca.crt" /-->
			<!-- entry key="sslCert" value="conf/sdk.crt" /-->
			<!-- entry key="sslKey" value="conf/sdk.key" /-->
			<!-- GM SSL certificate configuration -->
			<!-- entry key="caCert" value="conf/gm/gmca.crt" /-->
			<!-- entry key="sslCert" value="conf/gm/gmsdk.crt" /-->
			<!-- entry key="sslKey" value="conf/gm/gmsdk.key" /-->
			<!--entry key="enSslCert" value="conf/gm/gmensdk.crt" /-->
			<!--entry key="enSslKey" value="conf/gm/gmensdk.key" /-->
			</map>
		</property>
		<property name="network">
			<map>
				<entry key="peers">
					<list>
						<value>127.0.0.1:20200</value>
						<value>127.0.0.1:20201</value>
					</list>
				</entry>
			</map>
		</property>
		<property name="account">
			<map>
				<entry key="keyStoreDir" value="account" />
				<entry key="accountAddress" value="" />
				<entry key="accountFileFormat" value="pem" />
				<entry key="password" value="" />
				<entry key="accountFilePath" value="" />
			</map>
		</property>
		<property name="threadPool">
			<map>
			<entry key="channelProcessorThreadSize" value="16" />
			<entry key="receiptProcessorThreadSize" value="16" />
			<entry key="maxBlockingQueueSize" value="102400" />
			</map>
		</property>
	</bean>

	<bean id="defaultConfigOption" class="org.fisco.bcos.sdk.config.ConfigOption">
		<constructor-arg name="configProperty">
				<ref bean="defaultConfigProperty"/>
		</constructor-arg>
	</bean>

	<bean id="bcosSDK" class="org.fisco.bcos.sdk.BcosSDK">
		<constructor-arg name="configOption">
			<ref bean="defaultConfigOption"/>
		</constructor-arg>
	</bean>
</beans>
```

### 运行

编译并运行测试案例，在项目根目录下运行：
```
$ cd spring-boot-starter
$ ./gradlew build
$ ./gradlew test
```

当所有测试案例运行成功，则代表区块链运行正常，该项目通过SDK连接区块链正常。开发者可以基于该项目进行具体应用开发。

**注：如果在IntelliJ IDEA或Eclipse中运行该demo工程，则使用gradle wrapper模式，此外IntelliJ IDEA需要在设置中开启`Annotation Processors`功能。**

## 测试案例介绍

### 初始化BcosSDK

```java
public abstract class BaseTest {
    protected BcosSDK bcosSDK;
    protected Client client;

    public void init() {
        @SuppressWarnings("resource")
        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

        bcosSDK = context.getBean(BcosSDK.class);
        client = bcosSDK.getClient(1);
    }
}
```

### Java SDK API测试

提供JavaSDKApiTest测试类测试Java SDK API。示例测试如下：

```java
  @Test
  public void getBlockNumber() throws IOException {
        init();
        BigInteger blockNumber = client.getBlockNumber().getBlockNumber();
        assertTrue(blockNumber.compareTo(new BigInteger("0")) >= 0);
  }
```

### Precompiled Service API测试

提供PrecompiledServiceApiTest测试类测试Precompiled Service API。示例测试如下：

```java
  @Test
  public void testSystemConfigService() throws Exception {
        init();
        SystemConfigService systemConfigService =
                new SystemConfigService(client, client.getCryptoSuite().createKeyPair());
        systemConfigService.setValueByKey("tx_count_limit", "2000");
        String value = client.getSystemConfigByKey("tx_count_limit").getSystemConfig();
        System.out.println(value);
        assertTrue("2000".equals(value));
  }
```

### 部署和调用合约测试

提供ContractTest测试类测试部署和调用合约。示例测试如下：

```java
 @Test
  public void deployAndCallHelloWorld() throws Exception {
        init();
        // deploy contract
        HelloWorld helloWorld = HelloWorld.deploy(client, client.getCryptoSuite().createKeyPair());
        if (helloWorld != null) {
            System.out.println("HelloWorld address is: " + helloWorld.getContractAddress());
            // call set function
            helloWorld.set("Hello, World!");
            // call get function
            String result = helloWorld.get();
            System.out.println(result);
            assertTrue("Hello, World!".equals(result));
        }
  }
```

## 贡献代码
- 我们欢迎并非常感谢您的贡献，请参阅[代码贡献流程](https://mp.weixin.qq.com/s/hEn2rxqnqp0dF6OKH6Ua-A)和[代码规范](./CONTRIBUTING_CN.md)。
- 如项目对您有帮助，欢迎star支持！

## 加入我们的社区

**FISCO BCOS开源社区**是国内活跃的开源社区，社区长期为机构和个人开发者提供各类支持与帮助。已有来自各行业的数千名技术爱好者在研究和使用FISCO BCOS。如您对FISCO BCOS开源技术及应用感兴趣，欢迎加入社区获得更多支持与帮助。

![](https://media.githubusercontent.com/media/FISCO-BCOS/LargeFiles/master/images/QR_image.png)

## 相关链接

- 了解FISCO BCOS项目，请参考[FISCO BCOS文档](https://fisco-bcos-documentation.readthedocs.io/zh_CN/latest/docs/introduction.html)。
- 了解Web3SDK项目，请参考[Web3SDK文档](https://fisco-bcos-documentation.readthedocs.io/zh_CN/latest/docs/sdk/sdk.html)。
- 了解spring boot，请参考[Spring Boot官网](https://spring.io/guides/gs/spring-boot/)。
