package org.example.demo.config;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.sdk.v3.BcosSDK;
import org.fisco.bcos.sdk.v3.config.ConfigOption;
import org.fisco.bcos.sdk.v3.config.exceptions.ConfigException;
import org.fisco.bcos.sdk.v3.config.model.ConfigProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class BcosSdkBeanConfig {
    @Autowired private BcosConfig bcosConfig;

    @Bean
    public BcosSDK bcosSDK() throws ConfigException {
        ConfigProperty property = new ConfigProperty();
        configNetwork(property);
        configCryptoMaterial(property);

        ConfigOption configOption = new ConfigOption(property);
        return new BcosSDK(configOption);
    }

    public void configNetwork(ConfigProperty configProperty) {
        Map peers = bcosConfig.getNetwork();
        configProperty.setNetwork(peers);
    }

    public void configCryptoMaterial(ConfigProperty configProperty) {
        Map<String, Object> cryptoMaterials = bcosConfig.getCryptoMaterial();
        configProperty.setCryptoMaterial(cryptoMaterials);
    }
}
