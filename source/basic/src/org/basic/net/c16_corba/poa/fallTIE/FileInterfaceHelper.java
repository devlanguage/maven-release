package org.basic.net.c16_corba.poa.fallTIE;


/**
* org/basic/corba/poa/fallTIE/FileInterfaceHelper.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ���
* ���� FileServer.idl
* 2010��8��2�� ����һ ����06ʱ01��55�� CST
*/

abstract public class FileInterfaceHelper
{
  private static String  _id = "IDL:org/basic/corba/poa/fallTIE/FileInterface:1.0";

  public static void insert (org.omg.CORBA.Any a, org.basic.net.c16_corba.poa.fallTIE.FileInterface that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static org.basic.net.c16_corba.poa.fallTIE.FileInterface extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (org.basic.net.c16_corba.poa.fallTIE.FileInterfaceHelper.id (), "FileInterface");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static org.basic.net.c16_corba.poa.fallTIE.FileInterface read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_FileInterfaceStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, org.basic.net.c16_corba.poa.fallTIE.FileInterface value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static org.basic.net.c16_corba.poa.fallTIE.FileInterface narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof org.basic.net.c16_corba.poa.fallTIE.FileInterface)
      return (org.basic.net.c16_corba.poa.fallTIE.FileInterface)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      org.basic.net.c16_corba.poa.fallTIE._FileInterfaceStub stub = new org.basic.net.c16_corba.poa.fallTIE._FileInterfaceStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static org.basic.net.c16_corba.poa.fallTIE.FileInterface unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof org.basic.net.c16_corba.poa.fallTIE.FileInterface)
      return (org.basic.net.c16_corba.poa.fallTIE.FileInterface)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      org.basic.net.c16_corba.poa.fallTIE._FileInterfaceStub stub = new org.basic.net.c16_corba.poa.fallTIE._FileInterfaceStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
