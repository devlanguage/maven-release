package org.basic.xml.stax.digester.catalog;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;

public class CatalogTest {

    public static void main(String[] args) {

        try {
            // 创建digester对象并指定解析规�?
            Digester digester = DigesterLoader.createDigester(CatalogTest.class.getResource("Catalog-rules.xml"));
            // 传�?配置文件引用input到digester进行解析，解析出的对象就是对应的对象�?
            Catalog catalog = (Catalog) digester.parse(CatalogTest.class.getResourceAsStream("Catalog.xml"));
            System.out.println(catalog.toString());
        } catch (Exception exc) {
            exc.printStackTrace();
        }

    }
}
