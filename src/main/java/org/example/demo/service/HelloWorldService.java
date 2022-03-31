package org.example.demo.service;

import java.util.Arrays;
import javax.annotation.PostConstruct;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.demo.config.ContractConfig;
import org.example.demo.constants.ContractConstants;
import org.example.demo.model.bo.HelloWorldSetInputBO;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.v3.transaction.manager.TransactionProcessorFactory;
import org.fisco.bcos.sdk.v3.transaction.model.dto.CallResponse;
import org.fisco.bcos.sdk.v3.transaction.model.dto.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@Data
public class HelloWorldService {

    private String address;

    @Autowired private Client client;

    @Autowired private ContractConfig contractConfig;

    AssembleTransactionProcessor txProcessor;

    @PostConstruct
    public void init() throws Exception {
        this.txProcessor =
                TransactionProcessorFactory.createAssembleTransactionProcessor(
                        this.client, this.client.getCryptoSuite().getCryptoKeyPair());
        this.address = contractConfig.getHelloWorldAddress();
    }

    public TransactionResponse set(HelloWorldSetInputBO input) throws Exception {
        return this.txProcessor.sendTransactionAndGetResponse(
                this.address, ContractConstants.HelloWorldAbi, "set", input.toArgs());
    }

    public CallResponse get() throws Exception {
        return this.txProcessor.sendCall(
                this.client.getCryptoSuite().getCryptoKeyPair().getAddress(),
                this.address,
                ContractConstants.HelloWorldAbi,
                "get",
                Arrays.asList());
    }
}
