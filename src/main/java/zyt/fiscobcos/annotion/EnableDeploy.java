package zyt.fiscobcos.annotion;

import java.lang.annotation.*;

/**
 * 此注解配合配置文件中basePackages使用，在合约类添加此注解，配置文件配置包名，实现扫描
 * @author zyt
 * @date 2023/09/28
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface EnableDeploy {
}
