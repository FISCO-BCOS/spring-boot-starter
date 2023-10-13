# bcos-springboot-starter
本示例项目基于Java SDK + Maven + SpringBoot方式来调用智能合约。
## 一.项目结构
annotion:存放注解
client:fiscobcos客户端，客户端默认提供了crud方法，如果有自定义的方法，继承此类即可，如果想使用此类的方法，在编写智能合约的时候，函数的名称必须为:insert,queryById,updateById,removeById。
common:提供合约的基础方法
config:项目相关配置
contract:生成合约编译后的java程序
deploy:部署合约方法
entity:存放实体类
utils:工具类
## 二.核心类介绍
FManager:通过该类可以拿到FiscoClient和BcosSDK实例，通过这两个对象可以对合约进行相关操作。
## 三.使用方法
### 1. 下载依赖
```
        <dependency>
            <groupId>com.gitee.zhangyutong</groupId>
            <artifactId>bcos-springboot-starter</artifactId>
            <version>3.0.1-Beta</version>
        </dependency>
```
### 2. 配置配置文件
在application.yml文件中加入以下配置:
```yaml
fisco:
  peers:
    - ip:端口
  enabled: false
  basePackages: 
```
下面对配置字段做下说明:
peers:配置fiscobcos的节点，可配置多个
enabled:是否部署合约
basePackages:合约包路径，需要结合enabled字段使用，当enabled为true时,根据包路径进行合约的部署。
### 3.编写controller代码
controller主要有核心代码:
```java
 FiscoClient fiscoClient = FManager.getFiscoClient();
```
首先需要拿到客户端，使用客户端可以对智能合约进行crud操作。下面对fiscoClient四个方法进行说明:
#### (1)queryById
此方法有三个参数:第一个参数为查询的id,第二个参数为合约的名称，项目在自动部署的时候默认使用类名作为合约名称，第三个参数为合约的类。
### (2)insert
此方法有三个参数:第一个参数为插入的list信息，list的第一个值参数为主键，后面是需要新增的值，其他两个参数同queryById。
### (3)updateById
此方法共有一个参数，接收list信息，list集合的第一个值为修改的主键，后面为修改的值。
### (4)removeById
此方法共一个参数，根据id删除数据。
### (5)下面给出一个案例供参考:
```java
@RestController
public class GrxxController {

    @GetMapping("/query/{id}")
    public ResponseData queryById(@PathVariable("id") String id) throws Exception {
        FiscoClient fiscoClient = FManager.getFiscoClient();
        TransactionReceipt transactionReceipt = (TransactionReceipt) fiscoClient.queryById(id,"My", My.class);
        String output = transactionReceipt.getOutput();
        return ResponseData.success(fiscoClient.decodeMethodAndGetOutputAbiObjectParser(false,output,"selectById"));
    }

    @PostMapping("/insert")
    public ResponseData insert(@RequestBody List<String> value) throws Exception {
        FiscoClient fiscoClient = FManager.getFiscoClient();
        fiscoClient.insert(value,"My", My.class);
        return ResponseData.success("新增成功");
    }

    @PutMapping("/update")
    public ResponseData updateById(@RequestBody List<String> value) throws Exception {
        FiscoClient fiscoClient = FManager.getFiscoClient();
        fiscoClient.updateById(value,"My", My.class);
        return ResponseData.success("修改成功");
    }

    @DeleteMapping("/remove/{id}")
    public ResponseData removeById(@PathVariable("id") String id) throws Exception {
        FiscoClient fiscoClient = FManager.getFiscoClient();
        fiscoClient.removeById(id,"My", My.class);
        return ResponseData.success("删除成功");
    }
}
```
## 四.补充
### 1.
项目提供了两种方法实现合约的自动部署
#### (1)配置配置文件部署
需要配置enabled和basePackages字段，然后再智能合约的java代码类上加上@EnableDeploy注解，可实现合约的自动部署。
#### (2)使用注解实现合约的部署
在springboot的启动类上加上@EnableScan注解，并配置packages,可实现合约的自动部署。
### 2.所有的合约都是基于内存部署
### 3.本项目基于fisco-bcos-java-sdk3.4.0开发
### 4.FiscoClient的方法是基于合约编写，不同的合约FiscoClient的一些基本方法可能不适用，需要自己编写。继承FiscoClient

