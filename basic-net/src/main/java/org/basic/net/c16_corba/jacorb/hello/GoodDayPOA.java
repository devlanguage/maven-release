package org.basic.net.c16_corba.jacorb.hello;


/**
 * Generated from IDL interface "GoodDay".
 *
 * @author JacORB IDL compiler V 2.3.0, 17-Feb-2007
 * @version generated at Jun 15, 2011 2:32:19 PM
 */

public abstract class GoodDayPOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, org.basic.net.c16_corba.jacorb.hello.GoodDayOperations
{
	static private final java.util.Hashtable m_opsHash = new java.util.Hashtable();
	static
	{
		m_opsHash.put ( "hello_latin1", new java.lang.Integer(0));
		m_opsHash.put ( "hello_chinese", new java.lang.Integer(1));
	}
	private String[] ids = {"IDL:demo/hello/GoodDay:1.0"};
	public org.basic.net.c16_corba.jacorb.hello.GoodDay _this()
	{
		return org.basic.net.c16_corba.jacorb.hello.GoodDayHelper.narrow(_this_object());
	}
	public org.basic.net.c16_corba.jacorb.hello.GoodDay _this(org.omg.CORBA.ORB orb)
	{
		return org.basic.net.c16_corba.jacorb.hello.GoodDayHelper.narrow(_this_object(orb));
	}
	public org.omg.CORBA.portable.OutputStream _invoke(String method, org.omg.CORBA.portable.InputStream _input, org.omg.CORBA.portable.ResponseHandler handler)
		throws org.omg.CORBA.SystemException
	{
		org.omg.CORBA.portable.OutputStream _out = null;
		// do something
		// quick lookup of operation
		java.lang.Integer opsIndex = (java.lang.Integer)m_opsHash.get ( method );
		if ( null == opsIndex )
			throw new org.omg.CORBA.BAD_OPERATION(method + " not found");
		switch ( opsIndex.intValue() )
		{
			case 0: // hello_latin1
			{
				_out = handler.createReply();
				_out.write_string(hello_latin1());
				break;
			}
			case 1: // hello_chinese
			{
				_out = handler.createReply();
				_out.write_wstring(hello_chinese());
				break;
			}
		}
		return _out;
	}

	public String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] obj_id)
	{
		return ids;
	}
}
