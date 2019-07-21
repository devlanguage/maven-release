package org.basic.grammar.pattern.structural.Composite.test1;

public class EmployeeTree {

    /**
     * <pre>
     * bossRootNode
     *   |__marketVP
     *   |  |___salesMgr
     *   |  |___salesMgr
     *   |__prodVP
     *      |___prodMgr
     *      |___shipMgr
     * </pre>
     */
    Employee bossRootNode, marketVP, prodVP;
    Employee salesMgr, advMgr;
    Employee prodMgr, shipMgr;

    public EmployeeTree() {

        constructEmployeeTree();
    }

    // --------------------------------------
    private void constructEmployeeTree() {

        bossRootNode = new Employee("CEO", 200000);
        bossRootNode.add(marketVP = new Employee("Marketing VP", 100000));
        bossRootNode.add(prodVP = new Employee("Production VP", 100000));

        marketVP.add(salesMgr = new Employee("Sales Mgr", 50000));
        marketVP.add(advMgr = new Employee("Advt Mgr", 50000));

        for (int i = 0; i < 5; i++) {
            salesMgr.add(new Employee("Sales " + new Integer(i).toString(),
                    30000.0F + (float) (Math.random() - 0.5) * 10000));
        }
        advMgr.add(new Employee("Secy", 20000));

        prodVP.add(prodMgr = new Employee("Prod Mgr", 40000));
        prodVP.add(shipMgr = new Employee("Ship Mgr", 35000));
        for (int i = 0; i < 4; i++) {
            prodMgr.add(new Employee("Manuf " + new Integer(i).toString(), 25000.0F + (float) (Math
                    .random() - 0.5) * 5000));
        }
        for (int i = 0; i < 3; i++) {
            shipMgr.add(new Employee("ShipClrk " + new Integer(i).toString(),
                    20000.0F + (float) (Math.random() - 0.5) * 5000));
        }

    }

    /**
     * @return get method for the field boss
     */
    public Employee getBossRootNode() {

        return this.bossRootNode;
    }

    /**
     * @param boss
     *            the boss to set
     */
    public void setBossRootNode(Employee boss) {

        this.bossRootNode = boss;
    }

    // new GetPropertyAction("os.name"));
    // System.out.println(osName);

}