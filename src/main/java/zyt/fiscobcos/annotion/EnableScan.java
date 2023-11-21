package zyt.fiscobcos.annotion;

import java.lang.annotation.*;

/**
 * 将此注解放在springboot的启动类上，实现自动部署合约
 * @author zyt
 * @date 2023/09/28
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface EnableScan {

    /**
     * 默认值，目前不用
     * @return
     */
    String value() default "";

    /**
     * 扫描合约所在的包
     * @return
     */
    String[] packages() default {};
}
