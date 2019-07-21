package org.basic.io.xml.dom.jaxb;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.basic.common.bean.CommonLogger;
import org.basic.io.xml.stax.digester.Teacher;

public class JaxbTest {
    private static final String DECLARATION = "<?xml version='1.0' encoding='utf-8'?><!DOCTYPE document SYSTEM 'xmlschemas/domino_7_0_1.dtd'>";
    private static final String ENCODING = "UTF-8";
    private final static Person person = createPerson();
    static final CommonLogger logger = CommonLogger.getLog(JaxbTest.class);

    public static final String jaxbToString(Object obj) {
        try {
            javax.xml.bind.JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());

            javax.xml.bind.Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            // marshaller.setProperty(Marshaller.JAXB_ENCODING, "GBK");
            // marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "GBK");

            java.io.StringWriter writer = new StringWriter();
            marshaller.marshal(obj, writer);
            return writer.getBuffer().toString();

        } catch (Exception e) {
            logger.error("Failed to parse the " + obj, e);
            return String.valueOf(obj);
        }
    }

    public static final Object jaxbToString(Class jaxbObject, String xml) {
        try {
            javax.xml.bind.JAXBContext jaxbContext = JAXBContext.newInstance(jaxbObject);

            javax.xml.bind.Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
            // marshaller.setProperty(Marshaller.JAXB_ENCODING, "GBK");
            // marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "GBK");

            javax.xml.bind.Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Object bean = unmarshaller.unmarshal(new StringReader(xml));
            System.out.println(bean);
            return bean;

        } catch (Exception e) {
            logger.error("Failed to parse the " + xml, e);
            return null;
        }
    }

    public static void main(String[] args) {
//        System.out.println(jaxbToString(person));
        System.out.println(jaxbToString(new Teacher()));
    }

    private final static Person createPerson() {
        Person<Hobby> person = new Person<Hobby>();

        person.setUserid(112);
        person.setUsername("就不告诉你");
        person.setBirthday(new Date());
        person.setGraduateDay(new Date());

        NameValuePair province = new NameValuePair();
        province.setName("province");
        province.setValue("ZheJiang");
        person.addAddress(province);
        NameValuePair city = new NameValuePair();
        city.setName("city");
        city.setValue("HangZhou");
        person.addAddress(city);

        person.setSex(Sex.MALE);
        person.setHooby(new BasketBall());
        return person;
    }

    private String getPersonXml() throws Exception {
        try {
            JaxbBinder binder = new JaxbBinder(Person.class);
            return binder.toXml(person, ENCODING, DECLARATION);
        } catch (Exception e) {
            throw e;
        }
    }
}