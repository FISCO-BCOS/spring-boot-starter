package zyt.fiscobcos.deploy;

import io.github.classgraph.*;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.sdk.v3.BcosSDK;
import org.fisco.bcos.sdk.v3.contract.Contract;
import org.springframework.boot.CommandLineRunner;
import zyt.fiscobcos.FManager;
import zyt.fiscobcos.annotion.EnableScan;
import zyt.fiscobcos.client.FiscoClient;
import zyt.fiscobcos.utils.PackageScannerUtil;

import java.util.*;

/**
 * 根据注解部署合约
 * @author  zyt
 * @date 2023/09/28
 */
@Slf4j
public class DeployAnnotationContract implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        FiscoClient fiscoClient =  FManager.getFiscoClient();
        BcosSDK bcosSDK = FManager.getBcosSDK();
        ClassInfoList classInfoList = PackageScannerUtil.scanClassInfoWithAnnotation(EnableScan.class);
        if (classInfoList != null && classInfoList.size()>0) {
            ClassInfo classInfo = classInfoList.get(0);
            AnnotationInfo annotationInfo = classInfo.getAnnotationInfo(EnableScan.class);
            AnnotationParameterValueList annotationParameterValueList = annotationInfo.getParameterValues(true);
            AnnotationParameterValue annotationParameterValue = annotationParameterValueList.get("packages");
            String[] values = (String[]) annotationParameterValue.getValue();
            if (values != null && values.length>0) {
                List<String> packs = Arrays.asList(values);
                Set<Class<?>> classes = new HashSet<>();
                packs.forEach(item->{
                    log.info("包名为: "+item);
                    ScanResult clazzes = PackageScannerUtil.scanResultWithPackage(item);
                    for (ClassInfo info : clazzes.getAllClasses()) {
                        Class<?> Obj = info.loadClass();
                        classes.add(Obj);
                    }
                });
                for (Class<?> clazz : classes) {
                    if (Contract.class.isAssignableFrom(clazz)) {
                        fiscoClient.deploy(clazz.getSimpleName(),clazz,bcosSDK);
                    }
                }

            }
        }

    }
}
