package org.basic.net.c16_corba.poa.fall.entity;


/**
* org/basic/corba/poa/fall/entity/IconFontSizes.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ���
* ���� FileServer.idl
* 2010��8��2�� ����һ ����06ʱ02��55�� CST
*/

public class IconFontSizes implements org.omg.CORBA.portable.IDLEntity
{
  private        int __value;
  private static int __size = 4;
  private static org.basic.net.c16_corba.poa.fall.entity.IconFontSizes[] __array = new org.basic.net.c16_corba.poa.fall.entity.IconFontSizes [__size];

  public static final int _NoLabel = 0;
  public static final org.basic.net.c16_corba.poa.fall.entity.IconFontSizes NoLabel = new org.basic.net.c16_corba.poa.fall.entity.IconFontSizes(_NoLabel);
  public static final int _SmallFont = 1;
  public static final org.basic.net.c16_corba.poa.fall.entity.IconFontSizes SmallFont = new org.basic.net.c16_corba.poa.fall.entity.IconFontSizes(_SmallFont);
  public static final int _MediumFont = 2;
  public static final org.basic.net.c16_corba.poa.fall.entity.IconFontSizes MediumFont = new org.basic.net.c16_corba.poa.fall.entity.IconFontSizes(_MediumFont);
  public static final int _LargeFont = 3;
  public static final org.basic.net.c16_corba.poa.fall.entity.IconFontSizes LargeFont = new org.basic.net.c16_corba.poa.fall.entity.IconFontSizes(_LargeFont);

  public int value ()
  {
    return __value;
  }

  public static org.basic.net.c16_corba.poa.fall.entity.IconFontSizes from_int (int value)
  {
    if (value >= 0 && value < __size)
      return __array[value];
    else
      throw new org.omg.CORBA.BAD_PARAM ();
  }

  protected IconFontSizes (int value)
  {
    __value = value;
    __array[__value] = this;
  }
} // class IconFontSizes
