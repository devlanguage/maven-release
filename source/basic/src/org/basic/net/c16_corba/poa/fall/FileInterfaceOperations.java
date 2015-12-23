package org.basic.net.c16_corba.poa.fall;


/**
* org/basic/corba/poa/fall/FileInterfaceOperations.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ���
* ���� FileServer.idl
* 2010��8��2�� ����һ ����06ʱ02��55�� CST
*/

public interface FileInterfaceOperations 
{
  org.basic.net.c16_corba.poa.fall.FileInterfacePackage.Data[] downloadFile (String fileName);
  org.basic.net.c16_corba.poa.fall.entity.Student sayHi (String user);
} // interface FileInterfaceOperations
