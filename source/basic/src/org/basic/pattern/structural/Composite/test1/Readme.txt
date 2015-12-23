合成模式：合成模式将对象组织到树结构中，可以用来描述整体与部分的关系。合成模式就是一个处理对象的树结构的模式。合成模式把部分与整体的关系用树结构
       表示出来。合成模式使得客户端把一个个单独的成分对象和由他们复合而成的合成对象同等看待。 
 * bossRootNode
 *   |__marketVP
 *   |  |___salesMgr
 *   |  |___salesMgr
 *   |__prodVP
 *      |___prodMgr
 *      |___shipMgr
 
public class Employee {
    String name;
    float salary;
    Vector<Employee> subordinates;
    boolean isLeaf;
    Employee parent = null;
}
public class EmployeeTree {
    Employee bossRootNode, marketVP, prodVP;
    Employee salesMgr, advMgr;
    Employee prodMgr, shipMgr;
}