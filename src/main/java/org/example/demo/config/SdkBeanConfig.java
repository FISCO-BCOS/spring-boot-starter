package org.example.demo.config;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.example.demo.constants.ContractConstants;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.config.ConfigOption;
import org.fisco.bcos.sdk.config.model.ConfigProperty;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.transaction.manager.TransactionProcessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class SdkBeanConfig {

    @Autowired private SystemConfig systemConfig;

    @Autowired private BcosConfig bcosConfig;

    @Autowired private ContractConfig contractConfig;

    @Bean
    public Client client() throws Exception {
        ConfigProperty property = new ConfigProperty();
        configNetwork(property);
        configCryptoMaterial(property);

        ConfigOption configOption = new ConfigOption(property);
        String groupIdStr = String.valueOf(systemConfig.getGroupId());
	Client client = new BcosSDK(configOption).getClient(groupIdStr);

        BigInteger blockNumber = client.getBlockNumber().getBlockNumber();
        if (log.isInfoEnabled()) {
            log.info("Chain connect successful. Current block number {}", blockNumber);
        }

        configCryptoKeyPair(client);
        if (log.isInfoEnabled()) {
            log.info(
                    "Your account is Gm:{}, address:{}",
                    client.getCryptoSuite().cryptoTypeConfig == 1,
                    client.getCryptoSuite().getCryptoKeyPair().getAddress());
        }

        if (contractConfig.getHelloWorldAddress() == null
                || contractConfig.getHelloWorldAddress().isEmpty()) {
            contractConfig.setHelloWorldAddress(deploy(client));
        }
        return client;
    }

    private String deploy(Client client) throws Exception {
        AssembleTransactionProcessor txProcessor =
                TransactionProcessorFactory.createAssembleTransactionProcessor(
                        client, client.getCryptoSuite().getCryptoKeyPair());
        String abi = ContractConstants.HelloWorldAbi;
        String bin = ContractConstants.HelloWorldBinary;
        TransactionReceipt receipt =
                txProcessor.deployAndGetResponse(abi, bin, Arrays.asList()).getTransactionReceipt();
        if (receipt.isStatusOK()) {
            return receipt.getContractAddress();
        } else {
            throw new RuntimeException("Deploy failed");
        }
    }

    public void configNetwork(ConfigProperty configProperty) {
        Map peers = bcosConfig.getNetwork();
        configProperty.setNetwork(peers);
    }

    public void configCryptoMaterial(ConfigProperty configProperty) {
        Map<String, Object> cryptoMaterials = bcosConfig.getCryptoMaterial();
        configProperty.setCryptoMaterial(cryptoMaterials);
    }

    public void configCryptoKeyPair(Client client) {
        if (systemConfig.getHexPrivateKey() == null || systemConfig.getHexPrivateKey().isEmpty()) {
            return;
        }
        if (systemConfig.getHexPrivateKey().startsWith("0x")
                || systemConfig.getHexPrivateKey().startsWith("0X")) {
            systemConfig.setHexPrivateKey(systemConfig.getHexPrivateKey().substring(2));
        }
        client.getCryptoSuite()
                .setCryptoKeyPair(
                        client.getCryptoSuite().createKeyPair(systemConfig.getHexPrivateKey()));
    }
}
