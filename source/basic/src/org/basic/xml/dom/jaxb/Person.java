package org.basic.xml.dom.jaxb;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * <pre>
 * <ns2:Person sex="MALE" xmlns:ns2="http://jaxbtest.com/person">
 *     <userId>112</userId>
 *     <UserName>就不告诉你</UserName>
 *     <graduateDay>NewFormat:2014-03-17</graduateDay>
 *     <birthday>2014-03-17T17:08:41.291+08:00</birthday>
 *     <addresses>
 *         <address name="province">ZheJiang</address>
 *         <address name="city">HangZhou</address>
 *     </addresses>
 *     <hobby xsi:type="basketBall" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
 *         <diameter>33 CM</diameter>
 *         <weight>0.444 KG</weight>
 *     </hobby>
 * </ns2:Person>
 * </pre>
 */
@XmlRootElement(name = "Person", namespace = "http://jaxbtest.com/person")
@XmlAccessorType(XmlAccessType.FIELD)
// field + set/get explicted annotated
// @XmlAccessorType(XmlAccessType.PROPERTY) //field explicied anntoated + set/get
// @XmlAccessorType(XmlAccessType.PUBLIC_MEMBER) //Default, public field+public set/getter + other filed explicited
// annotated
@XmlType(propOrder = { "userId", "username", "sex", "graduateDay", "birthday", "addresses", "hobby" })
@XmlSeeAlso(value = { Hobby.class })
public class Person<T> {

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    private Integer userId;
    @XmlElement(defaultValue = "defaultUser", name = "UserName", nillable = true, required = true)
    private String username;

    @XmlJavaTypeAdapter(value = DateXmlAdapter.class)
    private Date graduateDay;
    private Date birthday;

    @XmlElementWrapper(name = "addresses")
    @XmlElement(name = "address")
    private Set<NameValuePair> addresses = new HashSet<NameValuePair>();

    @XmlAttribute
    private Sex sex;

    private T hobby;

    public T getHooby() {
        return hobby;
    }

    public void setHooby(T hooby) {
        this.hobby = hooby;
    }

    public void addAddress(NameValuePair address) {
        this.addresses.add(address);
    }

    public void setAddresses(Set<NameValuePair> addresses) {
        this.addresses = addresses;
    }

    public Integer getUserid() {
        return userId;
    }

    public void setUserid(Integer userid) {
        this.userId = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Persion [userid=");
        builder.append(userId);
        builder.append(", username=");
        builder.append(username);
        builder.append(", graduateDay=");
        builder.append(graduateDay);
        builder.append(", birthday=");
        builder.append(birthday);
        builder.append("]");
        return builder.toString();
    }

    public Date getGraduateDay() {
        return graduateDay;
    }

    public void setGraduateDay(Date graduateDay) {
        this.graduateDay = graduateDay;
    }

}