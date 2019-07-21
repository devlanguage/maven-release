package org.basic.net.c16_corba.poa.fallTIE;

/**
* org/basic/corba/poa/fallTIE/FileInterfaceHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ���
* ���� FileServer.idl
* 2010��8��2�� ����һ ����06ʱ01��55�� CST
*/

public final class FileInterfaceHolder implements org.omg.CORBA.portable.Streamable
{
  public org.basic.net.c16_corba.poa.fallTIE.FileInterface value = null;

  public FileInterfaceHolder ()
  {
  }

  public FileInterfaceHolder (org.basic.net.c16_corba.poa.fallTIE.FileInterface initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.basic.net.c16_corba.poa.fallTIE.FileInterfaceHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.basic.net.c16_corba.poa.fallTIE.FileInterfaceHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.basic.net.c16_corba.poa.fallTIE.FileInterfaceHelper.type ();
  }

}
