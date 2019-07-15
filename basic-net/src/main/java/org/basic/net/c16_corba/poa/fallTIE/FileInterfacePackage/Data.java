package org.basic.net.c16_corba.poa.fallTIE.FileInterfacePackage;


/**
* org/basic/corba/poa/fallTIE/FileInterfacePackage/Data.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ���
* ���� FileServer.idl
* 2010��8��2�� ����һ ����06ʱ01��55�� CST
*/

public final class Data implements org.omg.CORBA.portable.IDLEntity
{
  public int viewId = (int)0;

  //Numeric Id assigned by database
  public double overlap = (double)0;
  public String name = null;
  public boolean filterLabels = false;
  public boolean systemView = false;
  public boolean overwrite = false;
  public boolean deleteThis = false;
  public org.basic.net.c16_corba.poa.fallTIE.entity.Bounds viewExtent = null;
  public org.basic.net.c16_corba.poa.fallTIE.entity.IconSizes iconSize = null;

  //enum IconSize { Micro, Small, Medium, Large };
  public org.basic.net.c16_corba.poa.fallTIE.entity.IconFontSizes iconFontSize = null;

  //enum IconFontSize { NoLabel, Small, Medium, Large };
  public String geoEntities[] = null;

  public Data ()
  {
  } // ctor

  public Data (int _viewId, double _overlap, String _name, boolean _filterLabels, boolean _systemView, boolean _overwrite, boolean _deleteThis, org.basic.net.c16_corba.poa.fallTIE.entity.Bounds _viewExtent, org.basic.net.c16_corba.poa.fallTIE.entity.IconSizes _iconSize, org.basic.net.c16_corba.poa.fallTIE.entity.IconFontSizes _iconFontSize, String[] _geoEntities)
  {
    viewId = _viewId;
    overlap = _overlap;
    name = _name;
    filterLabels = _filterLabels;
    systemView = _systemView;
    overwrite = _overwrite;
    deleteThis = _deleteThis;
    viewExtent = _viewExtent;
    iconSize = _iconSize;
    iconFontSize = _iconFontSize;
    geoEntities = _geoEntities;
  } // ctor

} // class Data
