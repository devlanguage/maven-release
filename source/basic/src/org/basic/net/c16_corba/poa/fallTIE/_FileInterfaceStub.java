package org.basic.net.c16_corba.poa.fallTIE;


/**
* org/basic/corba/poa/fallTIE/_FileInterfaceStub.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ���
* ���� FileServer.idl
* 2010��8��2�� ����һ ����06ʱ01��55�� CST
*/

public class _FileInterfaceStub extends org.omg.CORBA.portable.ObjectImpl implements org.basic.net.c16_corba.poa.fallTIE.FileInterface
{

  public org.basic.net.c16_corba.poa.fallTIE.FileInterfacePackage.Data[] downloadFile (String fileName)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("downloadFile", true);
                $out.write_string (fileName);
                $in = _invoke ($out);
                org.basic.net.c16_corba.poa.fallTIE.FileInterfacePackage.Data $result[] = org.basic.net.c16_corba.poa.fallTIE.FileInterfacePackage.DataListHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return downloadFile (fileName        );
            } finally {
                _releaseReply ($in);
            }
  } // downloadFile

  public org.basic.net.c16_corba.poa.fallTIE.entity.Student sayHi (String user)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("sayHi", true);
                $out.write_string (user);
                $in = _invoke ($out);
                org.basic.net.c16_corba.poa.fallTIE.entity.Student $result = org.basic.net.c16_corba.poa.fallTIE.entity.StudentHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return sayHi (user        );
            } finally {
                _releaseReply ($in);
            }
  } // sayHi

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:org/basic/corba/poa/fallTIE/FileInterface:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     org.omg.CORBA.Object obj = orb.string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
   } finally {
     orb.destroy() ;
   }
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     String str = orb.object_to_string (this);
     s.writeUTF (str);
   } finally {
     orb.destroy() ;
   }
  }
} // class _FileInterfaceStub
