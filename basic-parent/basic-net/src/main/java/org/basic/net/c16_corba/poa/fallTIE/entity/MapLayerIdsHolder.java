package org.basic.net.c16_corba.poa.fallTIE.entity;

/**
* org/basic/corba/poa/fallTIE/entity/MapLayerIdsHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ���
* ���� FileServer.idl
* 2010��8��2�� ����һ ����06ʱ01��55�� CST
*/

public final class MapLayerIdsHolder implements org.omg.CORBA.portable.Streamable
{
  public org.basic.net.c16_corba.poa.fallTIE.entity.MapLayerIds value = null;

  public MapLayerIdsHolder ()
  {
  }

  public MapLayerIdsHolder (org.basic.net.c16_corba.poa.fallTIE.entity.MapLayerIds initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.basic.net.c16_corba.poa.fallTIE.entity.MapLayerIdsHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.basic.net.c16_corba.poa.fallTIE.entity.MapLayerIdsHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.basic.net.c16_corba.poa.fallTIE.entity.MapLayerIdsHelper.type ();
  }

}
