package zyt.fiscobcos.common;


import org.fisco.bcos.sdk.v3.BcosSDK;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.client.protocol.response.*;
import org.fisco.bcos.sdk.v3.codec.ContractCodec;
import org.fisco.bcos.sdk.v3.codec.ContractCodecException;
import org.fisco.bcos.sdk.v3.codec.datatypes.Type;
import org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String;
import org.fisco.bcos.sdk.v3.codec.wrapper.ABIObject;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zyt.fiscobcos.FManager;
import zyt.fiscobcos.contract.MyContract;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: zyt
 * @Description:此类提供一个发布合约的方法，并提供了基本实现方法，可以继承此类实现自己的方法
 * @Date: Created in 16:29 2021/1/20
 */
public abstract class CommonClient {

    public static final Logger logger = LoggerFactory.getLogger(CommonClient.class.getName());

    public CommonClient() {
    }

    private Map<String, Object> contractMap = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public <T> void deploy(String contractName, Class<T> clazz, BcosSDK sdk) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String groupId = FManager.getFiscoBcosConfigProperties().getGroupId();
        // 为群组初始化client
        Client client = sdk.getClient(groupId);
        // 向群组部署合约
        CryptoKeyPair cryptoKeyPair = client.getCryptoSuite().getCryptoKeyPair();
        Method method = clazz.getMethod("deploy", Client.class, CryptoKeyPair.class);
        T result = (T) method.invoke(null, client, cryptoKeyPair);
        logger.info("执行CommonClient的deploy方法");
        logger.info("部署合约 {} 成功:{}", contractName, result);
        contractMap.put(contractName, result);
    }

    public List<Utf8String> decodeMethodAndGetOutputAbiObjectParser(boolean isWasm, String output, String methodName) throws ContractCodecException {
        List<Utf8String> result = new ArrayList<>();
        CryptoSuite cryptoSuite = getCryptoSuite();
        ContractCodec contractCodec = new ContractCodec(cryptoSuite,isWasm);
        ABIObject abiObject = contractCodec.decodeMethodAndGetOutputAbiObject(MyContract.getABI(), methodName,output);
        List<ABIObject> abiObjects = abiObject.getStructFields();
        abiObjects.forEach(item->{
            if (item!=null) {
                item.getListValues().forEach(obj-> result.add(obj.getStringValue()));
            }
        });
        return result;
    }

    // 函数名 + Type格式的返回列表
    @Deprecated
    public  List<Type> decodeMethodParser(boolean isWasm, String output, String methodName) throws ContractCodecException {
        CryptoSuite cryptoSuite = getCryptoSuite();
        ContractCodec contractCodec = new ContractCodec(cryptoSuite,isWasm);
        List<Type> result = contractCodec.decodeMethod(MyContract.getABI(), methodName, output);
        return result;
    }

    public CryptoSuite getCryptoSuite() {
        BcosSDK bcosSDK = FManager.getBcosSDK();
        String groupId = FManager.getFiscoBcosConfigProperties().getGroupId();
        Client client = bcosSDK.getClient(groupId);
        CryptoSuite cryptoSuite = client.getCryptoSuite();
        return cryptoSuite;
    }

    //获取Client对象对应的群组最新块高
    public BlockNumber getBlockNumber() {
        String groupId = FManager.getFiscoBcosConfigProperties().getGroupId();
        Client client = getClient(groupId);
        return client.getBlockNumber();
    }

    //获取Client对应群组的交易统计信息，包括上链的交易数、上链失败的交易数目。
    public TotalTransactionCount getTotalTransactionCount() {
        String groupId = FManager.getFiscoBcosConfigProperties().getGroupId();
        Client client = getClient(groupId);
        TotalTransactionCount totalTransactionCount = client.getTotalTransactionCount();
        return totalTransactionCount;
    }

    //根据区块高度获取区块哈希。
    public BlockHash getBlockHashByNumber() {
        String groupId = FManager.getFiscoBcosConfigProperties().getGroupId();
        BlockNumber blockNumber = getBlockNumber();
        Client client = getClient(groupId);
        BlockHash blockHash = client.getBlockHashByNumber(blockNumber.getBlockNumber());
        return blockHash;
    }

    //返回根据交易哈希查询的交易回执信息
    public Object getTransactionReceipt(String hash) {
        String groupId = FManager.getFiscoBcosConfigProperties().getGroupId();
        Client client = getClient(groupId);
        BcosTransactionReceipt bcosTransactionReceipt = client.getTransactionReceipt(hash,true);
        return bcosTransactionReceipt;
    }

    private Client getClient(String groupId ) {
        BcosSDK sdk = FManager.getBcosSDK();
        Client client = sdk.getClient(String.valueOf(groupId));
        return client;
    }

    public Object getContractMap(String contractName) {
        if (getContractMap().containsKey(contractName)) {
            return getContractMap().get(contractName);
        }
        return null;
    }

    public Map<String, Object> getContractMap() {
        return contractMap;
    }

    public void setContractMap(Map<String, Object> contractMap) {
        this.contractMap = contractMap;
    }
}


