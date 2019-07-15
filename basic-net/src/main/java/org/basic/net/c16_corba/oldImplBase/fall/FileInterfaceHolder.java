package org.basic.net.c16_corba.oldImplBase.fall;

/**
* org/basic/corba/poa/fall/FileInterfaceHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ���
* ���� FileServer.idl
* 2010��8��2�� ����һ ����06ʱ00��05�� CST
*/

public final class FileInterfaceHolder implements org.omg.CORBA.portable.Streamable
{
  public org.basic.net.c16_corba.oldImplBase.fall.FileInterface value = null;

  public FileInterfaceHolder ()
  {
  }

  public FileInterfaceHolder (org.basic.net.c16_corba.oldImplBase.fall.FileInterface initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.basic.net.c16_corba.oldImplBase.fall.FileInterfaceHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.basic.net.c16_corba.oldImplBase.fall.FileInterfaceHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.basic.net.c16_corba.oldImplBase.fall.FileInterfaceHelper.type ();
  }

}
