package zyt.fiscobcos;


import org.fisco.bcos.sdk.v3.BcosSDK;
import org.springframework.beans.factory.annotation.Autowired;
import zyt.fiscobcos.client.FiscoClient;
import zyt.fiscobcos.config.FiscoBcosConfigProperties;

/**
 * @author zyt
 * @date 2023/10/09
 * FiscoBcos注入类
 */
public class FiscoBcosInject {

    /**
     * 配置文件
     * @param fiscoBcosConfigProperties
     */
    public FiscoBcosInject(
            @Autowired(required = false) FiscoBcosConfigProperties fiscoBcosConfigProperties
    ) {
        FManager.setFiscoBcosConfigConfigProperty(fiscoBcosConfigProperties);
    }

    /**
     * 客户端注入
     * @param fiscoClient
     */
    @Autowired(required = false)
    public void setFiscoClient(FiscoClient fiscoClient) {
        FManager.setFiscoClient(fiscoClient);
    }

    /**
     * sdk注入
     * @param bcosSDK
     */
    @Autowired(required = false)
    public void setBcosSDK(BcosSDK bcosSDK) {
        FManager.setBcosSDK(bcosSDK);
    }

}
