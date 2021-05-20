package org.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.example.demo.BcosConfig;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.config.ConfigOption;
import org.fisco.bcos.sdk.config.model.ConfigProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigInteger;
import java.util.Map;

@Configuration
@Slf4j
public class SdkBeanConfig {

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private BcosConfig bcosConfig;

    @Bean
    public Client client() throws Exception {
        ConfigProperty property = new ConfigProperty();
        configNetwork(property);
        configCryptoMaterial(property);

        ConfigOption configOption = new ConfigOption(property);
        Client client = new BcosSDK(configOption).getClient(systemConfig.getGroupId());

        BigInteger blockNumber = client.getBlockNumber().getBlockNumber();
        log.info("Chain connect successful. Current block number {}", blockNumber);

        configCryptoKeyPair(client);
        log.info("is Gm:{}, address:{}", client.getCryptoSuite().cryptoTypeConfig == 1, client.getCryptoSuite().getCryptoKeyPair().getAddress());
        return client;
    }

    public void configNetwork(ConfigProperty configProperty){
        Map peers = bcosConfig.getNetwork();
        configProperty.setNetwork(peers);
    }

    public void configCryptoMaterial(ConfigProperty configProperty){
        Map<String, Object> cryptoMaterials = bcosConfig.getCryptoMaterial();
        configProperty.setCryptoMaterial(cryptoMaterials);
    }

    public void configCryptoKeyPair(Client client){
        if(systemConfig.getHexPrivateKey() == null || systemConfig.getHexPrivateKey().isEmpty()){
            return;
        }
        if(systemConfig.getHexPrivateKey().startsWith("0x") || systemConfig.getHexPrivateKey().startsWith("0X")){
            systemConfig.setHexPrivateKey(systemConfig.getHexPrivateKey().substring(2));
        }
        client.getCryptoSuite().setCryptoKeyPair(client.getCryptoSuite().createKeyPair(systemConfig.getHexPrivateKey()));
    }
}
