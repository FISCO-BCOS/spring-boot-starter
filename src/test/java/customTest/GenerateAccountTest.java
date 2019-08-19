package customTest;

import org.fisco.bcos.Application;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.EncryptType;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class GenerateAccountTest {

    /*
    * 生产用户测试，生成4对账户，同时生成公私钥
    *
    * */
    @Test
    public void GenerateAccount() throws Exception {

        //创建普通账户
        EncryptType.encryptType = 0;
        //创建国密账户，向国密区块链节点发送交易需要使用国密账户
        // EncryptType.encryptType = 1;

        for (int i=1;i<4;i++){

            Credentials credentials = GenCredential.create();
            //账户地址
            String address = credentials.getAddress();
            //账户私钥
            String privateKey = credentials.getEcKeyPair().getPrivateKey().toString(16);
            //账户公钥
            String publicKey = credentials.getEcKeyPair().getPublicKey().toString(16);
            System.out.println(i+"、GenerateAccount address:"+address);
            System.out.println(i+"、GenerateAccount privateKey:"+privateKey);
            System.out.println(i+"、GenerateAccount publicKey:"+publicKey);
        }

    }

}
