package org.example.demo;

import lombok.Data;
import lombok.ToString;
import org.fisco.bcos.sdk.config.model.AmopTopic;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Data
@Configuration
@ConfigurationProperties
public class BcosConfig {
    private Map<String, Object> cryptoMaterial;
    public Map<String, List<String>> network;
    public List<AmopTopic> amop;
    public Map<String, Object> account;
    public Map<String, Object> threadPool;
}
