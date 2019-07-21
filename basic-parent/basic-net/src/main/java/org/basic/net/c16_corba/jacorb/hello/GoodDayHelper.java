package org.basic.net.c16_corba.jacorb.hello;


/**
 * Generated from IDL interface "GoodDay".
 *
 * @author JacORB IDL compiler V 2.3.0, 17-Feb-2007
 * @version generated at Jun 15, 2011 2:32:19 PM
 */

public final class GoodDayHelper
{
	public static void insert (final org.omg.CORBA.Any any, final org.basic.net.c16_corba.jacorb.hello.GoodDay s)
	{
			any.insert_Object(s);
	}
	public static org.basic.net.c16_corba.jacorb.hello.GoodDay extract(final org.omg.CORBA.Any any)
	{
		return narrow(any.extract_Object()) ;
	}
	public static org.omg.CORBA.TypeCode type()
	{
		return org.omg.CORBA.ORB.init().create_interface_tc("IDL:demo/hello/GoodDay:1.0", "GoodDay");
	}
	public static String id()
	{
		return "IDL:demo/hello/GoodDay:1.0";
	}
	public static GoodDay read(final org.omg.CORBA.portable.InputStream in)
	{
		return narrow(in.read_Object(org.basic.net.c16_corba.jacorb.hello._GoodDayStub.class));
	}
	public static void write(final org.omg.CORBA.portable.OutputStream _out, final org.basic.net.c16_corba.jacorb.hello.GoodDay s)
	{
		_out.write_Object(s);
	}
	public static org.basic.net.c16_corba.jacorb.hello.GoodDay narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof org.basic.net.c16_corba.jacorb.hello.GoodDay)
		{
			return (org.basic.net.c16_corba.jacorb.hello.GoodDay)obj;
		}
		else if (obj._is_a("IDL:demo/hello/GoodDay:1.0"))
		{
			org.basic.net.c16_corba.jacorb.hello._GoodDayStub stub;
			stub = new org.basic.net.c16_corba.jacorb.hello._GoodDayStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
		else
		{
			throw new org.omg.CORBA.BAD_PARAM("Narrow failed");
		}
	}
	public static org.basic.net.c16_corba.jacorb.hello.GoodDay unchecked_narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof org.basic.net.c16_corba.jacorb.hello.GoodDay)
		{
			return (org.basic.net.c16_corba.jacorb.hello.GoodDay)obj;
		}
		else
		{
			org.basic.net.c16_corba.jacorb.hello._GoodDayStub stub;
			stub = new org.basic.net.c16_corba.jacorb.hello._GoodDayStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
	}
}
