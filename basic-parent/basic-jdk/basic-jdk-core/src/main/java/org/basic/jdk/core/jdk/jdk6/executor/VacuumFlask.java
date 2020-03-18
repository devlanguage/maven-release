package org.basic.jdk.core.jdk.jdk6.executor;

public class VacuumFlask {

    private int id;
    private String name;

    public VacuumFlask() {
        this(-1, "Electric VacuumFlask");
    }

    public VacuumFlask(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ":[id=" + this.id + ", name=" + this.name + "]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
