package org.basic.net.c16_corba.oldImplBase.fall.entity;


/**
* org/basic/corba/poa/fall/entity/Student.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ���
* ���� FileServer.idl
* 2010��8��2�� ����һ ����06ʱ00��05�� CST
*/

public final class Student implements org.omg.CORBA.portable.IDLEntity
{
  public String stuName = null;
  public org.basic.net.c16_corba.oldImplBase.fall.entity.IconSizes iconSize = null;
  public org.basic.net.c16_corba.oldImplBase.fall.entity.Sex sex = null;

  public Student ()
  {
  } // ctor

  public Student (String _stuName, org.basic.net.c16_corba.oldImplBase.fall.entity.IconSizes _iconSize, org.basic.net.c16_corba.oldImplBase.fall.entity.Sex _sex)
  {
    stuName = _stuName;
    iconSize = _iconSize;
    sex = _sex;
  } // ctor

} // class Student
