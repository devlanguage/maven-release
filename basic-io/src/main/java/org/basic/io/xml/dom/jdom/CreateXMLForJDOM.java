package org.basic.io.xml.dom.jdom;

public class CreateXMLForJDOM {
//
//    public static String jdomToXml(List<UserJavaBeanForXML> list) {
//
//        String xmlStr = null;
//        try {
//            // 创建文档根节点<cities>
//            Element usersElt = new Element("users");
//            if (null != list && !list.isEmpty()) {
//                for (UserJavaBeanForXML user : list) {
//                    // 创建子元素节点<city>
//                    Element userElt     = new Element("user");
//
//                    // 可以跟user对象增加 标识 属性
//                    userElt.setAttribute("uuid", "1");
//
//                    // 创建子元素节点<id>
//                    Element idElt = new Element("uuid");
//                    // 向元素<city>中添加子元素<id>
//                    userElt.addContent(idElt);
//                    // 添加id文本
//                    idElt.addContent(String.valueOf(user.getUuid()));
//
//                    // 创建元素节点<name>
//                    Element nameElt = new Element("myname");
//                    // 向元素<city>中添加子元素<name>
//                    userElt.addContent(nameElt);
//                    // 添加name文本
//                    nameElt.addContent(user.getMyname());
//
//                    Element sexElt = new Element("sex");
//                    userElt.addContent(sexElt);
//                    sexElt.addContent(user.getSex());
//
//                    Element ageElt = new Element("age");
//                    userElt.addContent(ageElt);
//                    ageElt.addContent(user.getAge() + "");
//
//                    usersElt.addContent(userElt);
//                }
//            }
//
//            Document doc = new Document(usersElt);
//            XMLOutputter out = new XMLOutputter();
//            // 获得XML字符串形式
//            xmlStr = out.outputString(doc);
//
//            // 生成XML文件
//            PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream("city-jdom.xml"), "utf-8"));
//
//            out.output(doc, writer);
//            writer.flush();
//            writer.close();
//
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("JDOM:" + xmlStr);
//        return xmlStr;
//    }

}