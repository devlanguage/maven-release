package org.basic.net.c16_corba.poa.fallTIE.entity;

/**
* org/basic/corba/poa/fallTIE/entity/FontStylesHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ���
* ���� FileServer.idl
* 2010��8��2�� ����һ ����06ʱ01��55�� CST
*/

public final class FontStylesHolder implements org.omg.CORBA.portable.Streamable
{
  public org.basic.net.c16_corba.poa.fallTIE.entity.FontStyles value = null;

  public FontStylesHolder ()
  {
  }

  public FontStylesHolder (org.basic.net.c16_corba.poa.fallTIE.entity.FontStyles initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.basic.net.c16_corba.poa.fallTIE.entity.FontStylesHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.basic.net.c16_corba.poa.fallTIE.entity.FontStylesHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.basic.net.c16_corba.poa.fallTIE.entity.FontStylesHelper.type ();
  }

}
