package org.basic.net.c16_corba.oldImplBase.fallTIE.entity;


/**
* org/basic/corba/poa/fallTIE/entity/MapLayerIds.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ���
* ���� FileServer.idl
* 2010��8��2�� ����һ ����06ʱ00��43�� CST
*/

public class MapLayerIds implements org.omg.CORBA.portable.IDLEntity
{
  private        int __value;
  private static int __size = 7;
  private static org.basic.net.c16_corba.oldImplBase.fallTIE.entity.MapLayerIds[] __array = new org.basic.net.c16_corba.oldImplBase.fallTIE.entity.MapLayerIds [__size];

  public static final int _World = 0;
  public static final org.basic.net.c16_corba.oldImplBase.fallTIE.entity.MapLayerIds World = new org.basic.net.c16_corba.oldImplBase.fallTIE.entity.MapLayerIds(_World);
  public static final int _State = 1;
  public static final org.basic.net.c16_corba.oldImplBase.fallTIE.entity.MapLayerIds State = new org.basic.net.c16_corba.oldImplBase.fallTIE.entity.MapLayerIds(_State);
  public static final int _InterStateRoads = 2;
  public static final org.basic.net.c16_corba.oldImplBase.fallTIE.entity.MapLayerIds InterStateRoads = new org.basic.net.c16_corba.oldImplBase.fallTIE.entity.MapLayerIds(_InterStateRoads);
  public static final int _StateCounty = 3;
  public static final org.basic.net.c16_corba.oldImplBase.fallTIE.entity.MapLayerIds StateCounty = new org.basic.net.c16_corba.oldImplBase.fallTIE.entity.MapLayerIds(_StateCounty);
  public static final int _MajorCities = 4;
  public static final org.basic.net.c16_corba.oldImplBase.fallTIE.entity.MapLayerIds MajorCities = new org.basic.net.c16_corba.oldImplBase.fallTIE.entity.MapLayerIds(_MajorCities);
  public static final int _StateCities = 5;
  public static final org.basic.net.c16_corba.oldImplBase.fallTIE.entity.MapLayerIds StateCities = new org.basic.net.c16_corba.oldImplBase.fallTIE.entity.MapLayerIds(_StateCities);
  public static final int _StateRoads = 6;
  public static final org.basic.net.c16_corba.oldImplBase.fallTIE.entity.MapLayerIds StateRoads = new org.basic.net.c16_corba.oldImplBase.fallTIE.entity.MapLayerIds(_StateRoads);

  public int value ()
  {
    return __value;
  }

  public static org.basic.net.c16_corba.oldImplBase.fallTIE.entity.MapLayerIds from_int (int value)
  {
    if (value >= 0 && value < __size)
      return __array[value];
    else
      throw new org.omg.CORBA.BAD_PARAM ();
  }

  protected MapLayerIds (int value)
  {
    __value = value;
    __array[__value] = this;
  }
} // class MapLayerIds
