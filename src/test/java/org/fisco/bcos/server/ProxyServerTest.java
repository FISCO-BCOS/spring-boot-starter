package org.fisco.bcos.server;

import org.fisco.bcos.Application;
import org.fisco.bcos.channel.handler.ChannelConnections;
import org.fisco.bcos.channel.proxy.Server;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

import javax.net.ssl.SSLException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ProxyServerTest {
	@Ignore
	@Test
	public  void proxyServerTest() throws SSLException {
	 Server server =	new Server();
	 ChannelConnections channelConnections = new ChannelConnections();
	 List<String> ilist = new ArrayList<>();
	 ilist.add("10.107.105.138:30901");
	 channelConnections.setConnectionsStr(ilist);
	 server.setRemoteConnections(channelConnections);

	 server.setThreadPool(new ThreadPoolTaskExecutor());
	 server.setBindPort(8830);
	 server.run();
	}
}
