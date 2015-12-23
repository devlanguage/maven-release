package org.basic.net.c16_corba.oldImplBase.fall;


/**
* org/basic/corba/poa/fall/_FileInterfaceImplBase.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ���
* ���� FileServer.idl
* 2010��8��2�� ����һ ����06ʱ00��05�� CST
*/

public abstract class _FileInterfaceImplBase extends org.omg.CORBA.portable.ObjectImpl
                implements org.basic.net.c16_corba.oldImplBase.fall.FileInterface, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors
  public _FileInterfaceImplBase ()
  {
  }

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("downloadFile", new java.lang.Integer (0));
    _methods.put ("sayHi", new java.lang.Integer (1));
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
       case 0:  // org/basic/corba/poa/fall/FileInterface/downloadFile
       {
         String fileName = in.read_string ();
         org.basic.net.c16_corba.oldImplBase.fall.FileInterfacePackage.Data $result[] = null;
         $result = this.downloadFile (fileName);
         out = $rh.createReply();
         org.basic.net.c16_corba.oldImplBase.fall.FileInterfacePackage.DataListHelper.write (out, $result);
         break;
       }

       case 1:  // org/basic/corba/poa/fall/FileInterface/sayHi
       {
         String user = in.read_string ();
         org.basic.net.c16_corba.oldImplBase.fall.entity.Student $result = null;
         $result = this.sayHi (user);
         out = $rh.createReply();
         org.basic.net.c16_corba.oldImplBase.fall.entity.StudentHelper.write (out, $result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:org/basic/corba/poa/fall/FileInterface:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }


} // class _FileInterfaceImplBase
