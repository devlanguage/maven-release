package org.basic.net.c16_corba.poa.fall.FileInterfacePackage;


/**
* org/basic/corba/poa/fall/FileInterfacePackage/DataListHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ���
* ���� FileServer.idl
* 2010��8��2�� ����һ ����06ʱ02��55�� CST
*/

public final class DataListHolder implements org.omg.CORBA.portable.Streamable
{
  public org.basic.net.c16_corba.poa.fall.FileInterfacePackage.Data value[] = null;

  public DataListHolder ()
  {
  }

  public DataListHolder (org.basic.net.c16_corba.poa.fall.FileInterfacePackage.Data[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.basic.net.c16_corba.poa.fall.FileInterfacePackage.DataListHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.basic.net.c16_corba.poa.fall.FileInterfacePackage.DataListHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.basic.net.c16_corba.poa.fall.FileInterfacePackage.DataListHelper.type ();
  }

}
