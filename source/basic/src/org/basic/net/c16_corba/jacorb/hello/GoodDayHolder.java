package org.basic.net.c16_corba.jacorb.hello;

/**
 * Generated from IDL interface "GoodDay".
 *
 * @author JacORB IDL compiler V 2.3.0, 17-Feb-2007
 * @version generated at Jun 15, 2011 2:32:19 PM
 */

public final class GoodDayHolder	implements org.omg.CORBA.portable.Streamable{
	 public GoodDay value;
	public GoodDayHolder()
	{
	}
	public GoodDayHolder (final GoodDay initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return GoodDayHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = GoodDayHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		GoodDayHelper.write (_out,value);
	}
}
