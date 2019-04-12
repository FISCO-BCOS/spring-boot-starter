package org.fisco.bcos;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigInteger;

import org.fisco.bcos.web3j.protocol.Web3j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Slf4j
public class Web3jApiTest {

    @Autowired
    Web3j web3j;

    @Test
    public void getBlockNumber() throws IOException {
        BigInteger blockNumber = web3j.getBlockNumber().send().getBlockNumber();
        log.info("blockNumber is {}", blockNumber);
        assertTrue(blockNumber.compareTo(new BigInteger("0")) >= 0);
    }

}