package org.third.spring.config;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Person {

    private int id;
    private String userName;
    private List<String> hobbies;
    private Set<String> roles;
    private Map<String, String> address;
    private Properties emails;

    private String pmName;
    private PersonManager pm;

    /**
     * @return get method for the field pm
     */
    public PersonManager getPm() {

        return this.pm;
    }

    /**
     * @param pm
     *            the pm to set
     */
    public void setPm(PersonManager pm) {

        this.pm = pm;
    }

    @Override
    public String toString() {

        return super.toString()+ ": id=" + id + ", userName=" + userName + ",hobbies=" + hobbies + ",address" + address
                + ", pm=" + pm + ",pmName=" + pmName;
    }

    public Person() {

    }

    public Person(int id, String userName) {

        this.id = id;
        this.userName = userName;
    }

    public Person(int id, String userName, PersonManager pm, String pmName) {

        this.id = id;
        this.userName = userName;
        this.pm = pm;
        this.pmName = pmName;
    }

    /**
     * @return get method for the field address
     */
    public Map<String, String> getAddress() {

        return this.address;
    }

    /**
     * @param address
     *            the address to set
     */
    public void setAddress(Map<String, String> address) {

        this.address = address;
    }

    /**
     * @return get method for the field hobbies
     */
    public List<String> getHobbies() {

        return this.hobbies;
    }

    /**
     * @param hobbies
     *            the hobbies to set
     */
    public void setHobbies(List<String> hobbies) {

        this.hobbies = hobbies;
    }

    /**
     * @return get method for the field id
     */
    public int getId() {

        return this.id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(int id) {

        this.id = id;
    }

    /**
     * @return get method for the field userName
     */
    public String getUserName() {

        return this.userName;
    }

    /**
     * @param userName
     *            the userName to set
     */
    public void setUserName(String userName) {

        this.userName = userName;
    }

    /**
     * @return get method for the field pmName
     */
    public String getPmName() {

        return this.pmName;
    }

    /**
     * @param pmName
     *            the pmName to set
     */
    public void setPmName(String pmName) {

        this.pmName = pmName;
    }

    /**
     * @return get method for the field email
     */
    public Properties getEmails() {

        return this.emails;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmails(Properties email) {

        this.emails = email;
    }

    /**
     * @return get method for the field roles
     */
    public Set<String> getRoles() {

        return this.roles;
    }

    /**
     * @param roles
     *            the roles to set
     */
    public void setRoles(Set<String> roles) {

        this.roles = roles;
    }

}
