package org.basic.net.c16_corba.oldImplBase.fallTIE.entity;


/**
* org/basic/corba/poa/fallTIE/entity/StudentHelper.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ���
* ���� FileServer.idl
* 2010��8��2�� ����һ ����06ʱ00��43�� CST
*/

abstract public class StudentHelper
{
  private static String  _id = "IDL:org/basic/corba/poa/fallTIE/entity/Student:1.0";

  public static void insert (org.omg.CORBA.Any a, org.basic.net.c16_corba.oldImplBase.fallTIE.entity.Student that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static org.basic.net.c16_corba.oldImplBase.fallTIE.entity.Student extract (org.omg.CORBA.Any a)
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
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [3];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[0] = new org.omg.CORBA.StructMember (
            "stuName",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.basic.net.c16_corba.oldImplBase.fallTIE.entity.IconSizesHelper.type ();
          _members0[1] = new org.omg.CORBA.StructMember (
            "iconSize",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.basic.net.c16_corba.oldImplBase.fallTIE.entity.SexHelper.type ();
          _members0[2] = new org.omg.CORBA.StructMember (
            "sex",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (org.basic.net.c16_corba.oldImplBase.fallTIE.entity.StudentHelper.id (), "Student", _members0);
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

  public static org.basic.net.c16_corba.oldImplBase.fallTIE.entity.Student read (org.omg.CORBA.portable.InputStream istream)
  {
    org.basic.net.c16_corba.oldImplBase.fallTIE.entity.Student value = new org.basic.net.c16_corba.oldImplBase.fallTIE.entity.Student ();
    value.stuName = istream.read_string ();
    value.iconSize = org.basic.net.c16_corba.oldImplBase.fallTIE.entity.IconSizesHelper.read (istream);
    value.sex = org.basic.net.c16_corba.oldImplBase.fallTIE.entity.SexHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, org.basic.net.c16_corba.oldImplBase.fallTIE.entity.Student value)
  {
    ostream.write_string (value.stuName);
    org.basic.net.c16_corba.oldImplBase.fallTIE.entity.IconSizesHelper.write (ostream, value.iconSize);
    org.basic.net.c16_corba.oldImplBase.fallTIE.entity.SexHelper.write (ostream, value.sex);
  }

}
