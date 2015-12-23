package org.third.bytecode.cglib.KeyFactory;

import net.sf.cglib.core.KeyFactory;

public class MapKeyExample {

    public static void main(String[] args) {
        MapKeyFactory f = (MapKeyFactory) KeyFactory.create(MapKeyFactory.class);
        Object key1 = f.newInstance(26, new String[] { "String 01", "String 02" }, true);
        Object key2 = f.newInstance(26, new String[] { "String 00", "String 02" }, true);
        Object key3 = f.newInstance(26, new String[] { "String 01", "String 02" }, true);
        System.out.println(key1.equals(key2));
        System.out.println(key1.equals(key3));
    }
}
