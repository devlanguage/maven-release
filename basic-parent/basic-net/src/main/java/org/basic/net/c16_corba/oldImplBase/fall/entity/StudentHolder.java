package org.basic.net.c16_corba.oldImplBase.fall.entity;

/**
* org/basic/corba/poa/fall/entity/StudentHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ���
* ���� FileServer.idl
* 2010��8��2�� ����һ ����06ʱ00��05�� CST
*/

public final class StudentHolder implements org.omg.CORBA.portable.Streamable
{
  public org.basic.net.c16_corba.oldImplBase.fall.entity.Student value = null;

  public StudentHolder ()
  {
  }

  public StudentHolder (org.basic.net.c16_corba.oldImplBase.fall.entity.Student initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.basic.net.c16_corba.oldImplBase.fall.entity.StudentHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.basic.net.c16_corba.oldImplBase.fall.entity.StudentHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.basic.net.c16_corba.oldImplBase.fall.entity.StudentHelper.type ();
  }

}
