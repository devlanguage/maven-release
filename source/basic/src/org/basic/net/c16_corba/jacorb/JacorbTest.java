package org.basic.net.c16_corba.jacorb;

// List all objects from a NamingService using JacORB 收藏
// Purpose: To list all objects and their IOR within a NamingService.
//
// 1. Write NSWrapper.java
//
// 2. compile NSWrapper.java
//
// 3. run NSWrapper using following command line
//
// :-#cat listNS.sh
//
// #!/bin/ksh
//
// JACORB_HOME=/vendorpackages/PrismTech/JACORB
//
// java -classpath \
// ./:\
// ${JACORB_HOME}/lib/avalon-framework.jar:\
// ${JACORB_HOME}/lib/jacorb.jar:\
// ${JACORB_HOME}/lib/logkit.jar \
// NSWrapper
//
// 4. attached is source file of NSWrapper.java

/*
 * Description: Utility class for using the CORBA Naming Service
 */

//import org.jacorb.orb.giop.CodeSet;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.Binding;
import org.omg.CosNaming.BindingHolder;
import org.omg.CosNaming.BindingIteratorHolder;
import org.omg.CosNaming.BindingListHolder;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.AlreadyBound;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * This class wraps the CORBA Naming Service with methods to make it more 'userfriendly'.
 * 
 */
public class JacorbTest {

    // private static final Logger m_log = Logger.getLogger( NSWrapper.class.getName());

    private NamingContextExt context = null;
    private String namingServiceStr = "corbaloc::localhost:29100/NameService";
    private ORB orb = null;

    // private static final ORB orb = getOrb();

    private void initOrb() {


        Properties props = System.getProperties();
        props.put("org.omg.CORBA.ORBClass", "org.jacorb.orb.ORB");
        props.put("org.omg.CORBA.ORBSingletonClass", "org.jacorb.orb.ORBSingleton");

        orb = ORB.init(new String[0], props);

        org.omg.CORBA.Object obj = orb.string_to_object(namingServiceStr);
        context = NamingContextExtHelper.narrow(obj);
    }

    // =============================================================================
    // ==================== public wrapped methods ===============================
    // =============================================================================

    public void bind(String name, org.omg.CORBA.Object obj) throws NotFound, CannotProceed, InvalidName, AlreadyBound {

        context.bind(context.to_name(name), obj);
    }

    public void unbind(String name) throws NotFound, CannotProceed, InvalidName {

        context.unbind(context.to_name(name));
    }

    public void rebind(String name, org.omg.CORBA.Object obj) throws NotFound, CannotProceed, InvalidName {

        context.rebind(context.to_name(name), obj);
    }

    // ===================== public tool functions =========================
    // =====================================================================

    /**
     * Get an IOR for object identified by name.
     * 
     * @param name
     *            of object.
     * @return IOR of the object name.
     */
    public String resolve(String name) {

        int loopUntil = 5;
        org.omg.CORBA.Object obj;

        for (int i = 0; i < loopUntil; i++) {
            try {
                obj = context.resolve_str(name);
                return orb.object_to_string(obj);
            } catch (NotFound e) {
                if (i >= loopUntil) {
                    return null;
                }
                // Sleep a while
                synchronized (this) {
                    try {
                        wait(1000);
                    } catch (Exception ex) {
                        // ignore
                    }
                }
            } catch (CannotProceed e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvalidName e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        return null; // Will never happen, makes the compiler to shut up.
    }

    /**
     * List all names within a naming service.
     * 
     * @return List of names
     */
    private List<String> list() {

        List<String> rets = new ArrayList<String>();

        try {
            BindingListHolder blsoh = new BindingListHolder(new Binding[0]);

            BindingIteratorHolder bioh = new BindingIteratorHolder();

            context.list(0, blsoh, bioh);

            BindingHolder bh = new BindingHolder();

            if (bioh.value == null)
                return rets;

            while (bioh.value.next_one(bh)) {
                String stringName = context.to_string(bh.value.binding_name);
                rets.add(stringName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rets;
    }

    /**
     * List all initial services.
     * 
     */
    public void listInitialServices() {

        String services[] = orb.list_initial_services();
        if (services != null) {
            System.out.println("There are total " + services.length + " initial service");
            for (int i = 0; i < services.length; i++)
                System.out.println(" initial service[" + i + "] = [" + services[i] + "]");
        }
    }

    public static void main(String args[]) {

//       int JacorbCodeSet= CodeSet.UTF16;
    	int JacorbCodeSet= 1;
    	
        JacorbTest wrapper = new JacorbTest();
        wrapper.initOrb();

        // wrapper.listInitialServices();

        // get all registered name objects.
        List<String> names = wrapper.list();
        String name = null;
        String ior = null;
        for (int i = 0; i < names.size(); i++) {
            name = names.get(i);
            ior = wrapper.resolve(name);
            System.out.println("IOR[" + name + "]=[" + ior + "]");
        }
    }
}