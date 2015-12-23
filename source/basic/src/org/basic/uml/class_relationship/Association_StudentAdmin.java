package org.basic.uml.class_relationship;

import java.awt.Color;
//三者从概念上来讲：
//Association 两者“无”整体与部分关系， 是一般的关联，有”use a”的含义
//Aggregation 两者“有”整体与部分关系， 有”has a”的含义。 部分可脱离整体，依然有意义，部分被client创建，也可被多个整体共享. Aggregated by Reference
//Composition 两者“有”整体与部分关系， 有”Contain a”的含义。部分不可脱离整体，部分无意义，部分被整体创建， 是独占式的.     Aggregated by Value
//
//从代码实现的角度上讲：
//    三者都是以属性出现，其中Association中作为属性出现时，不需要对其进行强制赋值，只要在使用是对其进行初始化即可。
//    Aggregation中作为属性出现时，需要在构造器中通过传递参数来对其进行初始化。
//    Composition中  作为属性出现时，需要在整体的构造器中创建部分的具体实例，完成对其的实例化。
//    
//从数据库的层面上来讲：Association不需要被级联删除，Aggregation不需要被级联删除，Composition是需要被级联删除的。
public class Association_StudentAdmin {

    public static void main(String aa[]) {

        Student aStudent = new Student("Peter", 22);
        BasketBall aBasketBall = new BasketBall(Color.red, 32);
        aStudent.getBall(aBasketBall);
        aStudent.play();
    }
}
