package org.basic.net.c16_corba.oldImplBase.fallTIE.entity;

/**
* org/basic/corba/poa/fallTIE/entity/ViewAssocTypeHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ���
* ���� FileServer.idl
* 2010��8��2�� ����һ ����06ʱ00��43�� CST
*/

public final class ViewAssocTypeHolder implements org.omg.CORBA.portable.Streamable
{
  public org.basic.net.c16_corba.oldImplBase.fallTIE.entity.ViewAssocType value = null;

  public ViewAssocTypeHolder ()
  {
  }

  public ViewAssocTypeHolder (org.basic.net.c16_corba.oldImplBase.fallTIE.entity.ViewAssocType initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.basic.net.c16_corba.oldImplBase.fallTIE.entity.ViewAssocTypeHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.basic.net.c16_corba.oldImplBase.fallTIE.entity.ViewAssocTypeHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.basic.net.c16_corba.oldImplBase.fallTIE.entity.ViewAssocTypeHelper.type ();
  }

}
