package org.basic.net.c16_corba.oldImplBase.fallTIE.entity;


/**
* org/basic/corba/poa/fallTIE/entity/BoundsHelper.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ���
* ���� FileServer.idl
* 2010��8��2�� ����һ ����06ʱ00��43�� CST
*/

abstract public class BoundsHelper
{
  private static String  _id = "IDL:org/basic/corba/poa/fallTIE/entity/Bounds:1.0";

  public static void insert (org.omg.CORBA.Any a, org.basic.net.c16_corba.oldImplBase.fallTIE.entity.Bounds that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static org.basic.net.c16_corba.oldImplBase.fallTIE.entity.Bounds extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  private static boolean __active = false;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      synchronized (org.omg.CORBA.TypeCode.class)
      {
        if (__typeCode == null)
        {
          if (__active)
          {
            return org.omg.CORBA.ORB.init().create_recursive_tc ( _id );
          }
          __active = true;
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [4];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_double);
          _members0[0] = new org.omg.CORBA.StructMember (
            "left",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_double);
          _members0[1] = new org.omg.CORBA.StructMember (
            "top",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_double);
          _members0[2] = new org.omg.CORBA.StructMember (
            "right",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_double);
          _members0[3] = new org.omg.CORBA.StructMember (
            "bottom",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (org.basic.net.c16_corba.oldImplBase.fallTIE.entity.BoundsHelper.id (), "Bounds", _members0);
          __active = false;
        }
      }
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static org.basic.net.c16_corba.oldImplBase.fallTIE.entity.Bounds read (org.omg.CORBA.portable.InputStream istream)
  {
    org.basic.net.c16_corba.oldImplBase.fallTIE.entity.Bounds value = new org.basic.net.c16_corba.oldImplBase.fallTIE.entity.Bounds ();
    value.left = istream.read_double ();
    value.top = istream.read_double ();
    value.right = istream.read_double ();
    value.bottom = istream.read_double ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, org.basic.net.c16_corba.oldImplBase.fallTIE.entity.Bounds value)
  {
    ostream.write_double (value.left);
    ostream.write_double (value.top);
    ostream.write_double (value.right);
    ostream.write_double (value.bottom);
  }

}
