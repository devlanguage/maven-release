package org.basic.net.c16_corba.oldImplBase.fall.FileInterfacePackage;


/**
* org/basic/corba/poa/fall/FileInterfacePackage/DataHelper.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ���
* ���� FileServer.idl
* 2010��8��2�� ����һ ����06ʱ00��05�� CST
*/

abstract public class DataHelper
{
  private static String  _id = "IDL:org/basic/corba/poa/fall/FileInterface/Data:1.0";

  public static void insert (org.omg.CORBA.Any a, org.basic.net.c16_corba.oldImplBase.fall.FileInterfacePackage.Data that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static org.basic.net.c16_corba.oldImplBase.fall.FileInterfacePackage.Data extract (org.omg.CORBA.Any a)
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
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [11];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_long);
          _members0[0] = new org.omg.CORBA.StructMember (
            "viewId",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_double);
          _members0[1] = new org.omg.CORBA.StructMember (
            "overlap",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[2] = new org.omg.CORBA.StructMember (
            "name",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_boolean);
          _members0[3] = new org.omg.CORBA.StructMember (
            "filterLabels",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_boolean);
          _members0[4] = new org.omg.CORBA.StructMember (
            "systemView",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_boolean);
          _members0[5] = new org.omg.CORBA.StructMember (
            "overwrite",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_boolean);
          _members0[6] = new org.omg.CORBA.StructMember (
            "deleteThis",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.basic.net.c16_corba.oldImplBase.fall.entity.BoundsHelper.type ();
          _members0[7] = new org.omg.CORBA.StructMember (
            "viewExtent",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.basic.net.c16_corba.oldImplBase.fall.entity.IconSizesHelper.type ();
          _members0[8] = new org.omg.CORBA.StructMember (
            "iconSize",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.basic.net.c16_corba.oldImplBase.fall.entity.IconFontSizesHelper.type ();
          _members0[9] = new org.omg.CORBA.StructMember (
            "iconFontSize",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_sequence_tc (0, _tcOf_members0);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_alias_tc (org.basic.net.c16_corba.oldImplBase.fall.entity.StringListHelper.id (), "StringList", _tcOf_members0);
          _members0[10] = new org.omg.CORBA.StructMember (
            "geoEntities",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (org.basic.net.c16_corba.oldImplBase.fall.FileInterfacePackage.DataHelper.id (), "Data", _members0);
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

  public static org.basic.net.c16_corba.oldImplBase.fall.FileInterfacePackage.Data read (org.omg.CORBA.portable.InputStream istream)
  {
    org.basic.net.c16_corba.oldImplBase.fall.FileInterfacePackage.Data value = new org.basic.net.c16_corba.oldImplBase.fall.FileInterfacePackage.Data ();
    value.viewId = istream.read_long ();
    value.overlap = istream.read_double ();
    value.name = istream.read_string ();
    value.filterLabels = istream.read_boolean ();
    value.systemView = istream.read_boolean ();
    value.overwrite = istream.read_boolean ();
    value.deleteThis = istream.read_boolean ();
    value.viewExtent = org.basic.net.c16_corba.oldImplBase.fall.entity.BoundsHelper.read (istream);
    value.iconSize = org.basic.net.c16_corba.oldImplBase.fall.entity.IconSizesHelper.read (istream);
    value.iconFontSize = org.basic.net.c16_corba.oldImplBase.fall.entity.IconFontSizesHelper.read (istream);
    value.geoEntities = org.basic.net.c16_corba.oldImplBase.fall.entity.StringListHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, org.basic.net.c16_corba.oldImplBase.fall.FileInterfacePackage.Data value)
  {
    ostream.write_long (value.viewId);
    ostream.write_double (value.overlap);
    ostream.write_string (value.name);
    ostream.write_boolean (value.filterLabels);
    ostream.write_boolean (value.systemView);
    ostream.write_boolean (value.overwrite);
    ostream.write_boolean (value.deleteThis);
    org.basic.net.c16_corba.oldImplBase.fall.entity.BoundsHelper.write (ostream, value.viewExtent);
    org.basic.net.c16_corba.oldImplBase.fall.entity.IconSizesHelper.write (ostream, value.iconSize);
    org.basic.net.c16_corba.oldImplBase.fall.entity.IconFontSizesHelper.write (ostream, value.iconFontSize);
    org.basic.net.c16_corba.oldImplBase.fall.entity.StringListHelper.write (ostream, value.geoEntities);
  }

}
