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
Copy the `ca.crt`, `sdk.crt`, and `sdk.key` files in the node's directory `nodes/${ip}/sdk` to the project's `src/main/resources` directory.(Before FISCO BCOS 2.1, the certificate files are `ca.crt`, `node.crt` and `node.key`)

### Settings

The `application.yml` of the spring boot project is shown below, and the commented content is modified according to the blockchain node configuration.
  
```yml
encrypt-type: # 0:standard, 1:guomi
 encrypt-type: 0 
 
group-channel-connections-config:
  all-channel-connections:
  - group-id: 1  # group ID
    connections-str:
                    - 127.0.0.1:20200  # node listen_ip:channel_listen_port
                    - 127.0.0.1:20201
  - group-id: 2  
    connections-str:
                    - 127.0.0.1:20202  # node listen_ip:channel_listen_port
                    - 127.0.0.1:20203
 
channel-service:
  group-id: 1 # The specified group to which the SDK connects
  agency-name: fisco # agency name

accounts:
  pem-file: 0xcdcce60801c0a2e6bb534322c32ae528b9dec8d2.pem # PEM format account file
  p12-file: 0x98333491efac02f8ce109b0c499074d47e7779a6.p12 # PKCS12 format account file
  password: 123456 # PKCS12 format account password
```

A detail description of the SDK configuration for the project, please checkout [ here](https://fisco-bcos-documentation.readthedocs.io/en/latest/docs/sdk/sdk.html#sdk)。

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

The sample project provides test cases for developers to use. The test cases are mainly divided into tests for [Web3j API](https://fisco-bcos-documentation.readthedocs.io/en/latest/docs/sdk/sdk.html#web3j-api), [Precompiled Serveice API](https://fisco-bcos-documentation.readthedocs.io/en/latest/docs/sdk/sdk.html#precompiled-service-api), Solidity contract file to Java contract file, deployment and call contract.

### Web3j API Test

Provide `Web3jApiTest` class to test the Web3j API. The sample test is as follows:

```java
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

### Solidity contract file to Java contract file Test

Provide `SolidityFunctionWrapperGeneratorTest` class to test contract compilation. The sample test is as follows:

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

This test case converts all Solidity contract files (`HelloWorld` contract provided by default) in the `src/test/resources/contract` directory to the corresponding `abi` and `bin` files, and save them in the `src/test/resources/solidity` directory. Then convert the `abi` file and the corresponding `bin` file combination into a Java contract file, which is saved in the `src/test/java/org/fisco/bcos/temp` directory. The SDK will use the Java contract file for contract deployment and invocation.

### Deployment and Invocation Contract Test

Provide `ContractTest` class to test deploy and call contracts. The sample test is as follows:

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

## Code Contribution

- Your contributions are most welcome and appreciated. Please read the [contribution instructions ](CONTRIBUTING.md).
- If this project is useful to you, please star us on GitHub project page!

## Join Our Community

The FISCO BCOS community is one of the most active open-source blockchain communities in China. It provides long-term technical support for both institutional and individual developers and users of FISCO BCOS. Thousands of technical enthusiasts from numerous industry sectors have joined this community, studying and using FISCO BCOS platform. If you are also interested, you are most welcome to join us for more support and fun.

![](https://media.githubusercontent.com/media/FISCO-BCOS/LargeFiles/master/images/QR_image_en.png)

## Related Links

- For FISCO BCOS project, please check out [FISCO BCOS Documentation](https://fisco-bcos-documentation.readthedocs.io/en/latest/docs/introduction.html)。
- For Web3SDK project, please check out [Web3SDK Documentation](https://fisco-bcos-documentation.readthedocs.io/en/latest/docs/sdk/sdk.html)。
- For Spring Boot applications, please check out [Spring Boot](https://spring.io/guides/gs/spring-boot/)。
