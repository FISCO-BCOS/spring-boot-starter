package org.fisco.bcos;

import static org.junit.Assert.assertTrue;

import org.fisco.bcos.sdk.contract.precompiled.sysconfig.SystemConfigService;
import org.junit.Test;

public class PrecompiledServiceApiTest extends BaseTest {
    @Test
    public void testSystemConfigService() throws Exception {
        init();
        SystemConfigService systemConfigService =
                new SystemConfigService(client, client.getCryptoSuite().createKeyPair());
        systemConfigService.setValueByKey("tx_count_limit", "2000");
        String value = client.getSystemConfigByKey("tx_count_limit").getSystemConfig();
        System.out.println(value);
        assertTrue("2000".equals(value));
    }
}
