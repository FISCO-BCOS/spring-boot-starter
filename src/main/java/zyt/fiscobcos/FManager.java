package zyt.fiscobcos;


import org.fisco.bcos.sdk.v3.BcosSDK;
import org.fisco.bcos.sdk.v3.config.model.ConfigProperty;
import zyt.fiscobcos.client.FiscoClient;
import zyt.fiscobcos.config.FiscoBcosConfigProperties;


/**
 * @author zyt
 * @date 2023/10/09
 * 全局管理类，可以拿到fiscoBcos相关组件
 */
public class FManager {

    public volatile  static FiscoBcosConfigProperties fiscoBcosConfigProperties;
    public static void setFiscoBcosConfigConfigProperty(FiscoBcosConfigProperties fiscoBcosConfigProperties) {
        FManager.fiscoBcosConfigProperties = fiscoBcosConfigProperties;
    }

    public static FiscoBcosConfigProperties getFiscoBcosConfigProperties() {
       return fiscoBcosConfigProperties;
    }

    public volatile  static ConfigProperty configProperty;
    public static void setConfigProperty(ConfigProperty configProperty) {

        FManager.configProperty = configProperty;
    }

    public static ConfigProperty getConfigProperty() {
        return configProperty;
    }

    public volatile static FiscoClient fiscoClient;
    public static void setFiscoClient(FiscoClient fiscoClient) {
        FManager.fiscoClient = fiscoClient;
    }

    public static FiscoClient getFiscoClient() {
        return fiscoClient;
    }

    public volatile static BcosSDK bcosSDK;
    public static void setBcosSDK(BcosSDK bcosSDK) {
        FManager.bcosSDK = bcosSDK;
    }

    public static BcosSDK getBcosSDK() {
        return  bcosSDK;
    }

}
