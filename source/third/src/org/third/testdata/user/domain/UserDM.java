
import java.sql.Timestamp;

public class UserDM implements java.io.Serializable {

    private static final long serialVersionUID = 8146643356719320592L;
    private int id;
    private String name;
    private String password;
    private AddressDM address;
    private java.sql.Timestamp birthday;
    private java.sql.Date graduateDay;
    private Boolean sex;
    private Double weight;
    private float height;

    public AddressDM getAddress() {
        return address;
    }

    public void setAddress(AddressDM address) {
        this.address = address;
    }

    public final static int generateUserId() {

        return (int) (Math.random() * 1000);
    }

    public UserDM() {

        this("DEFAULT_USER_", "DEFAULT_PASSWORD_");
    }

    public UserDM(String name, String password) {

        this(generateUserId(), name, password);
    }

    @SuppressWarnings("deprecation")
    public UserDM(int id, String name, String password) {

        this.id = id;
        this.name = name + id;
        this.password = password + id;
        this.sex = true;
        this.address = new AddressDM();
        this.birthday = new Timestamp(2013, 11, 10, 12, 20, 30, 0);
        this.graduateDay = new java.sql.Date(2100, 11, 12);
        this.height = (float) (Math.random() * 100);
        this.weight = (Math.random() * 100);
    }

    public UserDM(int id, String name, java.sql.Timestamp birthDay, java.sql.Date graduateDay, Boolean sex, Double weight, float height) {
        this.id = id;
        this.name = name;
        this.birthday = birthDay;
        this.graduateDay = graduateDay;
        this.sex = sex;
        this.weight = weight;
        this.height = height;
    }

    @Override
    public String toString() {

        return "User:[" + hashCode() + " name=" + this.name + ",password=" + this.password + ",address=" + this.address + "]";
    }

    /**
     * @return get method for the field name
     */
    public String getName() {

        return this.name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {

        this.name = name;
    }

    /**
     * @return get method for the field password
     */
    public String getPassword() {

        return this.password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {

        this.password = password;
    }

    /**
     * @return get method for the field id
     */
    public int getId() {

        return this.id;
    }

    /**
     * @return the graduateDay
     */
    public java.sql.Date getGraduateDay() {
        return graduateDay;
    }

    /**
     * @param graduateDay
     *            the graduateDay to set
     */
    public void setGraduateDay(java.sql.Date graduateDay) {
        this.graduateDay = graduateDay;
    }

    /**
     * @return the birthday
     */
    public java.sql.Timestamp getBirthday() {
        return birthday;
    }

    /**
     * @param birthday
     *            the birthday to set
     */
    public void setBirthday(java.sql.Timestamp birthday) {
        this.birthday = birthday;
    }

    /**
     * @return the sex
     */
    public Boolean getSex() {
        return sex;
    }

    /**
     * @param sex
     *            the sex to set
     */
    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    /**
     * @return the weight
     */
    public Double getWeight() {
        return weight;
    }

    /**
     * @param weight
     *            the weight to set
     */
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    /**
     * @return the height
     */
    public float getHeight() {
        return height;
    }

    /**
     * @param height
     *            the height to set
     */
    public void setHeight(float height) {
        this.height = height;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(int id) {

        this.id = id;
    }
}
