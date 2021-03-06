package org.basic.net.c16_corba.hello.hellostub;


/**
* org/basic/corba/hello/hellostub/GoodDayPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from hello.idl
* Monday, April 1, 2013 10:51:40 AM CST
*/

public abstract class GoodDayPOA extends org.omg.PortableServer.Servant
 implements org.basic.net.c16_corba.hello.hellostub.GoodDayOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("_get_helloSimple", new java.lang.Integer (0));
    _methods.put ("helloWide", new java.lang.Integer (1));
    _methods.put ("helloWideException", new java.lang.Integer (2));
    _methods.put ("lock", new java.lang.Integer (3));
    _methods.put ("ping", new java.lang.Integer (4));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // org/basic/corba/hello/hellostub/GoodDay/_get_helloSimple
       {
         String $result = null;
         $result = this.helloSimple ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }


  //float balance ();
       case 1:  // org/basic/corba/hello/hellostub/GoodDay/helloWide
       {
         String msg = in.read_wstring ();
         String $result = null;
         $result = this.helloWide (msg);
         out = $rh.createReply();
         out.write_wstring ($result);
         break;
       }

       case 2:  // org/basic/corba/hello/hellostub/GoodDay/helloWideException
       {
         try {
           String msg = in.read_wstring ();
           String $result = null;
           $result = this.helloWideException (msg);
           out = $rh.createReply();
           out.write_string ($result);
         } catch (org.basic.net.c16_corba.hello.hellostub.InternalException $ex) {
           out = $rh.createExceptionReply ();
           org.basic.net.c16_corba.hello.hellostub.InternalExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 3:  // org/basic/corba/hello/hellostub/GoodDay/lock
       {
         this.lock ();
         out = $rh.createReply();
         break;
       }

       case 4:  // org/basic/corba/hello/hellostub/GoodDay/ping
       {
         String hello = in.read_string ();
         boolean $result = false;
         $result = this.ping (hello);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:org/basic/corba/hello/hellostub/GoodDay:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public GoodDay _this() 
  {
    return GoodDayHelper.narrow(
    super._this_object());
  }

  public GoodDay _this(org.omg.CORBA.ORB orb) 
  {
    return GoodDayHelper.narrow(
    super._this_object(orb));
  }


} // class GoodDayPOA
