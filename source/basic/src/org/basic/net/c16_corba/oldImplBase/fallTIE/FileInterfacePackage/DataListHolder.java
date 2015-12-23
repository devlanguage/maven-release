package org.basic.net.c16_corba.oldImplBase.fallTIE.FileInterfacePackage;


/**
* org/basic/corba/poa/fallTIE/FileInterfacePackage/DataListHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ���
* ���� FileServer.idl
* 2010��8��2�� ����һ ����06ʱ00��43�� CST
*/

public final class DataListHolder implements org.omg.CORBA.portable.Streamable
{
  public org.basic.net.c16_corba.oldImplBase.fallTIE.FileInterfacePackage.Data value[] = null;

  public DataListHolder ()
  {
  }

  public DataListHolder (org.basic.net.c16_corba.oldImplBase.fallTIE.FileInterfacePackage.Data[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.basic.net.c16_corba.oldImplBase.fallTIE.FileInterfacePackage.DataListHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.basic.net.c16_corba.oldImplBase.fallTIE.FileInterfacePackage.DataListHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.basic.net.c16_corba.oldImplBase.fallTIE.FileInterfacePackage.DataListHelper.type ();
  }

}
