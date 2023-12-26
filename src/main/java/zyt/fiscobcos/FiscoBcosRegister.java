package zyt.fiscobcos;


import org.fisco.bcos.sdk.v3.BcosSDK;
import org.fisco.bcos.sdk.v3.codec.ContractCodec;
import org.fisco.bcos.sdk.v3.config.ConfigOption;
import org.fisco.bcos.sdk.v3.config.model.ConfigProperty;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import zyt.fiscobcos.client.FiscoClient;
import zyt.fiscobcos.config.FiscoBcosConfigProperties;
import zyt.fiscobcos.deploy.DeployFileContract;
import zyt.fiscobcos.deploy.DeployAnnotationContract;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zyt
 * @date 2023/10/09
 * FiscoBcos相关对象注册，具体见官网:https://fisco-bcos-documentation.readthedocs.io/zh_CN/latest/docs/sdk/java_sdk/index.html
 */
public class FiscoBcosRegister {

    /**
     * 配置文件注册
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "fisco")
    public FiscoBcosConfigProperties fiscoBcosConfigProperties() {
        return  new FiscoBcosConfigProperties();
    }

    /**
     * 配置fiscobcos的配置项
     * @return
     */
    @Bean
    @DependsOn("fiscoBcosConfigProperties")
    public ConfigProperty configProperty() {
        ConfigProperty configProperty = new ConfigProperty();
        FiscoBcosConfigProperties fiscoBcosConfigProperties = FManager.getFiscoBcosConfigProperties();
        Map<String,Object> map = new HashMap<>();
        map.put("certPath", fiscoBcosConfigProperties.getCertPath());
        configProperty.setCryptoMaterial(map);

        Map<String,Object> netWork = new HashMap<>();
        netWork.put("peers", fiscoBcosConfigProperties.getPeers());
        configProperty.setNetwork(netWork);

        Map<String,Object> Account = new HashMap<>();
        Account.put("keyStoreDir", fiscoBcosConfigProperties.getKeyStoreDir());
        Account.put("accountAddress", fiscoBcosConfigProperties.getAccountAddress());
        Account.put("accountFileFormat", fiscoBcosConfigProperties.getAccountFileFormat());
        Account.put("password", fiscoBcosConfigProperties.getPassword());
        Account.put("accountFilePath", fiscoBcosConfigProperties.getAccountFilePath());
        configProperty.setAccount(Account);

        Map<String,Object> ThreadPool = new HashMap<>();
        ThreadPool.put("channelProcessorThreadSize", fiscoBcosConfigProperties.getChannelProcessorThreadSize());
        ThreadPool.put("receiptProcessorThreadSize", fiscoBcosConfigProperties.getReceiptProcessorThreadSize());
        ThreadPool.put("maxBlockingQueueSize", fiscoBcosConfigProperties.getMaxBlockingQueueSize());
        configProperty.setThreadPool(ThreadPool);
        return configProperty;
    }

    @Bean
    @DependsOn("configProperty")
    public ConfigOption configOption(ConfigProperty configProperty) throws Exception {
        return new ConfigOption(configProperty);
    }

    @Bean
    @DependsOn("configOption")
    public BcosSDK bcosSDK(ConfigOption configOption)  {
        return new BcosSDK(configOption);
    }

    @Bean
    @DependsOn("bcosSDK")
    public FiscoClient fiscoClient() {
        return new FiscoClient();
    }

    @Bean
    @DependsOn("fiscoClient")
    public DeployFileContract deployFileContract() {
        return new DeployFileContract();
    }

    @Bean
    @DependsOn("fiscoClient")
    public DeployAnnotationContract packageContractScanner() {
        return new DeployAnnotationContract();
    }


}
