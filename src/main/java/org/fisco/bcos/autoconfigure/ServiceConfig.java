package org.fisco.bcos.autoconfigure;

import lombok.Data;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.channel.handler.ChannelConnections;
import org.fisco.bcos.channel.handler.GroupChannelConnectionsConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

@Data
@Configuration
@ConfigurationProperties(prefix = "channelService")
public class ServiceConfig {

    private String orgID;
    private Integer connectSeconds= 30 ;
    private Integer connectSleepPerMillis = 1;
    private GroupChannelConnectionsConfig allChannelConnections;
    private static int groupId =1;

    @Bean
    public Service getService(GroupChannelConnectionsConfig groupChannelConnectionsConfig) {
    Service channelService= new Service();
    channelService.setConnectSeconds(connectSeconds);
    channelService.setOrgID(orgID);
    channelService.setConnectSleepPerMillis(connectSleepPerMillis);
    channelService.setGroupId(groupId);
   channelService.setAllChannelConnections(groupChannelConnectionsConfig);
    return channelService;
    }


    public String getOrgID() {
        return orgID;
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID;
    }

    public Integer getConnectSeconds() {
        return connectSeconds;
    }

    public void setConnectSeconds(Integer connectSeconds) {
        this.connectSeconds = connectSeconds;
    }

    public Integer getConnectSleepPerMillis() {
        return connectSleepPerMillis;
    }

    public void setConnectSleepPerMillis(Integer connectSleepPerMillis) {
        this.connectSleepPerMillis = connectSleepPerMillis;
    }

    public GroupChannelConnectionsConfig getAllChannelConnections() {
        return allChannelConnections;
    }

    public void setAllChannelConnections(GroupChannelConnectionsConfig allChannelConnections) {
        this.allChannelConnections = allChannelConnections;
    }

    public static int getGroupId() {
        return groupId;
    }

    public static void setGroupId(int groupId) {
        ServiceConfig.groupId = groupId;
    }
}
