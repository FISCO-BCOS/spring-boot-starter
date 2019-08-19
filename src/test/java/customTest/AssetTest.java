package customTest;

import org.fisco.bcos.Application;
import org.fisco.bcos.solidity.Asset;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.math.BigInteger;
/*
* 本单元测试，是模拟 资产合约部署，到调用的过程
*  需要按照执行步骤，每个测试，有些参数需要用到上一个测试打印数据
*
* */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class AssetTest {
    private Credentials credentials;
    private static BigInteger gasPrice = new BigInteger("1");
    private static BigInteger gasLimit = new BigInteger("300000000");
    @Autowired
    Web3j web3j;

    //这很重要，没有这个无法通过
    @Before
    public void setUp() throws Exception {
       /* credentials =
                GenCredential.create(
                        "b83261efa42895c38c6c2364ca878f43e77f3cddbc922bf57d0d48070f79feb6");
        if (credentials == null) {
            throw new Exception("create Credentials failed");
        }*/

         credentials = GenCredential.create();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void DoAsset() throws Exception {
        AssetRegisterAndQuery();
        //DeployAsset();

    }
    @Test
    //1、部署合约
    public void DeployAsset() throws Exception {
        // 部署合约
        Asset asset = Asset.deploy(web3j, credentials, new StaticGasProvider(gasPrice, gasLimit)).send();

        if (asset != null) {
            System.out.println("Asset address is: " + asset.getContractAddress());
        }

    }
    @Test
    //2、用户注册 资产查询 本测试需要用到部署合约时候的地址
    public void AssetRegisterAndQuery()throws Exception  {
        String contractAddress = "0xbd602573e62bcd6f73c7a93cf0223888e6105411";//需要根据部署地址修改
        // 加载合约地址
        Asset asset = Asset.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));

        if (asset != null) {
            System.out.println("Asset address is: " + asset.getContractAddress());
            // call set function
            System.out.println("1、注册用户，并注册资产--------------------------------------");
            String assetAccount1="0x608153babb8b00f11523f6b1b2b225ea9e7dfd8b";
            String assetAccount2="0x86f17b879ce121e5d00351a120de0bd39867bf4c";
            // register接口调用
            TransactionReceipt receipt1 = asset.register(assetAccount1, new BigInteger("121210000") ).send();
            TransactionReceipt receipt2 = asset.register(assetAccount2, new BigInteger("121121213") ).send();

            System.out.println("receipt1="+receipt1.toString());
            System.out.println("receipt2="+receipt2.toString());

            System.out.println("2、查询用户资产----------------------------------------------");
            // select接口调用
            Tuple2<BigInteger, BigInteger> result1 = asset.select("abc").send();
            Tuple2<BigInteger, BigInteger> result2 = asset.select("ddd").send();
            System.out.println("Tuple2<BigInteger, BigInteger> result1="+result1.toString());
            System.out.println("Tuple2<BigInteger, BigInteger> result2="+result2.toString());
            //assertTrue("Hello, World!".equals(result));
        }


    }

    // 3、资产交易
    @Test
    public  void AssetTransfer() throws  Exception{

        String contractAddress = "0xf9343346a8d80c3d2f2026bf72fff3aec48a4133";//本地址需要部署合约的地址
        // 加载合约地址
        Asset asset = Asset.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));

        String fromAssetAccount="0x608153babb8b00f11523f6b1b2b225ea9e7dfd8b";
        String  toAssetAccount="0x86f17b879ce121e5d00351a120de0bd39867bf4c";
      BigInteger amount = new BigInteger("121210000");
        if (asset != null) {
            // transfer接口
            TransactionReceipt receipt = asset.transfer( fromAssetAccount,toAssetAccount, amount).send();
            System.out.println("AssetTest.AssetTransfer receipt="+receipt.toString());
        }

    }
}