package org.fisco.bcos;


import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.fisco.bcos.temp.HelloWorld;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ContractTest {

    private static BigInteger gasPrice = new BigInteger("300000000");
    private static BigInteger gasLimit = new BigInteger("300000000");
    private Credentials credentials;

    @Autowired
    Web3j web3j;

    @Before
    public void setUp() throws Exception {
        credentials = GenCredential.create("b83261efa42895c38c6c2364ca878f43e77f3cddbc922bf57d0d48070f79feb6");
        if (credentials == null) {
            throw new Exception("create Credentials failed");
        }
    }

    @After
    public void tearDown() {

    }

    @Test
    public void deployAndCallHelloWorld() throws Exception {
        //deploy contract
        HelloWorld helloWorld = HelloWorld.deploy(web3j, credentials, new StaticGasProvider(gasPrice, gasLimit)).send();
        if (helloWorld != null) {
            System.out.println("HelloWorld address is: " + helloWorld.getContractAddress());
            //call set function
            helloWorld.set("Hello, World!").send();
            //call get function
            String result = helloWorld.get().send();
            System.out.println(result);
            assertTrue( "Hello, World!".equals(result));
        }
    }
}
