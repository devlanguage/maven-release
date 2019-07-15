
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;

public class Student //
        implements BeanNameAware, BeanFactoryAware, BeanClassLoaderAware {

    private int id;
    private String userName;
    private List<String> hobbies;
    private Set<String> roles;
    private Map<String, String> address;
    private Properties emails;

    private String pmName;
    private StudentManager pm;

    private String beanName;
    private ClassLoader beanClassLoader;
    private BeanFactory beanFactory;

    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public void setBeanName(String name) {
        this.beanName = name;
    }

    @Override
    public String toString() {

        return this.getClass().getSimpleName() + ":hashCode=" + hashCode() + " id=" + id + ", userName=" + userName + ",hobbies=" + hobbies
                + ",address" + address + ", pm=" + pm + ",pmName=" + pmName;
    }

    public Student() {
        int int1 = (int) (10 + Math.random());
        id = int1;
        userName = "Student_" + int1;
    }

    public Student(int id, String userName) {

        this.id = id;
        this.userName = userName;
    }

    public Student(int id, String userName, StudentManager pm, String pmName) {

        this.id = id;
        this.userName = userName;
        this.pm = pm;
        this.pmName = pmName;
    }

    /**
     * @return get method for the field pm
     */
    public StudentManager getPm() {

        return this.pm;
    }

    /**
     * @param pm
     *            the pm to set
     */
    public void setPm(StudentManager pm) {

        this.pm = pm;
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
