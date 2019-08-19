package customTest;

import org.fisco.bcos.channel.client.PEMManager;
import org.fisco.bcos.temp.TableTemp;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.ECKeyPair;
import org.fisco.bcos.web3j.precompile.crud.CRUDService;
import org.fisco.bcos.web3j.precompile.crud.Condition;
import org.fisco.bcos.web3j.precompile.crud.Entry;
import org.fisco.bcos.web3j.precompile.crud.Table;
import org.fisco.bcos.web3j.precompile.permission.PermissionService;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.junit.Assert;
import org.junit.Test;

/*
* 权限操作测试
*
* */
public class PermissionTest extends TestBase {

    @Test
    // 授权新创建用户的权限，测试它部署合约的权限，正常不授权无法部署
    public void PermissionDeploy() throws Exception {

        //1、初始化创建一个用户，没有在底层注册过的
        String tempAddress="0x80e3c3f1f1140fbc550fbfdaa318073af373141d";
        String tempPriKey="9265efb6b860ef244f4fbe3dd445f7829e86d11226573190c026323f77ebcd22";
        String tempPublicKey="1f9c09a4df7e336961c06e1f2373fb2ef6a1b642c444652a7a5a84b04bdc668431bbddab4a1665cb382d6a94aee9ff18bf88210bae7a2388315b2bc5253bcaa";


        //pem  链管理员进行操作，对新用户进行合约部署权限授权
        PEMManager pem = context.getBean(PEMManager.class);
        ECKeyPair pemKeyPair = pem.getECKeyPair();

        //1、链管理员操作 授权新用户的可以发布和部署合约
        credentials = Credentials.create(pemKeyPair);
         PermissionService permissionService = new PermissionService(web3j, credentials);
        String s = permissionService.grantDeployAndCreateManager(tempAddress);
        System.out.println("s = " + s);//这个是授权状态

        //2、使用新用户新合约部署测试
        Credentials userCredentials=Credentials.create(tempPriKey,tempPublicKey);
        // 部署合约
        TableTemp tableTemp = TableTemp.deploy(web3j, userCredentials, new StaticGasProvider(gasPrice, gasLimit)).send();

        if (tableTemp != null) {
            System.out.println("TableTemp address is: " + tableTemp.getContractAddress());
        }

    }
    @Test
    // 授权新创建用户的权限，用户表操作修改权限
    public void PermissionGrantUserTable() throws Exception {

        //1、初始化创建一个用户，没有在底层注册过的
        String tempAddress="0x80e3c3f1f1140fbc550fbfdaa318073af373141d";
        String tempPriKey="9265efb6b860ef244f4fbe3dd445f7829e86d11226573190c026323f77ebcd22";
        String tempPublicKey="1f9c09a4df7e336961c06e1f2373fb2ef6a1b642c444652a7a5a84b04bdc668431bbddab4a1665cb382d6a94aee9ff18bf88210bae7a2388315b2bc5253bcaa";


        //pem  链管理员进行操作，对新用户进行合约部署权限授权
        PEMManager pem = context.getBean(PEMManager.class);
        ECKeyPair pemKeyPair = pem.getECKeyPair();

        //1、链管理员操作 授权新用户的可以发布和部署合约
        credentials = Credentials.create(pemKeyPair);
        PermissionService permissionService = new PermissionService(web3j, credentials);
        String s = permissionService.grantUserTableManager("t_item2",tempAddress);
        System.out.println("s = " + s);//这个是授权状态

        //2、使用新用户进行表更新 操作
        Credentials userCredentials=Credentials.create(tempPriKey,tempPublicKey);
         //完成后，测试表操作中update操作看看是否可以成功
        CRUDService crudSerivce = new CRUDService(web3j, userCredentials);
        String tableName = "t_item2";
        String key = "name";
        Table table = new Table(tableName, key);
        int num = 5;
        table.setKey("q");//查询记录sql语句必须在where子句中提供表的主键字段值。
        Entry updateEntry = table.getEntry();
        updateEntry.put("item_id", "aaa");
        updateEntry.put("item_name", "121212");
        Condition updateCondition = table.getCondition();
        updateCondition.EQ("name", "q");
        updateCondition.EQ("item_id", "aaaaa");
        int updateResult = crudSerivce.update(table, updateEntry, updateCondition);
        Assert.assertEquals(updateResult, num);

        //结果测试成功，使用授权用户操作的表，可以进行更新操作；否则，不具备这个权限的用户，是无法操作的
    }

}
