package org.example.demo.constants;

public class ContractConstants {
    public static final String HelloWorldAbi;

    public static final String HelloWorldBinary;

    public static final String HelloWorldGmBinary;

    static {
        try {
            HelloWorldAbi =
                    org.apache.commons.io.IOUtils.toString(
                            Thread.currentThread()
                                    .getContextClassLoader()
                                    .getResource("abi/HelloWorld.abi"));
            HelloWorldBinary =
                    org.apache.commons.io.IOUtils.toString(
                            Thread.currentThread()
                                    .getContextClassLoader()
                                    .getResource("bin/HelloWorld.bin"));
            HelloWorldGmBinary =
                    org.apache.commons.io.IOUtils.toString(
                            Thread.currentThread()
                                    .getContextClassLoader()
                                    .getResource("bin/HelloWorldSM.bin"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
