package org.basic.net.c16_corba.poa.fall.entity;

/**
* org/basic/corba/poa/fall/entity/FontStylesHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ���
* ���� FileServer.idl
* 2010��8��2�� ����һ ����06ʱ02��55�� CST
*/

public final class FontStylesHolder implements org.omg.CORBA.portable.Streamable
{
  public org.basic.net.c16_corba.poa.fall.entity.FontStyles value = null;

  public FontStylesHolder ()
  {
  }

  public FontStylesHolder (org.basic.net.c16_corba.poa.fall.entity.FontStyles initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.basic.net.c16_corba.poa.fall.entity.FontStylesHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.basic.net.c16_corba.poa.fall.entity.FontStylesHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.basic.net.c16_corba.poa.fall.entity.FontStylesHelper.type ();
  }

}
