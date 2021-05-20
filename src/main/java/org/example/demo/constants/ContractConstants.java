package org.example.demo.constants;

import java.lang.Exception;
import java.lang.RuntimeException;
import java.lang.String;

public class ContractConstants {
  public static String HelloWorldAbi;

  public static String HelloWorldBinary;

  public static String HelloWorldGmBinary;

  static {
    try {
      HelloWorldAbi = org.apache.commons.io.IOUtils.toString(Thread.currentThread().getContextClassLoader().getResource("abi/HelloWorld.abi"));
      HelloWorldBinary = org.apache.commons.io.IOUtils.toString(Thread.currentThread().getContextClassLoader().getResource("bin/ecc/HelloWorld.bin"));
      HelloWorldGmBinary = org.apache.commons.io.IOUtils.toString(Thread.currentThread().getContextClassLoader().getResource("bin/sm/HelloWorld.bin"));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
