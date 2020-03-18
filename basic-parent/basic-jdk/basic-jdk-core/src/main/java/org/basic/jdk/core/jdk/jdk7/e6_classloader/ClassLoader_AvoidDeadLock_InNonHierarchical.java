package org.basic.jdk.core.jdk.jdk7.e6_classloader;

/**
 * <pre>
 * 6. Multithreaded Custom Class Loaders  
 *     
 *     解决并发下加载class可能导致的死锁问题，这个是jdk1.6的一些新版本就解决了，jdk7也做了一些优化。有兴趣可以仔细从官方文档详细了解
 * 
 * jdk7前：
 *   
 *     Class Hierarchy:            
 *       class A extends B
 *       class C extends D
 *     ClassLoader Delegation Hierarchy:
 *     Custom Classloader CL1:
 *       directly loads class A 
 *       delegates to custom ClassLoader CL2 for class B
 *     Custom Classloader CL2:
 *       directly loads class C
 *       delegates to custom ClassLoader CL1 for class D
 *     Thread 1:
 *       Use CL1 to load class A (locks CL1)
 *         defineClass A triggers
 *           loadClass B (try to lock CL2)
 *     Thread 2:
 *       Use CL2 to load class C (locks CL2)
 *         defineClass C triggers
 *           loadClass D (try to lock CL1)
 *     Synchronization in the ClassLoader class wa 
 * 
 * jdk7
 * 
 *     Thread 1:
 *       Use CL1 to load class A (locks CL1+A)
 *         defineClass A triggers
 *           loadClass B (locks CL2+B)
 *     Thread 2:
 *       Use CL2 to load class C (locks CL2+C)
 *         defineClass C triggers
 *           loadClass D (locks CL1+D)
 * 
 * </pre>
 * 
 * @author ygong
 *
 */
public class ClassLoader_AvoidDeadLock_InNonHierarchical {

}
