package customTest;

import org.fisco.bcos.Application;
import org.fisco.bcos.web3j.precompile.crud.CRUDService;
import org.fisco.bcos.web3j.precompile.crud.Condition;
import org.fisco.bcos.web3j.precompile.crud.Entry;
import org.fisco.bcos.web3j.precompile.crud.Table;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;


/*本测试是 针对CRUDService 的测试
* 1、继承 TestBase，配置文件在main的java的resources中applicationContext.xml
* 2、如果底层以及设置了权限，需要相对应有权限的人员私钥等操作，否则会有失败报错等问题
*
* */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class CRUDServiceTest extends TestBase {
    //这里如果设置了权限，那么必须权限用户才可以使用，请看TestBase 中的改造，credentials
    private CRUDService crudSerivce = new CRUDService(web3j, credentials);

    //1、创建表操作
    @SuppressWarnings("unchecked")
    @Test
    public void CreateTest() throws Exception {

        String tableName = "t_item2";
        String key = "name";
        String valueFields  = "item_id, item_name,item_address,item_count";
        Table table = new Table(tableName, key, valueFields);
        // create table
        int resultCreate = crudSerivce.createTable(table);
        Assert.assertEquals(resultCreate, 0);
    }

    //2、插入表
    @SuppressWarnings("unchecked")
    @Test
    public  void Insert()throws Exception{

        String tableName = "t_item2";
        String key = "name";

        Table table = new Table(tableName, key);
        int insertResult = 0;
        int num = 5;
        for(int i = 1; i <= num; i++)
        {
            Entry insertEntry = table.getEntry();
            insertEntry.put("item_id", "q");
            insertEntry.put("item_name", "q"+i);
            insertEntry.put("item_address", "q"+i);
            insertEntry.put( "item_count",BigInteger.valueOf(i).toString());
            table.setKey("q");//在w供表的主键字段值。
            insertResult += crudSerivce.insert(table, insertEntry);
        }
        Assert.assertEquals(insertResult, num);

    }

    //3、查询表
    @SuppressWarnings("unchecked")
    @Test
    public  void  Select()throws Exception{
        String tableName = "t_item2";
        String key = "name";
        Table table = new Table(tableName, key);
        // select records
        Condition condition1 = table.getCondition();
        condition1.EQ("name", "q");
        condition1.EQ("item_id", "q");
       // condition1.Limit(1);
        table.setKey("q");//查询记录sql语句必须在where子句中提供表的主键字段值。

        List<Map<String, String>> resultSelect1 = crudSerivce.select(table, condition1);
        Assert.assertEquals(resultSelect1.get(0).get("name"), "q");
        Assert.assertEquals(resultSelect1.get(0).get("item_id"), "q");
        Assert.assertEquals(resultSelect1.get(0).get("item_name"), "q1");
    }

    //更新表操作
    @SuppressWarnings("unchecked")
    @Test
    public void Update() throws Exception{
        String tableName = "t_item2";
        String key = "name";
        Table table = new Table(tableName, key);
        int num = 5;
        table.setKey("q");//查询记录sql语句必须在where子句中提供表的主键字段值。
        Entry updateEntry = table.getEntry();
        updateEntry.put("item_id", "abc");
        updateEntry.put("item_name", "121212");
        Condition updateCondition = table.getCondition();
        updateCondition.EQ("name", "q");
        updateCondition.EQ("item_id", "aaaaa");
        int updateResult = crudSerivce.update(table, updateEntry, updateCondition);
        Assert.assertEquals(updateResult, num);
    }
}
