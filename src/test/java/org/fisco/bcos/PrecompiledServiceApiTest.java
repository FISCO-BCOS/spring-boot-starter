package org.fisco.bcos;

import static org.junit.Assert.assertTrue;

import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.precompile.config.SystemConfigService;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PrecompiledServiceApiTest extends BaseTest {

    @Autowired Web3j web3j;

    @Autowired private Credentials credentials;

    @Test
    public void testSystemConfigService() throws Exception {
        SystemConfigService systemConfigSerivce = new SystemConfigService(web3j, credentials);
        systemConfigSerivce.setValueByKey("tx_count_limit", "2000");
        String value = web3j.getSystemConfigByKey("tx_count_limit").send().getSystemConfigByKey();
        System.out.println(value);
        assertTrue("2000".equals(value));
    }
}
