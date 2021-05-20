# spring-boot-starter-demo

本示例项目基于SpringBoot和JavaSdk方式来调用智能合约。若您仍想使用web3sdk方式访问智能合约，请参考https://github.com/FISCO-BCOS/spring-boot-starter/tree/master-web3sdk


## 1. 部署合约

本例中采用HelloWorld合约，该合约位于src/main/contracts目录下。为了部署该合约，可以采用两种做法：
- 使用[控制台](https://fisco-bcos-documentation.readthedocs.io/zh_CN/latest/docs/installation.html#id8)部署合约;
- 运行本项目中测试用例中的[deploy](src/test/java/org/example/demo/Demos.java)用例部署合约

部署完成后，请记录合约地址，后续会用到。

## 2.证书拷贝

请将配置文件拷贝到src/main/resources/conf目录下。

## 3. 配置连接节点

请修改application.properties，该文件包含如下信息：
```
### Java sdk configuration
cryptoMaterial.certPath=conf
network.peers[0]=127.0.0.1:20200
#network.peers[1]=127.0.0.1:20201

### Contract configuration
contract.helloWorldAddress=

### System configuration
system.groupId=1
system.privateKey=

### Springboot configuration
server.port=8080

```
其中：
- java sdk configuration配置部分与[javasdk](https://fisco-bcos-documentation.readthedocs.io/zh_CN/latest/docs/sdk/java_sdk/configuration.html)一致。就本例而言，用户需要：
    * 请将network.peers更换成实际的链节点监听地址。
    * cryptoMaterial.certPath设为conf

- Contract confguration配置部分，需要配置：
    * contract.helloWorldAddress设为前述HelloWorld合约地址

- System configuration配置部分，需要配置：
    * system.hexPrivateKey是16进制的私钥明文，可运行测试用例中的[keyGeneration](src/test/java/org/example/demo/Demos.java)生成。如果为空，系统会随机生成一个私钥。
    * system.groupId设为目标群组，默认为1


## 4. 编译和运行
您可以在idea内直接运行，也可以编译成可执行jar包后运行。以编译jar包方式为例：

```
cd spring-boot-starter-demo
./gradlew bootJar
cd dist
```
会在dist目录生成spring-boot-starter-demo-exec.jar，可执行此jar包：
```
java -jar spring-boot-starter-demo-exec.jar
```
随后，即可访问相关接口。

set示例：

```
curl http://127.0.0.1:8080/hello/set?n=hello
```
返回示例：
```
0x1c8b283daef12b38632e8a6b8fe4d798e053feb5128d9eaf2be77c324645763b
```

get示例：

```
curl http://127.0.0.1:8080/hello/get
```
返回示例：
```
["hello"]
```


## 加入我们的社区

**FISCO BCOS开源社区**是国内活跃的开源社区，社区长期为机构和个人开发者提供各类支持与帮助。已有来自各行业的数千名技术爱好者在研究和使用FISCO BCOS。如您对FISCO BCOS开源技术及应用感兴趣，欢迎加入社区获得更多支持与帮助。

![](https://raw.githubusercontent.com/FISCO-BCOS/LargeFiles/master/images/QR_image.png)

## 相关链接

- FISCO BCOS工程： [FISCO BCOS文档](https://fisco-bcos-documentation.readthedocs.io/zh_CN/latest/docs/introduction.html)。
- Java Sdk： [JavaSdk文档](https://fisco-bcos-documentation.readthedocs.io/zh_CN/latest/docs/sdk/java_sdk/index.html)。
- SpringBoot文档： [Spring Boot](https://spring.io/guides/gs/spring-boot/)。
