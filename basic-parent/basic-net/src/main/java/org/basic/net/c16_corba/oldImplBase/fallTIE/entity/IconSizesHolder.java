package org.basic.net.c16_corba.oldImplBase.fallTIE.entity;

/**
* org/basic/corba/poa/fallTIE/entity/IconSizesHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ���
* ���� FileServer.idl
* 2010��8��2�� ����һ ����06ʱ00��43�� CST
*/

public final class IconSizesHolder implements org.omg.CORBA.portable.Streamable
{
  public org.basic.net.c16_corba.oldImplBase.fallTIE.entity.IconSizes value = null;

  public IconSizesHolder ()
  {
  }

  public IconSizesHolder (org.basic.net.c16_corba.oldImplBase.fallTIE.entity.IconSizes initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.basic.net.c16_corba.oldImplBase.fallTIE.entity.IconSizesHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.basic.net.c16_corba.oldImplBase.fallTIE.entity.IconSizesHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.basic.net.c16_corba.oldImplBase.fallTIE.entity.IconSizesHelper.type ();
  }

}
