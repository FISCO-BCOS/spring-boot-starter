package org.fisco.bcos;

import static org.junit.Assert.assertTrue;

import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.fisco.bcos.web3j.precompile.config.SystemConfigSerivce;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class PrecompiledServiceApiTest {

    private Credentials credentials;

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

    @Autowired
    Web3j web3j;

    @Test
    public void testSystemConfigService() throws Exception {
        SystemConfigSerivce systemConfigSerivce = new SystemConfigSerivce(web3j, credentials);
        systemConfigSerivce.setValueByKey("tx_count_limit", "2000");
        String value = web3j.getSystemConfigByKey("tx_count_limit").send().getSystemConfigByKey();
        System.out.println(value);
        assertTrue("2000".equals(value));
    }

}