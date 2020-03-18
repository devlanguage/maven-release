package org.basic.jdk.jdk8.optional;

public class PersonRepository {

    public String findNameById(String id) {
        return id == null ? null : "Name";
    }

}
