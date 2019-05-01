package org.fisco.bcos.server;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.fisco.bcos.BaseTest;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.channel.dto.ChannelRequest;
import org.fisco.bcos.channel.dto.ChannelResponse;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class Channel2Client extends BaseTest {

    @Autowired Service service;

    @Test
    public void channel2ClientTest() throws Exception {

        String topic = "topic";
        Integer count = Integer.parseInt("2");

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        System.out.println("3s ...");
        Thread.sleep(1000);
        System.out.println("2s ...");
        Thread.sleep(1000);
        System.out.println("1s ...");
        Thread.sleep(1000);

        System.out.println("start test");
        System.out.println("===================================================================");

        for (Integer i = 0; i < count; ++i) {
            Thread.sleep(2000);
            ChannelRequest request = new ChannelRequest();
            request.setToTopic(topic);
            request.setMessageID(service.newSeq());
            request.setTimeout(5000);

            request.setContent("request seq:" + request.getMessageID());

            System.out.println(
                    df.format(LocalDateTime.now())
                            + " request seq:"
                            + String.valueOf(request.getMessageID())
                            + ", Content:"
                            + request.getContent());

            ChannelResponse response = service.sendChannelMessage2(request);

            System.out.println(
                    df.format(LocalDateTime.now())
                            + "response seq:"
                            + String.valueOf(response.getMessageID())
                            + ", ErrorCode:"
                            + response.getErrorCode()
                            + ", Content:"
                            + response.getContent());
        }
    }
}
