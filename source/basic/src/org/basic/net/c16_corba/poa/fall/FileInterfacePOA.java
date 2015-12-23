package org.basic.net.c16_corba.poa.fall;


/**
* org/basic/corba/poa/fall/FileInterfacePOA.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ���
* ���� FileServer.idl
* 2010��8��2�� ����һ ����06ʱ02��55�� CST
*/

public abstract class FileInterfacePOA extends org.omg.PortableServer.Servant
 implements org.basic.net.c16_corba.poa.fall.FileInterfaceOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

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
         org.basic.net.c16_corba.poa.fall.FileInterfacePackage.Data $result[] = null;
         $result = this.downloadFile (fileName);
         out = $rh.createReply();
         org.basic.net.c16_corba.poa.fall.FileInterfacePackage.DataListHelper.write (out, $result);
         break;
       }

       case 1:  // org/basic/corba/poa/fall/FileInterface/sayHi
       {
         String user = in.read_string ();
         org.basic.net.c16_corba.poa.fall.entity.Student $result = null;
         $result = this.sayHi (user);
         out = $rh.createReply();
         org.basic.net.c16_corba.poa.fall.entity.StudentHelper.write (out, $result);
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

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public FileInterface _this() 
  {
    return FileInterfaceHelper.narrow(
    super._this_object());
  }

  public FileInterface _this(org.omg.CORBA.ORB orb) 
  {
    return FileInterfaceHelper.narrow(
    super._this_object(orb));
  }


} // class FileInterfacePOA
