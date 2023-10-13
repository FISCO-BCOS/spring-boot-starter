package zyt.fiscobcos.client;

import zyt.fiscobcos.common.CommonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 这个类封装了一些通用的方法，如果想用这个类的方法，合约名字必须按照如下insert,queryById,updateById,removeById,
 * 在solidity中function名称必须是insert,queryById,updateById,removeById
 */
public class FiscoClient extends CommonClient  {

    public static final Logger logger = LoggerFactory.getLogger(FiscoClient.class.getName());

    public void insert(List<String> value,String contractName,Class clazz) throws Exception {

        Object contract =  getContractMap().get(contractName);
        Method method = clazz.getMethod("insert",List.class);
        Object obj = method.invoke(contract,value);
        logger.info("调用CRUDClient的insert方法");
        logger.info("结果：{}", obj);
    }


    public Object queryById(String id,String contractName,Class clazz) throws Exception {

        Object contract =  getContractMap().get(contractName);
        Method method = clazz.getMethod("selectById",String.class);
        Object obj = method.invoke(contract,id);
        logger.info("调用CRUDClient的query方法");
        logger.info("结果：{}", obj);
        return obj;

    }

    public  void updateById(List<String> value,String contractName,Class clazz) throws Exception{
        Object contract =  getContractMap().get(contractName);
        Method method = clazz.getMethod("updateById",List.class);
        Object obj = method.invoke(contract,value);
        logger.info("调用CRUDClient的edit方法");
        logger.info("结果：{}", obj);
    }

    public void removeById(String id,String contractName,Class clazz) throws Exception {

        Object contract =  getContractMap().get(contractName);
        Method method = clazz.getMethod("removeById",String.class);
        Object obj = method.invoke(contract,id);
        logger.info("调用CRUDClient的remove方法");
        logger.info("结果：{}", obj);

    }

}
