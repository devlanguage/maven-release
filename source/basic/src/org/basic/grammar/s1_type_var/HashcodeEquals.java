package org.basic.grammar.s1_type_var;

import java.util.HashSet;

class SameEqualDifferHash {
    private String name = "Test01";

    public SameEqualDifferHash(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        // return super.equals(obj);
        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}

public class HashcodeEquals {
    public static void main(String[] args) {
        SameEqualDifferHash sedf1 = new SameEqualDifferHash("test01");
        SameEqualDifferHash sedf2 = new SameEqualDifferHash("test01");

        HashSet<SameEqualDifferHash> hs = new HashSet<>(10);
        hs.add(sedf1);
        hs.add(sedf2);
        
        for(Object s : hs.toArray()){
            System.out.println(s);
        }

    }
}
