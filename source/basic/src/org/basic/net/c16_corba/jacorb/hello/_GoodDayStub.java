package org.basic.net.c16_corba.jacorb.hello;


/**
 * Generated from IDL interface "GoodDay".
 *
 * @author JacORB IDL compiler V 2.3.0, 17-Feb-2007
 * @version generated at Jun 15, 2011 2:32:19 PM
 */

public class _GoodDayStub
	extends org.omg.CORBA.portable.ObjectImpl
	implements org.basic.net.c16_corba.jacorb.hello.GoodDay
{
	private String[] ids = {"IDL:demo/hello/GoodDay:1.0"};
	public String[] _ids()
	{
		return ids;
	}

	public final static java.lang.Class _opsClass = org.basic.net.c16_corba.jacorb.hello.GoodDayOperations.class;
	public java.lang.String hello_latin1()
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "hello_latin1", true);
				_is = _invoke(_os);
				java.lang.String _result = _is.read_string();
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "hello_latin1", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			GoodDayOperations _localServant = (GoodDayOperations)_so.servant;
			java.lang.String _result;
			try
			{
				_result = _localServant.hello_latin1();
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

	public java.lang.String hello_chinese()
	{
		while(true)
		{
		if(! this._is_local())
		{
			org.omg.CORBA.portable.InputStream _is = null;
			try
			{
				org.omg.CORBA.portable.OutputStream _os = _request( "hello_chinese", true);
				_is = _invoke(_os);
				java.lang.String _result = _is.read_wstring();
				return _result;
			}
			catch( org.omg.CORBA.portable.RemarshalException _rx ){}
			catch( org.omg.CORBA.portable.ApplicationException _ax )
			{
				String _id = _ax.getId();
				throw new RuntimeException("Unexpected exception " + _id );
			}
			finally
			{
				this._releaseReply(_is);
			}
		}
		else
		{
			org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke( "hello_chinese", _opsClass );
			if( _so == null )
				throw new org.omg.CORBA.UNKNOWN("local invocations not supported!");
			GoodDayOperations _localServant = (GoodDayOperations)_so.servant;
			java.lang.String _result;
			try
			{
				_result = _localServant.hello_chinese();
			}
			finally
			{
				_servant_postinvoke(_so);
			}
			return _result;
		}

		}

	}

}
