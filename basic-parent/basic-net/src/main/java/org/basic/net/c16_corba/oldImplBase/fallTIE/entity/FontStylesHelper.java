package org.basic.net.c16_corba.oldImplBase.fallTIE.entity;


/**
* org/basic/corba/poa/fallTIE/entity/FontStylesHelper.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ���
* ���� FileServer.idl
* 2010��8��2�� ����һ ����06ʱ00��43�� CST
*/

abstract public class FontStylesHelper
{
  private static String  _id = "IDL:org/basic/corba/poa/fallTIE/entity/FontStyles:1.0";

  public static void insert (org.omg.CORBA.Any a, org.basic.net.c16_corba.oldImplBase.fallTIE.entity.FontStyles that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static org.basic.net.c16_corba.oldImplBase.fallTIE.entity.FontStyles extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_enum_tc (org.basic.net.c16_corba.oldImplBase.fallTIE.entity.FontStylesHelper.id (), "FontStyles", new String[] { "Plain", "Bold", "Italic", "BoldItalic"} );
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static org.basic.net.c16_corba.oldImplBase.fallTIE.entity.FontStyles read (org.omg.CORBA.portable.InputStream istream)
  {
    return org.basic.net.c16_corba.oldImplBase.fallTIE.entity.FontStyles.from_int (istream.read_long ());
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, org.basic.net.c16_corba.oldImplBase.fallTIE.entity.FontStyles value)
  {
    ostream.write_long (value.value ());
  }

}
