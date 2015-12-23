package org.basic.net.c16_corba.oldImplBase.fall.entity;


/**
* org/basic/corba/poa/fall/entity/FontStyles.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ���
* ���� FileServer.idl
* 2010��8��2�� ����һ ����06ʱ00��05�� CST
*/

public class FontStyles implements org.omg.CORBA.portable.IDLEntity
{
  private        int __value;
  private static int __size = 4;
  private static org.basic.net.c16_corba.oldImplBase.fall.entity.FontStyles[] __array = new org.basic.net.c16_corba.oldImplBase.fall.entity.FontStyles [__size];

  public static final int _Plain = 0;
  public static final org.basic.net.c16_corba.oldImplBase.fall.entity.FontStyles Plain = new org.basic.net.c16_corba.oldImplBase.fall.entity.FontStyles(_Plain);
  public static final int _Bold = 1;
  public static final org.basic.net.c16_corba.oldImplBase.fall.entity.FontStyles Bold = new org.basic.net.c16_corba.oldImplBase.fall.entity.FontStyles(_Bold);
  public static final int _Italic = 2;
  public static final org.basic.net.c16_corba.oldImplBase.fall.entity.FontStyles Italic = new org.basic.net.c16_corba.oldImplBase.fall.entity.FontStyles(_Italic);
  public static final int _BoldItalic = 3;
  public static final org.basic.net.c16_corba.oldImplBase.fall.entity.FontStyles BoldItalic = new org.basic.net.c16_corba.oldImplBase.fall.entity.FontStyles(_BoldItalic);

  public int value ()
  {
    return __value;
  }

  public static org.basic.net.c16_corba.oldImplBase.fall.entity.FontStyles from_int (int value)
  {
    if (value >= 0 && value < __size)
      return __array[value];
    else
      throw new org.omg.CORBA.BAD_PARAM ();
  }

  protected FontStyles (int value)
  {
    __value = value;
    __array[__value] = this;
  }
} // class FontStyles
