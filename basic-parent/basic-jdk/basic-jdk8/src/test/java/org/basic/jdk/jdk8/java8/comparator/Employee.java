package org.basic.jdk.jdk8.java8.comparator;

public class Employee implements Comparable<Employee>{
    String name;
    int age;
    double salary;
    long mobile;
    
    public Employee(String name, int age, int salary, int mobile) {
        this.name=name;this.age=age;this.salary=salary;this.mobile=mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public long getMobile() {
        return mobile;
    }

    public void setMobile(long mobile) {
        this.mobile = mobile;
    }

    @Override
    public int compareTo(Employee argEmployee) {
        return name.compareTo(argEmployee.getName());
    }
    
}
