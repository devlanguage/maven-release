package org.basic.jdk.core.pattern.structural.Composite.test1;

/**
 * A Component with some common function implementation You can abstract it.
 */
import java.util.Enumeration;
import java.util.Vector;

public class Employee {

    String name;
    float salary;
    Vector<Employee> subordinates;
    boolean isLeaf;
    Employee parent = null;

    // -------------------------------------------
    public Employee(String _name, float _salary) {

        this(null, _name, _salary);
    }

    // -------------------------------------------
    public Employee(Employee _parent, String _name, float _salary) {

        name = _name;
        salary = _salary;
        parent = _parent;
        subordinates = new Vector<Employee>();
        isLeaf = false;
    }

    // -------------------------------------------
    public void setLeaf(boolean b) {

        isLeaf = b; // if true, do not allow children
    }

    // -------------------------------------------
    public float getSalary() {

        return salary;
    }

    // -------------------------------------------
    public String getName() {

        return name;
    }

    // -------------------------------------------
    public boolean add(Employee e) {

        if (!isLeaf) {
            subordinates.addElement(e);
        }
        return isLeaf; // false if unsuccessful
    }

    // -------------------------------------------
    public void remove(Employee e) {

        if (!isLeaf) {
            subordinates.removeElement(e);
        }
    }

    // -------------------------------------------
    public Enumeration elements() {

        return subordinates.elements();
    }

    // -------------------------------------------
    public Employee searchChild(String s) {

        Employee newEmp = null;

        if (getName().equals(s)) {
            return this;
        } else {
            boolean found = false;
            Enumeration e = elements();
            while (e.hasMoreElements() && (!found)) {
                newEmp = (Employee) e.nextElement();
                found = newEmp.getName().equals(s);
                if (!found) {
                    newEmp = newEmp.searchChild(s);
                    found = (newEmp != null);
                }
            }
            if (found) {
                return newEmp;
            } else {
                return null;
            }
        }
    }

    // -------------------------------------------
    public float getSalaries() {

        float sum = salary;
        for (int i = 0; i < subordinates.size(); i++) {
            sum += subordinates.elementAt(i).getSalaries();
        }
        return sum;
    }

}