package org.basic.net.c16_corba.oldImplBase.fall.entity;


/**
* org/basic/corba/poa/fall/entity/ViewAssocType.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ���
* ���� FileServer.idl
* 2010��8��2�� ����һ ����06ʱ00��05�� CST
*/

public class ViewAssocType implements org.omg.CORBA.portable.IDLEntity
{
  private        int __value;
  private static int __size = 3;
  private static org.basic.net.c16_corba.oldImplBase.fall.entity.ViewAssocType[] __array = new org.basic.net.c16_corba.oldImplBase.fall.entity.ViewAssocType [__size];

  public static final int _UserAssoc = 0;
  public static final org.basic.net.c16_corba.oldImplBase.fall.entity.ViewAssocType UserAssoc = new org.basic.net.c16_corba.oldImplBase.fall.entity.ViewAssocType(_UserAssoc);
  public static final int _SystemAssoc = 1;
  public static final org.basic.net.c16_corba.oldImplBase.fall.entity.ViewAssocType SystemAssoc = new org.basic.net.c16_corba.oldImplBase.fall.entity.ViewAssocType(_SystemAssoc);
  public static final int _BestFitCoord = 2;
  public static final org.basic.net.c16_corba.oldImplBase.fall.entity.ViewAssocType BestFitCoord = new org.basic.net.c16_corba.oldImplBase.fall.entity.ViewAssocType(_BestFitCoord);

  public int value ()
  {
    return __value;
  }

  public static org.basic.net.c16_corba.oldImplBase.fall.entity.ViewAssocType from_int (int value)
  {
    if (value >= 0 && value < __size)
      return __array[value];
    else
      throw new org.omg.CORBA.BAD_PARAM ();
  }

  protected ViewAssocType (int value)
  {
    __value = value;
    __array[__value] = this;
  }
} // class ViewAssocType
