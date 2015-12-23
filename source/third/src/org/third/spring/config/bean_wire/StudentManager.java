package org.third.spring.config.bean_wire;

public class StudentManager {

    public void init() {
//        init-method="init" 
        System.out.println("StudentManager.init() is called");
    }

    public void destory() {
//        destroy-method="destory"
        System.out.println("StudentManager.destory() is called");
    }

    private static StudentManager instance = null;

    public synchronized final static StudentManager getInstance() {

        if (instance == null) {
            instance = new StudentManager();
        }
        return instance;
    }

    public final Student createStudent() {
        System.out.println("StudentManager.createStudent() create a student");
        return new Student();
    }

    public final Student createStudent(int id, String userName) {
        System.out.println("StudentManager.createStudent(int id, String userName) create a student");
        return new Student(id, userName);
    }

    public final Student createStudent(int id, String userName, StudentManager pm, String pmName) {
        System.out.println("StudentManager.createStudent(int id, String userName, StudentManager pm, String pmName) create a student");
        return new Student(id, userName, pm, pmName);
    }

}
