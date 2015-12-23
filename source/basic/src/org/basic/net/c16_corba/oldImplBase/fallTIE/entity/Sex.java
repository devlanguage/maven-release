package org.basic.net.c16_corba.oldImplBase.fallTIE.entity;


/**
* org/basic/corba/poa/fallTIE/entity/Sex.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ���
* ���� FileServer.idl
* 2010��8��2�� ����һ ����06ʱ00��43�� CST
*/

public class Sex implements org.omg.CORBA.portable.IDLEntity
{
  private        int __value;
  private static int __size = 2;
  private static org.basic.net.c16_corba.oldImplBase.fallTIE.entity.Sex[] __array = new org.basic.net.c16_corba.oldImplBase.fallTIE.entity.Sex [__size];

  public static final int _Female = 0;
  public static final org.basic.net.c16_corba.oldImplBase.fallTIE.entity.Sex Female = new org.basic.net.c16_corba.oldImplBase.fallTIE.entity.Sex(_Female);
  public static final int _Male = 1;
  public static final org.basic.net.c16_corba.oldImplBase.fallTIE.entity.Sex Male = new org.basic.net.c16_corba.oldImplBase.fallTIE.entity.Sex(_Male);

  public int value ()
  {
    return __value;
  }

  public static org.basic.net.c16_corba.oldImplBase.fallTIE.entity.Sex from_int (int value)
  {
    if (value >= 0 && value < __size)
      return __array[value];
    else
      throw new org.omg.CORBA.BAD_PARAM ();
  }

  protected Sex (int value)
  {
    __value = value;
    __array[__value] = this;
  }
} // class Sex
