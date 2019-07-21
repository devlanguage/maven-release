package org.basic.net.c16_corba.poa.fallTIE;


/**
* org/basic/corba/poa/fallTIE/FileInterfacePOATie.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ���
* ���� FileServer.idl
* 2010��8��2�� ����һ ����06ʱ01��55�� CST
*/

public class FileInterfacePOATie extends FileInterfacePOA
{

  // Constructors

  public FileInterfacePOATie ( org.basic.net.c16_corba.poa.fallTIE.FileInterfaceOperations delegate ) {
      this._impl = delegate;
  }
  public FileInterfacePOATie ( org.basic.net.c16_corba.poa.fallTIE.FileInterfaceOperations delegate , org.omg.PortableServer.POA poa ) {
      this._impl = delegate;
      this._poa      = poa;
  }
  public org.basic.net.c16_corba.poa.fallTIE.FileInterfaceOperations _delegate() {
      return this._impl;
  }
  public void _delegate (org.basic.net.c16_corba.poa.fallTIE.FileInterfaceOperations delegate ) {
      this._impl = delegate;
  }
  public org.omg.PortableServer.POA _default_POA() {
      if(_poa != null) {
          return _poa;
      }
      else {
          return super._default_POA();
      }
  }
  public org.basic.net.c16_corba.poa.fallTIE.FileInterfacePackage.Data[] downloadFile (String fileName)
  {
    return _impl.downloadFile(fileName);
  } // downloadFile

  public org.basic.net.c16_corba.poa.fallTIE.entity.Student sayHi (String user)
  {
    return _impl.sayHi(user);
  } // sayHi

  private org.basic.net.c16_corba.poa.fallTIE.FileInterfaceOperations _impl;
  private org.omg.PortableServer.POA _poa;

} // class FileInterfacePOATie
