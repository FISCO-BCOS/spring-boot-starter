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
将节点所在目录`nodes/${ip}/sdk`下的`ca.crt`、`sdk.crt`和`sdk.key`文件拷贝到项目的`src/main/resources`目`录下供SDK使用(FISCO BCOS 2.1以前，证书为`ca.crt`、`node.crt`和`node.key`)。

### 配置文件设置

spring boot项目的配置文件application.yml如下图所示，其中加了注释的内容根据区块链节点配置做相应修改。
  
```yml
encrypt-type: # 0：普通， 1：国密
 encrypt-type: 0 
 
group-channel-connections-config:
  all-channel-connections:
  - group-id: 1  # 群组ID
    connections-str:
                    - 127.0.0.1:20200  # 节点，listen_ip:channel_listen_port
                    - 127.0.0.1:20201
  - group-id: 2  
    connections-str:
                    - 127.0.0.1:20202  # 节点，listen_ip:channel_listen_port
                    - 127.0.0.1:20203
 
channel-service:
  group-id: 1 # sdk实际连接的群组
  agency-name: fisco # 机构名称

accounts:
  pem-file: 0xcdcce60801c0a2e6bb534322c32ae528b9dec8d2.pem # PEM 格式账户文件
  p12-file: 0x98333491efac02f8ce109b0c499074d47e7779a6.p12 # PKCS12 格式账户文件
  password: 123456 # PKCS12 格式账户密码
```
项目中关于SDK配置的详细说明请[参考这里](https://fisco-bcos-documentation.readthedocs.io/zh_CN/latest/docs/sdk/sdk.html#sdk)。

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

该示例项目提供的测试案例，供开发者参考使用。测试案例主要分为对[Web3j API](https://fisco-bcos-documentation.readthedocs.io/zh_CN/latest/docs/sdk/sdk.html#web3j-api)，[Precompiled Serveice API](https://fisco-bcos-documentation.readthedocs.io/zh_CN/latest/docs/sdk/sdk.html#precompiled-service-api)、Solidity合约文件转Java合约文件、部署和调用合约的测试。

### Web3j API测试

提供Web3jApiTest测试类测试Web3j API。示例测试如下：

```java
@Test
public void getBlockNumber() throws IOException {
    BigInteger blockNumber = web3j.getBlockNumber().send().getBlockNumber();
    System.out.println(blockNumber);
    assertTrue(blockNumber.compareTo(new BigInteger("0"))>= 0);
}
```

**温馨提示：** Application类初始化了web3j对象，在业务代码需要的地方可用注解的方式直接使用，使用方式如下：

  ```java
@Autowired
private Web3j web3j
  ```

### Precompiled Service API测试

提供PrecompiledServiceApiTest测试类测试Precompiled Service API。示例测试如下：

```java
@Test
public void testSystemConfigService() throws Exception {
    SystemConfigSerivce systemConfigSerivce = new SystemConfigSerivce(web3j, credentials);
    systemConfigSerivce.setValueByKey("tx_count_limit", "2000");
    String value = web3j.getSystemConfigByKey("tx_count_limit").send().getSystemConfigByKey();
    System.out.println(value);
    assertTrue("2000".equals(value));
}
```

### Solidity合约文件转Java合约文件测试

提供SolidityFunctionWrapperGeneratorTest测试类测试Solidity合约文件转Java合约文件。示例测试如下：

```java
@Test
public void compileSolFilesToJavaTest() throws IOException {
    File solFileList = new File("src/test/resources/contract");
    File[] solFiles = solFileList.listFiles();

    for (File solFile : solFiles) {

        SolidityCompiler.Result res = SolidityCompiler.compile(solFile, true, ABI, BIN, INTERFACE, METADATA);
        System.out.println("Out: '" + res.output + "'");
        System.out.println("Err: '" + res.errors + "'");
        CompilationResult result = CompilationResult.parse(res.output);
        System.out.println("contractname  " + solFile.getName());
        Path source = Paths.get(solFile.getPath());
        String contractname = solFile.getName().split("\\.")[0];
        CompilationResult.ContractMetadata a = result.getContract(solFile.getName().split("\\.")[0]);
        System.out.println("abi   " + a.abi);
        System.out.println("bin   " + a.bin);
        FileUtils.writeStringToFile(new File("src/test/resources/solidity/" + contractname + ".abi"), a.abi);
        FileUtils.writeStringToFile(new File("src/test/resources/solidity/" + contractname + ".bin"), a.bin);
        String binFile;
        String abiFile;
        String tempDirPath = new File("src/test/java/").getAbsolutePath();
        String packageName = "org.fisco.bcos.temp";
        String filename = contractname;
        abiFile = "src/test/resources/solidity/" + filename + ".abi";
        binFile = "src/test/resources/solidity/" + filename + ".bin";
        SolidityFunctionWrapperGenerator.main(Arrays.asList(
                "-a", abiFile,
                "-b", binFile,
                "-p", packageName,
                "-o", tempDirPath
        ).toArray(new String[0]));
    }
    System.out.println("generate successfully");
}
```

该测试案例将src/test/resources/contract目录下的所有Solidity合约文件(默认提供HelloWorld合约)均转为相应的abi和bin文件，保存在src/test/resources/solidity目录下。然后将abi文件和对应的bin文件组合转换为Java合约文件，保存在src/test/java/org/fisco/bcos/temp目录下。SDK将利用Java合约文件进行合约部署与调用。

### 部署和调用合约测试

提供ContractTest测试类测试部署和调用合约。示例测试如下：

```java
@Test
public void deployAndCallHelloWorld() throws Exception {
    //deploy contract
    HelloWorld helloWorld = HelloWorld.deploy(web3j, credentials, new StaticGasProvider(gasPrice, gasLimit)).send();
    if (helloWorld != null) {
        System.out.println("HelloWorld address is: " + helloWorld.getContractAddress());
        //call set function
        helloWorld.set("Hello, World!").send();
        //call get function
        String result = helloWorld.get().send();
        System.out.println(result);
        assertTrue( "Hello, World!".equals(result));
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
