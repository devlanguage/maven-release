package org.basic.net.c16_corba.poa.fallTIE.entity;


/**
* org/basic/corba/poa/fallTIE/entity/IconSizes.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ���
* ���� FileServer.idl
* 2010��8��2�� ����һ ����06ʱ01��55�� CST
*/

public class IconSizes implements org.omg.CORBA.portable.IDLEntity
{
  private        int __value;
  private static int __size = 4;
  private static org.basic.net.c16_corba.poa.fallTIE.entity.IconSizes[] __array = new org.basic.net.c16_corba.poa.fallTIE.entity.IconSizes [__size];

  public static final int _Micro = 0;
  public static final org.basic.net.c16_corba.poa.fallTIE.entity.IconSizes Micro = new org.basic.net.c16_corba.poa.fallTIE.entity.IconSizes(_Micro);
  public static final int _Small = 1;
  public static final org.basic.net.c16_corba.poa.fallTIE.entity.IconSizes Small = new org.basic.net.c16_corba.poa.fallTIE.entity.IconSizes(_Small);
  public static final int _Medium = 2;
  public static final org.basic.net.c16_corba.poa.fallTIE.entity.IconSizes Medium = new org.basic.net.c16_corba.poa.fallTIE.entity.IconSizes(_Medium);
  public static final int _Large = 3;
  public static final org.basic.net.c16_corba.poa.fallTIE.entity.IconSizes Large = new org.basic.net.c16_corba.poa.fallTIE.entity.IconSizes(_Large);

  public int value ()
  {
    return __value;
  }

  public static org.basic.net.c16_corba.poa.fallTIE.entity.IconSizes from_int (int value)
  {
    if (value >= 0 && value < __size)
      return __array[value];
    else
      throw new org.omg.CORBA.BAD_PARAM ();
  }

  protected IconSizes (int value)
  {
    __value = value;
    __array[__value] = this;
  }
} // class IconSizes
