package org.third.spring.aop.aopstyle.config;

public class DefaultUsageTracked implements UsageTracked {

    public void incrementUseCount() {
System.out.println("DefaultUsageTracked.incrementUseCount()");
    }

}
