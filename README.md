![](https://github.com/FISCO-BCOS/FISCO-BCOS/raw/master/docs/images/FISCO_BCOS_Logo.svg?sanitize=true)

English / [中文](doc/README_CN.md)

# Spring Boot Starter
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com)
[![Build Status](https://travis-ci.org/FISCO-BCOS/spring-boot-starter.svg?branch=master)](https://travis-ci.org/FISCO-BCOS/spring-boot-starter)
[![CodeFactor](https://www.codefactor.io/repository/github/fisco-bcos/spring-boot-starter/badge)](https://www.codefactor.io/repository/github/fisco-bcos/spring-boot-starter)
---

The sample spring boot project is based on [Web3SDK](https://fisco-bcos-documentation.readthedocs.io/en/latest/docs/sdk/sdk.html), which provides the basic framework and basic test cases for blockchain application and helps developers to quickly develop applications based on the FISCO BCOS blockchain. **The version only supports** [FISCO BCOS 2.0+](https://fisco-bcos-documentation.readthedocs.io/en/latest/).

## Quickstart

### Precodition

Build FISCO BCOS blockchain, please check out [here](https://fisco-bcos-documentation.readthedocs.io/en/latest/docs/installation.html)。

### Download

```
$ git clone https://github.com/FISCO-BCOS/spring-boot-starter.git
```
#### Certificate Configuration
Copy the `ca.crt`, `sdk.crt`, and `sdk.key` files in the node's directory `nodes/${ip}/sdk` to the project's `src/main/resources/conf` directory.(Before FISCO BCOS 2.1, the certificate files are `ca.crt`, `node.crt` and `node.key`)

### Settings

The `applicationContext.xml` of the spring boot project is shown below, and the commented content is modified according to the blockchain node configuration.
  
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

### Run

Compile and run test cases:

```
$ cd spring-boot-starter
$ ./gradlew build
$ ./gradlew test
```

When all test cases run successfully, it means that the blockchain is running normally,and the project is connected to the blockchain through the SDK. You can develop your blockchain application based on the project。

**Note: If you run the demo project in IntelliJ IDEA or Eclipse, please use gradle wrapper mode. In addition, please enable `Annotation Processors` in `Settings` for IntelliJ IDEA.**

## Test Case Introduction

### init BcosSDK

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

### Java SDK API Test

Provide `JavaSDKApiTest` class to test the Java SDK API. The sample test is as follows:

```java
  @Test
  public void getBlockNumber() throws IOException {
        init();
        BigInteger blockNumber = client.getBlockNumber().getBlockNumber();
        assertTrue(blockNumber.compareTo(new BigInteger("0")) >= 0);
  }
```

### Precompiled Service API Test

Provide `PrecompiledServiceApiTest` class to test the Precompiled Service API。The sample test is as follows:

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

### Deployment and Invocation Contract Test

Provide `ContractTest` class to test deploy and call contracts. The sample test is as follows:

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

## Code Contribution

- Your contributions are most welcome and appreciated. Please read the [contribution instructions ](CONTRIBUTING.md).
- If this project is useful to you, please star us on GitHub project page!

## Join Our Community

The FISCO BCOS community is one of the most active open-source blockchain communities in China. It provides long-term technical support for both institutional and individual developers and users of FISCO BCOS. Thousands of technical enthusiasts from numerous industry sectors have joined this community, studying and using FISCO BCOS platform. If you are also interested, you are most welcome to join us for more support and fun.

![](https://media.githubusercontent.com/media/FISCO-BCOS/LargeFiles/master/images/QR_image_en.png)

## Related Links

- For FISCO BCOS project, please check out [FISCO BCOS Documentation](https://fisco-bcos-documentation.readthedocs.io/en/latest/docs/introduction.html)。
- For Java SDK project, please check out [Java SDK Documentation](https://fisco-bcos-documentation.readthedocs.io/zh_CN/latest/docs/sdk/java_sdk/index.html)。
- For Spring Boot applications, please check out [Spring Boot](https://spring.io/guides/gs/spring-boot/)。
