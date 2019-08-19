
package customTest;

import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.fisco.bcos.Application;
import org.fisco.bcos.channel.client.P12Manager;
import org.fisco.bcos.temp.TableTemp;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.ECKeyPair;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple3;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

/*
* 合约模式下，表的CRUD测试操作
*
* */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)

//表创建
public class TableTempTest {
    private Credentials credentials;
    private static BigInteger gasPrice = new BigInteger("300000000");
    private static BigInteger gasLimit = new BigInteger("300000000");
    @Autowired
    Web3j web3j;
    protected String tempDirPath =  new File("src/main/resources/").getAbsolutePath();
    //这很重要，没有这个无法通过
    @Before
    public void setUp() throws Exception {
        ApplicationContext context =
                new ClassPathXmlApplicationContext(
                        "classpath:applicationContext-keystore-sample.xml");
        // test p12
        P12Manager p12 = context.getBean(P12Manager.class);
        ECKeyPair p12KeyPair = p12.getECKeyPair();

        System.out.println("p12KeyPair.getPrivateKey() = " + p12KeyPair.getPrivateKey().toString(16));
        System.out.println("p12KeyPair.getPublicKey() = " + p12KeyPair.getPublicKey().toString(16));


        ECPublicKey publicKey = (ECPublicKey) p12.getPublicKey();
        byte[] publicKeyBytes = publicKey.getQ().getEncoded(false);
        BigInteger publicKeyValue =
                new BigInteger(1, Arrays.copyOfRange(publicKeyBytes, 1, publicKeyBytes.length));

        System.out.println("publicKeyValue = " + publicKeyValue.toString(16));

        credentials = Credentials.create(p12KeyPair);

        System.out.println("credentials  getAddress= " + credentials.getAddress());
    }

    @After
    public void tearDown() {
    }

    @Test
    //1、部署合约
    public void DeployTable() throws Exception {
        // 部署合约
        TableTemp tableTemp = TableTemp.deploy(web3j, credentials, new StaticGasProvider(gasPrice, gasLimit)).send();

        if (tableTemp != null) {
            System.out.println("TableTemp address is: " + tableTemp.getContractAddress());
        }

    }

    //2、创建表操作
    @Test
    public void CreateTableTest()throws Exception  {
        String contractAddress = "0xb4245b7b6cc33f8f65d8bf37f084dec3e31ca573";
        // 加载合约地址
        TableTemp tableTemp = TableTemp.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
        TransactionReceipt receipt = tableTemp.create().send();
        System.out.println("AssetTest.AssetTransfer receipt="+receipt.toString());
    }

