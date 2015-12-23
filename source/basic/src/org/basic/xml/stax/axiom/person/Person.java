/**
 * 
 */
package org.basic.xml.stax.axiom.person;

/**
 * @author ygong
 * 
 */
public class Person {

    public int BASE_INT = (int) (Math.random() * 100);

    private String name = "ZhangSan-" + BASE_INT;
    private int age = BASE_INT;
    private String sex = age > 50 ? "Female" : "Male";
    private String phone = (int) (Math.random() * 1000000000) + "";

    /**
     * @return the address
     */
    public String getAddress() {

        return sex;
    }

    /**
     * @param address
     *            the address to set
     */
    public void setAddress(String address) {

        this.sex = address;
    }

    /**
     * @return the age
     */
    public int getAge() {

        return age;
    }

    /**
     * @param age
     *            the age to set
     */
    public void setAge(int age) {

        this.age = age;
    }

    /**
     * @return the name
     */
    public String getName() {

        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {

        this.name = name;
    }

    /**
     * @return the phonenum
     */
    public String getPhonenum() {

        return phone;
    }

    /**
     * @param phonenum
     *            the phonenum to set
     */
    public void setPhonenum(String phonenum) {

        this.phone = phonenum;
    }

    @Override
    public String toString() {

        return new StringBuffer(this.getClass().getSimpleName()).append(":[name=")
                .append(this.name).append(", age=").append(this.age).append(",address=").append(
                        this.sex).append(", phone=").append(this.phone).append("]").toString();

    }

}
