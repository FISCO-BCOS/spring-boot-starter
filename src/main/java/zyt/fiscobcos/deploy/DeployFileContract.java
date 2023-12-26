package zyt.fiscobcos.deploy;

import org.fisco.bcos.sdk.v3.BcosSDK;
import org.springframework.boot.CommandLineRunner;
import zyt.fiscobcos.FManager;
import zyt.fiscobcos.annotion.EnableDeploy;
import zyt.fiscobcos.client.FiscoClient;
import zyt.fiscobcos.utils.PackageScannerUtil;
import zyt.fiscobcos.config.FiscoBcosConfigProperties;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * 根据配置文件部署合约
 *  @author  zyt
 *  @date 2023/09/28
 */
public class DeployFileContract implements CommandLineRunner {

    @Override
    public void run(String... args)  {
        FiscoClient fiscoClient =  FManager.getFiscoClient();
        BcosSDK bcosSDK = FManager.getBcosSDK();
        FiscoBcosConfigProperties properties =  FManager.getFiscoBcosConfigProperties();
        if (properties.isEnabled()) {
            String basePackage = properties.getBasePackages();
            String[] basePackages = basePackage.split(",");
            List<String> basePackageList = Arrays.asList(basePackages);
            basePackageList.forEach(pack->{
                Set<Class<?>> classes = PackageScannerUtil.scanWithPackage(pack);
                // 处理扫描到的类
                for (Class<?> clazz : classes) {
                    if (clazz.isAnnotationPresent(EnableDeploy.class)) {
                        try {
                            fiscoClient.deploy(clazz.getSimpleName(),clazz,bcosSDK);
                        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

}
