package org.basic.jdk.jdk8.lambda;

import java.util.ArrayList;
import java.util.List;

class Zoo {
    private List animalList;

    public Zoo(List animalList, int size) {
        this.animalList = animalList;
        System.out.println("Zoo created.");
    }
}

interface ZooFactory {
    Zoo getZoo(List animals, int size);
}

public class ConstructorReferenceExample {

    public static void main(String[] args) {
        // following commented line is lambda expression equivalent
        // ZooFactory zooFactory = (List animalList)-> {return new
        // Zoo(animalList);};
        ZooFactory zooFactory = Zoo::new;
        System.out.println("Ok");
        Zoo zoo = zooFactory.getZoo(new ArrayList(), 0);
    }
}