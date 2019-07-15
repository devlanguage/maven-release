package org.basic.io.xml.stax.stax;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.stream.Location;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLReporter;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class XHTMLOutliner {

  /**
   * Determine if this is an XHTML heading element or not
   * 
   * @param name
   *          tag name
   * @return boolean true if this is h1, h2, h3, h4, h5, or h6; false otherwise
   */
  private static boolean isHeader(String name) {

    if (name.equals("address"))
      return true;
    if (name.equals("person"))
      return true;
    if (name.equals("h3"))
      return true;
    if (name.equals("h4"))
      return true;
    if (name.equals("h5"))
      return true;
    if (name.equals("h6"))
      return true;
    return false;
  }

  public static void main(String[] args) {

    // if (args.length == 0) {
    // System.err.println("Usage: java XHTMLOutliner url");
    // return;
    // }
    // String input = args[0];
    File input = new File("java/basic/resource/pull_root_write_001.xml");

    try {
      URL u = input.toURL();
      InputStream in = u.openStream();
      XMLInputFactory factory = XMLInputFactory.newInstance();
      // 如果用户希望检查XML文档的合法性，可以设置工厂的javax.xml.stream.isValidating属性为真，这样得到的XMLStreamReader
      // 对象在读取XML文档时就会验证其合法性。
      // factory.setProperty("javax.xml.stream.isValidating",
      // Boolean.TRUE);
      // 但是，StAX解析发现文档非法时并不会抛出异常，而是通过一个XMLReporter接口来报告这种错误。下面这段代码就是常见了一个匿名类的实例，并将其注册作为StAX报告错误的接口。
      //
      factory.setXMLReporter(new XMLReporter() {

        public void report(String message, String errorType, Object relatedInformation, Location location) {

          System.err.println("Problem in " + location.getLineNumber());
          System.err.println("at line " + location.getLineNumber() + ", column " + location.getColumnNumber());
          System.err.println(message);
        }
      });

      // StAX提供了一个特殊的方法require来测试当前文档读取的位置是否满足要求。例如下面这段代码就是要求当前文档读取的位置是一个head开始标记。这个方法很像assert，如果满足条件程序就能继续下去，否则，抛出XMLStreamException，程序将无法继续下去。
      // parser.require(XMLStreamConstants.START_ELEMENT,
      // "http://www.w3.org/1999/xhtml",
      // "head");
      //
      // StAX还可以生成并输出XML文档。下面是一个例程向data.xml文件中输出一个简单的XML文档。
      //
      // OutputStream out = new FileOutputStream("data.xml");
      // XMLOutputFactory factory = XMLOutputFactory.newInstance();
      // XMLStreamWriter writer = factory.createXMLStreamWriter(out);
      //
      // writer.writeStartDocument("ISO-8859-1", "1.0");
      // writer.writeStartElement("greeting");
      // writer.writeAttribute("id", "g1");
      // writer.writeCharacters("Hello StAX");
      // writer.writeEndDocument();
      //
      // writer.flush();
      // writer.close();
      // out.close();
      XMLStreamReader parser = factory.createXMLStreamReader(in);

      int inHeader = 0;
      for (int event = parser.next(); event != XMLStreamConstants.END_DOCUMENT; event = parser.next()) {
        switch (event) {
          case XMLStreamConstants.START_ELEMENT:
            if (isHeader(parser.getLocalName())) {
              inHeader++;
            }
            break;
          case XMLStreamConstants.END_ELEMENT:
            if (isHeader(parser.getLocalName())) {
              inHeader--;
              if (inHeader == 0)
                System.out.println();
            }
            break;
          case XMLStreamConstants.CHARACTERS:
            if (inHeader > 0)
              System.out.print(parser.getText());
            break;
          case XMLStreamConstants.CDATA:
            if (inHeader > 0)
              System.out.print(parser.getText());
            break;
        } // end switch
      } // end while
      parser.close();
    } catch (XMLStreamException ex) {
      // System.out.println(ex);
    } catch (IOException ex) {
      ex.printStackTrace();

    }

  }
}
