package org.third.testdata.db.bean;

public class DbObject {

    public final static String BEAN_NAME_DB_OBJECT = "dbObject";
    private String user;
    private String password;
    private String url;
    private String driverClass;

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    @Override
    public String toString() {

        return super.toString() + "@ user=" + user + ",password=" + password + ", url=" + url
                + ", driverClass=" + driverClass + "";
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
     * @return get method for the field user
     */
    public String getUser() {

        return this.user;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(String user) {

        this.user = user;
    }

}
