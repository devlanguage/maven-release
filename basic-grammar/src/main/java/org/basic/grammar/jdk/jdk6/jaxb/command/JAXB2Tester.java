package org.basic.grammar.jdk.jdk6.jaxb.command;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

enum Gender {

    MALE(true), FEMALE(false);

    private boolean value;

    Gender(boolean _value) {

        value = _value;
    }
}

public class JAXB2Tester {

    public static void main(String[] args) throws JAXBException, IOException {

        JAXBContext context = JAXBContext.newInstance(new Class[] { Person.class });

        // 下面代码演示将对象转变为XML
        Marshaller m = context.createMarshaller();
        Address address = new Address("China", "Beijing", "Beijing", "ShangDi West", "100080");
        Person p = new Person(Calendar.getInstance(), "JAXB2", address, Gender.MALE, "SW");
        FileWriter fw = new FileWriter("person.xml");

        m.marshal(p, fw);

        // 下面代码演示将上面生成的XML转换为对�?
        FileReader fr = new FileReader("person.xml");
        Unmarshaller um = context.createUnmarshaller();
        Person p2 = (Person) um.unmarshal(fr);
        System.out.println("Country:" + p2.getAddress().getCountry());
    }
}

@XmlRootElement
class Person {

    @XmlElement
    Calendar birthDay; // birthday将作为person的子元素

    @XmlAttribute
    String name; // name将作为person的的�?个属�?

    public Address getAddress() {

        return address;
    }

    @XmlElement
    Address address; // address将作为person的子元素

    @XmlElement
    Gender gender; // gender将作为person的子元素

    @XmlElement
    String job; // job将作为person的子元素

    public Person() {

    }

    public Person(Calendar birthDay, String name, Address address, Gender gender, String job) {

        this.birthDay = birthDay;
        this.name = name;
        this.address = address;
        this.gender = gender;
        this.job = job;
    }
}

class Address {

    @XmlAttribute
    String country;

    @XmlElement
    String state;

    @XmlElement
    String city;

    @XmlElement
    String street;

    @XmlElement
    String zipcode; // 由于没有添加@XmlElement,�?以该元素不会出现在输出的xml�?

    public Address() {

    }

    public Address(String country, String state, String city, String street, String zipcode) {

        this.country = country;
        this.state = state;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    public String getCountry() {

        return country;
    }
}
