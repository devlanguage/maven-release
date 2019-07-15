package org.basic.net.c14_mail;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.util.*;

public class MailClientAuth extends MailClient{
  private Authenticator ma;
  private String sendHost="localhost";
  private String receiveHost="localhost";
  private String receiveProtocol="imap";
  private String fromAddr="admin@mydomain.com";  //发送者地址
  private String toAddr="admin@mydomain.com"; //接收者地址

  public void init()throws Exception{
    //设置属性
    Properties  props = new Properties();
    props.put("mail.smtp.host", sendHost);
    
    // 创建Session对象
    ma=new MailAuthenticator();
    session = Session.getDefaultInstance(props,ma);

    // 创建Store对象
    store = session.getStore(receiveProtocol);
    //连接到邮件服务器
    store.connect(receiveHost,null, null);
  }
  
  public static void main(String[] args)throws Exception {
    MailClientAuth client=new MailClientAuth();
    client.init();
    client.sendMessage(client.fromAddr,client.toAddr);
    client.receiveMessage();
    client.close();
    System.exit(0);
  }
}


