package customTest;

import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.fisco.bcos.channel.client.P12Manager;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.ECKeyPair;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigInteger;
import java.util.Arrays;

public class TestBase {
  public static ApplicationContext context = null;
  public static Credentials credentials;

  protected static Web3j web3j;
  protected static BigInteger gasPrice = new BigInteger("30000000");
  protected static BigInteger gasLimit = new BigInteger("30000000");
  protected static String address;
  protected static BigInteger blockNumber;
  protected static String blockHash;
  protected static String txHash;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {

  	context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

    Service service = context.getBean(Service.class);
    service.run();

    ChannelEthereumService channelEthereumService = new ChannelEthereumService();
    channelEthereumService.setChannelService(service);

    web3j = Web3j.build(channelEthereumService, service.getGroupId());

    ApplicationContext context =
            new ClassPathXmlApplicationContext(
                    "classpath:applicationContext-keystore-sample.xml");
    // test p12
    P12Manager p12 = context.getBean(P12Manager.class);
    ECKeyPair p12KeyPair = p12.getECKeyPair();

    System.out.println("p12KeyPair.getPrivateKey() = " + p12KeyPair.getPrivateKey().toString(16));
    System.out.println("p12KeyPair.getPublicKey() = " + p12KeyPair.getPublicKey().toString(16));


    ECPublicKey publicKey = (ECPublicKey) p12.getPublicKey();
    byte[] publicKeyBytes = publicKey.getQ().getEncoded(false);
    BigInteger publicKeyValue =
            new BigInteger(1, Arrays.copyOfRange(publicKeyBytes, 1, publicKeyBytes.length));

    System.out.println("publicKeyValue = " + publicKeyValue.toString(16));

    credentials = Credentials.create(p12KeyPair);

    System.out.println("credentials  getAddress= " + credentials.getAddress());
  }

  @AfterClass
  public static void setUpAfterClass() throws Exception {
    ((ClassPathXmlApplicationContext) context).destroy();
  }
}
