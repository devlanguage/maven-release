package org.basic.jdk.core.jdk.jdk7.e9_jvm;

/**
 * <pre>
 * 9.jvm方面的一些特性增强，下面这些特性有些在jdk6中已经存在，这里做了一些优化和增强。
 * 
 * 1.Jvm支持非java的语言 invokedynamic 指令 
 * 
 * 2. Garbage-First Collector 适合server端，多处理器下大内存，将heap分成大小相等的多个区域，mark阶段检测每个区域的存活对象，compress阶段将存活对象最小的先做回收，这样会腾出很多空闲区域，这样并发回收其他区域就能减少停止时间，提高吞吐量。 
 * 
 * 3. HotSpot性能增强 
 *     Tiered Compilation  -XX:+UseTieredCompilation 多层编译，对于经常调用的代码会直接编译程本地代码，提高效率
 *    Compressed Oops  压缩对象指针，减少空间使用
 *   Zero-Based Compressed Ordinary Object Pointers (oops) 进一步优化零基压缩对象指针，进一步压缩空间
 * 
 * 4. Escape Analysis  逃逸分析，对于只是在一个方法使用的一些变量，可以直接将对象分配到栈上，方法执行完自动释放内存，而不用通过栈的对象引用引用堆中的对象，那么对于对象的回收可能不是那么及时。
 * 
 * 5. NUMA Collector Enhancements  
 * 
 * NUMA(Non Uniform Memory Access),NUMA在多种计算机系统中都得到实现,简而言之,就是将内存分段访问,类似于硬盘的RAID,Oracle中的分簇
 * 
 * </pre>
 */
public class JvmEnhancement {

}
