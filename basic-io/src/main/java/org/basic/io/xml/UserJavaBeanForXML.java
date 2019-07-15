package org.basic.io.xml;

public class UserJavaBeanForXML {

    private String uuid;
    private String myname;
    private String sex;
    private int age;

    public UserJavaBeanForXML() {

        super();

    }

    public UserJavaBeanForXML(String uuid, String myname, String sex, int age) {

        super();
        this.uuid = uuid;
        this.myname = myname;
        this.sex = sex;
        this.age = age;
    }

    public String getUuid() {

        return uuid;
    }

    public void setUuid(String uuid) {

        this.uuid = uuid;
    }

    public String getMyname() {

        return myname;
    }

    public void setMyname(String myname) {

        this.myname = myname;
    }

    public String getSex() {

        return sex;
    }

    public void setSex(String sex) {

        this.sex = sex;
    }

    public int getAge() {

        return age;
    }

    public void setAge(int age) {

        this.age = age;
    }
    @Override
    public String toString() {
        return "[uuid="+uuid+",myname="+myname+",age="+age+",sex="+sex+"]";
    }

}