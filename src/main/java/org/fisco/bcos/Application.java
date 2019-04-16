package org.fisco.bcos;

import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableConfigurationProperties
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Web3j getWeb3j(Service service) throws Exception {
        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
        service.run();
        channelEthereumService.setChannelService(service);
        channelEthereumService.setTimeout(30000);
        return Web3j.build(channelEthereumService,service.getGroupId());
    }
}
