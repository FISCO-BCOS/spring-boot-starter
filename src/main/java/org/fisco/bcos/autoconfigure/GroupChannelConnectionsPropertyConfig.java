package org.fisco.bcos.autoconfigure;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.fisco.bcos.channel.handler.ChannelConnections;
import org.fisco.bcos.channel.handler.GroupChannelConnectionsConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "group-channel-connections-config")
public class GroupChannelConnectionsPropertyConfig {

    List<ChannelConnections> allChannelConnections = new ArrayList<>();

    @Bean
    public GroupChannelConnectionsConfig getGroupChannelConnections() {
        GroupChannelConnectionsConfig groupChannelConnectionsConfig =
                new GroupChannelConnectionsConfig();
        groupChannelConnectionsConfig.setAllChannelConnections(allChannelConnections);
        return groupChannelConnectionsConfig;
    }
}
