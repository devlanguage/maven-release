package org.basic.net.c16_corba.oldImplBase.fall.FileInterfacePackage;


/**
* org/basic/corba/poa/fall/FileInterfacePackage/DataListHelper.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ���
* ���� FileServer.idl
* 2010��8��2�� ����һ ����06ʱ00��05�� CST
*/

abstract public class DataListHelper
{
  private static String  _id = "IDL:org/basic/corba/poa/fall/FileInterface/DataList:1.0";

  public static void insert (org.omg.CORBA.Any a, org.basic.net.c16_corba.oldImplBase.fall.FileInterfacePackage.Data[] that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static org.basic.net.c16_corba.oldImplBase.fall.FileInterfacePackage.Data[] extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.basic.net.c16_corba.oldImplBase.fall.FileInterfacePackage.DataHelper.type ();
      __typeCode = org.omg.CORBA.ORB.init ().create_sequence_tc (0, __typeCode);
      __typeCode = org.omg.CORBA.ORB.init ().create_alias_tc (org.basic.net.c16_corba.oldImplBase.fall.FileInterfacePackage.DataListHelper.id (), "DataList", __typeCode);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static org.basic.net.c16_corba.oldImplBase.fall.FileInterfacePackage.Data[] read (org.omg.CORBA.portable.InputStream istream)
  {
    org.basic.net.c16_corba.oldImplBase.fall.FileInterfacePackage.Data value[] = null;
    int _len0 = istream.read_long ();
    value = new org.basic.net.c16_corba.oldImplBase.fall.FileInterfacePackage.Data[_len0];
    for (int _o1 = 0;_o1 < value.length; ++_o1)
      value[_o1] = org.basic.net.c16_corba.oldImplBase.fall.FileInterfacePackage.DataHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, org.basic.net.c16_corba.oldImplBase.fall.FileInterfacePackage.Data[] value)
  {
    ostream.write_long (value.length);
    for (int _i0 = 0;_i0 < value.length; ++_i0)
      org.basic.net.c16_corba.oldImplBase.fall.FileInterfacePackage.DataHelper.write (ostream, value[_i0]);
  }

}
