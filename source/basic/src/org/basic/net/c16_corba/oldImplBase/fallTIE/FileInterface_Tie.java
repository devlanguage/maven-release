package org.basic.net.c16_corba.oldImplBase.fallTIE;


/**
* org/basic/corba/poa/fallTIE/FileInterface_Tie.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ���
* ���� FileServer.idl
* 2010��8��2�� ����һ ����06ʱ00��43�� CST
*/

public class FileInterface_Tie extends _FileInterfaceImplBase
{

  // Constructors
  public FileInterface_Tie ()
  {
  }

  public FileInterface_Tie (org.basic.net.c16_corba.oldImplBase.fallTIE.FileInterfaceOperations impl)
  {
    super ();
    _impl = impl;
  }

  public org.basic.net.c16_corba.oldImplBase.fallTIE.FileInterfacePackage.Data[] downloadFile (String fileName)
  {
    return _impl.downloadFile(fileName);
  } // downloadFile

  public org.basic.net.c16_corba.oldImplBase.fallTIE.entity.Student sayHi (String user)
  {
    return _impl.sayHi(user);
  } // sayHi

  private org.basic.net.c16_corba.oldImplBase.fallTIE.FileInterfaceOperations _impl;

} // class FileInterface_Tie
