package org.basic.arithmetic.data;

/**
 * @author feiye
 * 
 */
public class Person {

    private final static int BASE_INT = (int) Math.random() * 1000;
    private int id = BASE_INT;
    private String name = "ZhangSan-" + id;

    public Person(int id) {

        this.id = id;
        this.name = "ZhangSan-" + this.id;
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

    @Override
    public String toString() {

        return new StringBuffer(this.getClass().getSimpleName()).append(":[id=").append(this.id).append(", name=")
                .append(this.name).append("]").toString();
    }

    @Override
    public int hashCode() {

        // final int PRIME = 31;
        // int result = 1;
        // result = PRIME * result + id;
        // result = PRIME * result + ((name == null) ? 0 : name.hashCode());
        // return result;
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Person other = (Person) obj;
        if (id != other.id)
            return false;
        // if (name == null) {
        // if (other.name != null)
        // return false;
        // } else if (!name.equals(other.name))
        //            return false;
        return true;
    }
}
