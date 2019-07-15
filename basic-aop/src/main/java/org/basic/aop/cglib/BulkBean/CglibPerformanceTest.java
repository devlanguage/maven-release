package org.basic.aop.cglib.BulkBean;

import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.DebuggingClassWriter;

public class CglibPerformanceTest {
    final static TargetBean targetBean = new TargetBean();// new一次，避免new对象产生的代价影响测试结果
    final static SourceBean originalSoourceBean = new SourceBean();
    final static int testCount = 1000 ;

    public final static void testBeanCopier() {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "c:\\temp");

        final BeanCopier beanCopier = BeanCopier.create(SourceBean.class, TargetBean.class, false);
        testTemplate(new TestCallback() {

            public String getName() {
                return "BeanCopier";
            }

            public TargetBean call(SourceBean source) {
                beanCopier.copy(source, targetBean, null);
                return targetBean;
            }
        }, originalSoourceBean, testCount);
    }

    public final static void testPropertyUtils() {

        testTemplate(new TestCallback() {

            public String getName() {
                return "PropertyUtils";
            }

            public TargetBean call(SourceBean source) {
                try {
//                    org.apache.commons.beanutils.BeanUtils.copyProperties(targetBean, source);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return targetBean;
            }
        }, originalSoourceBean, testCount);
    }

    public final static void testBeanUtils() {

        testTemplate(new TestCallback() {

            public String getName() {
                return "PropertyUtils";
            }

            public TargetBean call(SourceBean source) {
                try {
//                    org.apache.commons.beanutils.PropertyUtils.copyProperties(targetBean, source);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return targetBean;
            }
        }, originalSoourceBean, testCount);
    }

    public static void main(String[] args) {
        testBeanCopier(); //BeanCopier total cost=32,849,144ns , each cost=32ns
        testBeanUtils();
        testPropertyUtils();
    }

    // 定义测试的模板方法
    private static final DecimalFormat integerFormat = new DecimalFormat("#,###");

    public static void testTemplate(TestCallback callback, SourceBean source, int count) {
        int warmup = 10;
        // 先进行预热，加载一些类，避免影响测试
        for (int i = 0; i < warmup; i++) {
            callback.call(source);
        }
        restoreJvm(); // 进行GC回收
        // 进行测试
        long start = System.nanoTime();
        for (int i = 0; i < count; i++) {
            callback.call(source);
        }
        long nscost = (System.nanoTime() - start);
        System.out.println(callback.getName() + " total cost=" + integerFormat.format(nscost) + "ns , each cost="
                + nscost / count + "ns");
        restoreJvm();// 进行GC回收

    }

    // 为了测试更加精确，避免因为在一次的循环中进行处理，jvm内存,GC,Class装载对测试的影响，有一个warmup的过程，先执行少量的测试方法，这里是执行10次
    // 避免jvm内存GC对测试id影响，这里有restoreJvm强制进行一次jvm GC
    private static void restoreJvm() {
        int maxRestoreJvmLoops = 10;
        long memUsedPrev = memoryUsed();
        for (int i = 0; i < maxRestoreJvmLoops; i++) {
            System.runFinalization();
            System.gc();

            long memUsedNow = memoryUsed();
            // 如果多次GC后内存稳定了，就退出
            if ((ManagementFactory.getMemoryMXBean().getObjectPendingFinalizationCount() == 0)
                    && (memUsedNow >= memUsedPrev)) {
                break;
            } else {
                memUsedPrev = memUsedNow;
            }
        }
    }

    private static long memoryUsed() {
        Runtime rt = Runtime.getRuntime();
        return rt.totalMemory() - rt.freeMemory();
    }

}
