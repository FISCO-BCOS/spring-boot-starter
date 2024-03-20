package org.example.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigInteger;
import java.util.Optional;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.client.protocol.model.JsonTransactionResponse;
import org.fisco.bcos.sdk.v3.client.protocol.response.BlockNumber;
import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.fisco.bcos.sdk.v3.utils.ObjectMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("chain")
public class BlockChainController {
    private final ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
    @Autowired private Client client;

    @GetMapping("blockNumber")
    public String blockNumber() {
        return client.getBlockNumber().getBlockNumber().toString();
    }

    @GetMapping("block")
    public String getBlock(@RequestParam("number") BigInteger number)
            throws JsonProcessingException {
        return objectMapper.writeValueAsString(
                client.getBlockByNumber(number, false, false).getBlock());
    }

    @GetMapping("latestBlock")
    public String getLatestBlock() throws JsonProcessingException {
        BlockNumber blockNumber = client.getBlockNumber();
        return objectMapper.writeValueAsString(
                client.getBlockByNumber(blockNumber.getBlockNumber(), false, false).getBlock());
    }

    @GetMapping("transaction")
    public String getTransaction(@RequestParam("hash") String hash) throws JsonProcessingException {
        Optional<JsonTransactionResponse> transaction =
                client.getTransaction(hash, true).getTransaction();
        if (transaction.isPresent()) {
            return objectMapper.writeValueAsString(transaction.get());
        } else {
            return "null";
        }
    }

    @GetMapping("receipt")
    public String getReceipt(@RequestParam("hash") String hash) throws JsonProcessingException {
        TransactionReceipt transactionReceipt =
                client.getTransactionReceipt(hash, true).getTransactionReceipt();
        return objectMapper.writeValueAsString(transactionReceipt);
    }
}
