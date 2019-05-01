package org.fisco.bcos.server;

import java.util.HashSet;
import java.util.Set;
import org.fisco.bcos.BaseTest;
import org.fisco.bcos.channel.client.Service;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class Channel2Server extends BaseTest {

    @Autowired Service service;

    @Test
    public void channel2ServerTest() throws Exception {

        String topic = "topic";
        Set<String> topics = new HashSet<>();
        topics.add(topic);
        service.setTopics(topics);

        PushCallback cb = new PushCallback();

        service.setPushCallback(cb);

        System.out.println("3s...");
        Thread.sleep(1000);
        System.out.println("2s...");
        Thread.sleep(1000);
        System.out.println("1s...");
        Thread.sleep(1000);

        System.out.println("start test");
        System.out.println("===================================================================");
    }
}
