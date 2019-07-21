package org.basic.io.xml.dom.dom4j;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.basic.io.xml.UserJavaBeanForXML;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;

public class CreateXMLForDOM4j {

    public static String dom4jToXml(List<UserJavaBeanForXML> list) {

        String strXml = null;
        try {
            if (null != list && !list.isEmpty()) {
                org.dom4j.DocumentFactory df = DocumentFactory.getInstance();
                // org.dom4j.Document doc = DocumentHelper.createDocument();
                org.dom4j.Document doc = df.createDocument("UTF-8");
                // 创建根节点
                org.dom4j.Element UsersElt = doc.addElement("users");

                for (UserJavaBeanForXML user : list) {
                    // 在节点<cities>下增加子节点<city>
                    Element userElt = UsersElt.addElement("user");
                    // 在这里给user加一个 标识 属性
                    userElt.addAttribute("uuid", "1");

                    // 在节点<city>下增加子节点<id>
                    org.dom4j.Element idElt = userElt.addElement("uuid");
                    idElt.addText(String.valueOf(user.getUuid()));

                    // 在节点<city>下增加子节点<name>
                    org.dom4j.Element nameElt = userElt.addElement("myname");
                    nameElt.addText(user.getMyname());

                    org.dom4j.Element sexElt = userElt.addElement("sex");
                    sexElt.addText(user.getSex());

                    org.dom4j.Element ageElt = userElt.addElement("age");
                    ageElt.addText(user.getAge() + "");

                }

                // 有样式(缩进)的写出
                org.dom4j.io.OutputFormat opf = org.dom4j.io.OutputFormat.createPrettyPrint();
                opf.setEncoding("UTF-8");
                opf.setTrimText(true);

                // 生成XML文件
                org.dom4j.io.XMLWriter xmlOut = new org.dom4j.io.XMLWriter(new FileOutputStream("city-dom4j.xml"), opf);
                xmlOut.write(doc);
                xmlOut.flush();
                xmlOut.close();

                // 获取XML字符串形式
                java.io.StringWriter writerStr = new java.io.StringWriter();
                org.dom4j.io.XMLWriter xmlw = new org.dom4j.io.XMLWriter(writerStr, opf);
                xmlw.write(doc);
                strXml = writerStr.getBuffer().toString();

                // 无样式的
                // strXml = doc.asXML();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("DOM4J:" + strXml);
        return strXml;
    }

}