    //3.1、插入表操作　无返回值操作
    @Test
    public void InsertTableTest()throws Exception  {
        String contractAddress = "0x215ac9f7af5766ff45d80082091856b54fcf4308";//需要用到上述部署的合约地址
        // 加载合约地址
        TableTemp tableTemp = TableTemp.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));

        String name = "wq";
        int item_id = Integer.parseInt("2");
        String item_name ="aaa";
        String item_address ="ddd";
        tableTemp.insert(name, BigInteger.valueOf(item_id), item_name,item_address).send();
       /* TransactionReceipt txReceipt = insert.send();
        List<TableTemp.InsertResultEventResponse> insertResultEvents =
                tableTemp.getInsertResultEvents(txReceipt);
        if (insertResultEvents.size() > 0) {
            for (int i = 0; i < insertResultEvents.size(); i++) {
                TableTemp.InsertResultEventResponse insertResultEventResponse =
                        insertResultEvents.get(i);
                System.out.println("insertCount = " + insertResultEventResponse.count.intValue());
                System.out.println(
                        "insertCount = " + insertResultEventResponse.count.intValue());
            }
    }*/
    }

    //3.2、插入表操作
    @Test
    public void InsertTableByReturnTest()throws Exception  {
        String contractAddress = "0xb4245b7b6cc33f8f65d8bf37f084dec3e31ca573";//需要用到上述部署的合约地址
        // 加载合约地址
        TableTemp tableTemp = TableTemp.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));

        String name = "ak";
        int item_id = Integer.parseInt("1");
        String item_name ="tempUser";
        String item_address ="北京";


        TransactionReceipt send = tableTemp.insert(name, BigInteger.valueOf(item_id), item_name, item_address).send();
        System.out.println(" send= "+send.toString());
       /* TransactionReceipt txReceipt = insert.send();
        List<TableTemp.InsertResultEventResponse> insertResultEvents =
                tableTemp.getInsertResultEvents(txReceipt);
        if (insertResultEvents.size() > 0) {
            for (int i = 0; i < insertResultEvents.size(); i++) {
                TableTemp.InsertResultEventResponse insertResultEventResponse =
                        insertResultEvents.get(i);
                System.out.println("insertCount = " + insertResultEventResponse.count.intValue());
                System.out.println(
                        "insertCount = " + insertResultEventResponse.count.intValue());
            }
    }*/
    }

    //4.1删除表数据操作
    @Test
    public void DeleteTableTest()throws Exception  {
        String contractAddress = "0x215ac9f7af5766ff45d80082091856b54fcf4308";//需要用到上述部署的合约地址
        // 加载合约地址
        TableTemp tableTemp = TableTemp.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
        TransactionReceipt send=  tableTemp.remove("ak",BigInteger.valueOf(1)).send();

        System.out.println("send.toString() = " + send.toString());
        System.out.println("send.getContractAddress() = " + send.getContractAddress());
        System.out.println("send.getBlockHash() = " + send.getBlockHash());
        System.out.println("send.getBlockNumber() = " + send.getBlockNumber());

    }
    //4.2删除表数据操作
    @Test
    public void DeleteTableByReturnTest()throws Exception  {
        String contractAddress = "0xb4245b7b6cc33f8f65d8bf37f084dec3e31ca573";//需要用到上述部署的合约地址
        // 加载合约地址
        TableTemp tableTemp = TableTemp.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));

        int item_id = Integer.parseInt("1");
        String name="ak";
        RemoteCall<TransactionReceipt> remove =
                (RemoteCall<TransactionReceipt>) tableTemp.remove(name,BigInteger.valueOf(item_id));

        TransactionReceipt transactionReceipt = remove.send();
        List<TableTemp.RemoveResultEventResponse> removeResultEvents =
                tableTemp.getRemoveResultEvents(transactionReceipt);
        if (removeResultEvents.size() > 0) {
            TableTemp.RemoveResultEventResponse reomveResultEventResponse = removeResultEvents.get(0);
            System.out.println(
                    "removeCount = " + reomveResultEventResponse.count.intValue());
        } else {
            System.out.println("tableTemp table does not exist.");
        }


    }

    //5、表查询
    @Test
    public void SelectTableTest() throws  Exception{
        String contractAddress = "0xa94c07af700bf2e435a3051b0a98f2f75eca0298";//需要用到上述部署的合约地址
        // 加载合约地址
        TableTemp tableTemp = TableTemp.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
        Tuple3<List<byte[]>, List<BigInteger>, List<byte[]>> lists = tableTemp.select("ak").send();
        //这个只是返回合约执行的结果，不会返回本身数据库表数据
     //   System.out.println("send.toString() = " + send.toString());
        List<byte[]> value1 = lists.getValue1();
        List<BigInteger> value2 = lists.getValue2();
        List<byte[]> value3 = lists.getValue3();

        for (int i = 0; i < value1.size(); i++) {
            String name = new String(value1.get(i));

            System.out.println("name = " + name);
            int item_id = value2.get(i).intValue();

            System.out.println("item_id = " + item_id);
            String item_name = new String(value3.get(i));

            System.out.println("item_name = " + item_name);
        }

    }

    //6、表更新
    @Test
    public void UpateTableTest() throws  Exception{
        String contractAddress = "0xb940c1966a6ce94484f0fdabd3bb8cb38edc9dfd";//需要用到上述部署的合约地址
        // 加载合约地址
        TableTemp tableTemp = TableTemp.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
        TransactionReceipt ak=  tableTemp.update("ak",BigInteger.valueOf(1),"121","wew").send();
        System.out.println("ak.toString() = " + ak.toString());
    }




}
