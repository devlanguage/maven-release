package org.basic.net.c16_corba.jacorb.hello;

import org.omg.PortableServer.POA;

/**
 * Generated from IDL interface "GoodDay".
 *
 * @author JacORB IDL compiler V 2.3.0, 17-Feb-2007
 * @version generated at Jun 15, 2011 2:32:19 PM
 */

public class GoodDayPOATie
	extends GoodDayPOA
{
	private GoodDayOperations _delegate;

	private POA _poa;
	public GoodDayPOATie(GoodDayOperations delegate)
	{
		_delegate = delegate;
	}
	public GoodDayPOATie(GoodDayOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public org.basic.net.c16_corba.jacorb.hello.GoodDay _this()
	{
		return org.basic.net.c16_corba.jacorb.hello.GoodDayHelper.narrow(_this_object());
	}
	public org.basic.net.c16_corba.jacorb.hello.GoodDay _this(org.omg.CORBA.ORB orb)
	{
		return org.basic.net.c16_corba.jacorb.hello.GoodDayHelper.narrow(_this_object(orb));
	}
	public GoodDayOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(GoodDayOperations delegate)
	{
		_delegate = delegate;
	}
	public POA _default_POA()
	{
		if (_poa != null)
		{
			return _poa;
		}
		return super._default_POA();
	}
	public java.lang.String hello_latin1()
	{
		return _delegate.hello_latin1();
	}

	public java.lang.String hello_chinese()
	{
		return _delegate.hello_chinese();
	}

}
