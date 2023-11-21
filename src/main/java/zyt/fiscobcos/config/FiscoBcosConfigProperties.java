package zyt.fiscobcos.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * @author zyt
 * @date 2023/10/09
 * 配置类,具体可以参见
 * https://fisco-bcos-documentation.readthedocs.io/zh_CN/latest/docs/sdk/java_sdk/configuration.html
 */
@Data
public class FiscoBcosConfigProperties implements Serializable {
    private List<String> peers;
    private String certPath = "conf";
    private String keyStoreDir = "account";
    private String accountAddress = "";
    private String accountFileFormat = "pem";
    private String password = "";
    private String accountFilePath = "";
    private String channelProcessorThreadSize = "16";
    private String receiptProcessorThreadSize = "16";
    private String maxBlockingQueueSize = "102400";
    private String basePackages;
    private boolean enabled = false;
    private String groupId ="group0";
}
