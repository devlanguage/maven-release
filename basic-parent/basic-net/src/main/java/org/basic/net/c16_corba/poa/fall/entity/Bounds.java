package org.basic.net.c16_corba.poa.fall.entity;


/**
* org/basic/corba/poa/fall/entity/Bounds.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ���
* ���� FileServer.idl
* 2010��8��2�� ����һ ����06ʱ02��55�� CST
*/

public final class Bounds implements org.omg.CORBA.portable.IDLEntity
{
  public double left = (double)0;
  public double top = (double)0;
  public double right = (double)0;
  public double bottom = (double)0;

  public Bounds ()
  {
  } // ctor

  public Bounds (double _left, double _top, double _right, double _bottom)
  {
    left = _left;
    top = _top;
    right = _right;
    bottom = _bottom;
  } // ctor

} // class Bounds
