package org.basic.arithmetic;

public class UserBean {

    private int id;
    private String userName;

    public UserBean(int id) {

        this(id, "Tester");
    }

    public UserBean(int id, String userName) {

        this.id = id;
        this.userName = userName;
    }

    @Override
    public String toString() {

        return this.getClass().getSimpleName() + ":[id=" + id + ",userName=" + this.userName + "]";
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

}
