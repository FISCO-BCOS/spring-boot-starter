English / [中文](doc/README_CN.md)

# Spring Boot Starter

The sample spring boot project is based on [Web3SDK](https://fisco-bcos-documentation.readthedocs.io/zh_CN/release-2.0/docs/sdk/sdk.html), which provides the basic framework and basic test cases for blockchain application and helps developers to quickly develop applications based on the FISCO BCOS blockchain. **The version only supports** [FISCO BCOS 2.0](https://fisco-bcos-documentation.readthedocs.io/zh_CN/release-2.0/docs/introduction.html).

## Quickstart

### Precodition
Build FISCO BCOS blockchain, please check out [here](https://fisco-bcos-documentation.readthedocs.io/zh_CN/release-2.0/docs/installation.html)。

### Configuration

### Download
```
$ git clone https://github.com/FISCO-BCOS/spring-boot-starter.git
```
#### Certificate Configuration
Copy the `ca.crt`, `node.crt`, and `node.key` files in the node's directory `nodes/${ip}/sdk` to the project's `src/main/resources` directory.

#### Settings
The `application.yml` of the spring boot project is shown below, and the commented content is modified according to the blockchain node configuration.
  
```yml
encryptType: 0  # 0:standard, 1:guomi
groupChannelConnectionsConfig:
  allChannelConnections:
  - groupId: 1  #group ID
    connectionsStr:
                    - 127.0.0.1:20200  # node listen_ip:channel_listen_port
                    - 127.0.0.1:20201
  - groupId: 2
    connectionsStr:
                    - 127.0.0.1:20202
                    - 127.0.0.1:20203
channelService:
  groupId: 1 # The specified group to which the SDK connects
  orgID: fisco # agency name
```
A detail description of the SDK configuration for the project, please checkout [ here](https://fisco-bcos-documentation.readthedocs.io/zh_CN/release-2.0/docs/sdk/sdk.html#sdk)。

### Run
Compile and run test cases:
```
$ ./gradlew build
```
When all test cases run successfully, it means that the blockchain is running normally,and the project is connected to the blockchain through the SDK. You can develop your blockchain application based on the project。

## Test Case Introduction

The sample project provides test cases for developers to use. The test cases are mainly divided into tests for [Web3j API](https://fisco-bcos-documentation.readthedocs.io/zh_CN/release-2.0/docs/sdk/sdk.html#web3j-api), [Precompiled Serveice API](https://fisco-bcos-documentation.readthedocs.io/zh_CN/release-2.0/docs/sdk/sdk.html#precompiled-service-api), Solidity contract file to Java contract file, deployment and call contract.

### Web3j API Test
Provide `Web3jApiTest` class to test the Web3j API. The sample test is as follows:
```
@Test
public void getBlockNumber() throws IOException {
    BigInteger blockNumber = web3j.getBlockNumber().send().getBlockNumber();
    System.out.println(blockNumber);
    assertTrue(blockNumber.compareTo(new BigInteger("0"))>= 0);
}
```
**Tips:** The `Application` class initializes the Web3j object, which can be used directly in the way where the business code needs it. The usage is as follows:
  ```
@Autowired
private Web3j web3j
  ```

### Precompiled Service API Test
Provide `PrecompiledServiceApiTest` class to test the Precompiled Service API。The sample test is as follows:
```API
@Test
public void testSystemConfigService() throws Exception {
    SystemConfigSerivce systemConfigSerivce = new SystemConfigSerivce(web3j, credentials);
    systemConfigSerivce.setValueByKey("tx_count_limit", "2000");
    String value = web3j.getSystemConfigByKey("tx_count_limit").send().getSystemConfigByKey();
    System.out.println(value);
    assertTrue("2000".equals(value));
}
```

### Solidity contract file to Java contract file Test
Provide `SolidityFunctionWrapperGeneratorTest` class to test contract compilation. The sample test is as follows:
```API
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
This test case converts all Solidity contract files (`HelloWorld` contract provided by default) in the `src/test/resources/contract` directory to the corresponding `abi` and `bin` files, and save them in the `src/test/resources/solidity` directory. Then convert the `abi` file and the corresponding `bin` file combination into a Java contract file, which is saved in the `src/test/java/org/fisco/bcos/temp` directory. The SDK will use the Java contract file for contract deployment and invocation.

### Deployment and Invocation Contract Test
Provide `ContractTest` class to test deploy and call contracts. The sample test is as follows:
```
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

## Related Links
- For FISCO BCOS project, please check out [FISCO BCOS Documentation](https://fisco-bcos-documentation.readthedocs.io/zh_CN/release-2.0/docs/introduction.html)。
- For Web3SDK project, please check out [Web3SDK Documentation](https://fisco-bcos-documentation.readthedocs.io/zh_CN/release-2.0/docs/sdk/sdk.html)。
- For Spring Boot applications, please check out [Spring Boot](https://spring.io/guides/gs/spring-boot/)。

## Community

By the end of 2018, Financial Blockchain Shenzhen Consortium (FISCO) has attracted and admitted more than 100 members from 6 sectors including banking, fund management, securities brokerage, insurance, regional equity exchanges, and financial information service companies. The first members include the following organizations: Beyondsoft, Huawei, Shenzhen Securities Communications, Digital China, Forms Syntron, Tencent, WeBank, Yuexiu FinTech.

- Join our WeChat [![Scan](https://img.shields.io/badge/style-Scan_QR_Code-green.svg?logo=wechat&longCache=false&style=social&label=Group)](doc/images/WeChatQR.jpeg) 

- Discuss in [![Gitter](https://img.shields.io/badge/style-on_gitter-green.svg?logo=gitter&longCache=false&style=social&label=Chat)](https://gitter.im/fisco-bcos/Lobby) 

- Read news by [![](https://img.shields.io/twitter/url/http/shields.io.svg?style=social&label=Follow@FiscoBcos)](https://twitter.com/FiscoBcos)

- Mail us at [![](https://img.shields.io/twitter/url/http/shields.io.svg?logo=Gmail&style=social&label=service@fisco.com.cn)](mailto:service@fisco.com.cn)