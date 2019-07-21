package org.basic.net.c20_jmx.jdmk.current.Lookup.msad;
/*
 * @(#)file      CreateJmxSchema.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.1
 * @(#)lastedit  04/02/05
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/*
 * Creates a schema for storing JMX connectors.
 *
 * After running this program, you should verify that the schema
 * has been updated correctly by using the directory server's
 * administration tool. If the schema has not been properly updated,
 * use the administration tool to correct it.
 *
 * You should first turn off schema-checking at the directory server
 * before running this program.
 *
 * usage:
 * java [-Djava.naming.provider.url=<ldap_server_url>] \
 *     CreateJmxSchema [-h|-l|-s[ad]] [-n<dn>] [-p<passwd>] [-a<auth>]
 *
 * -h           Print the usage message
 *
 * -l           List the JMX schema in the directory
 *
 * -s[ad]       Update schema:
 *
 *                -s update schema. This option is to be used with directory
 *                   servers not requiring any workaround for known bugs.
 *                   This option can be used with Sun ONE Directory Server.
 *
 *                -sad  means use a workaround for schema bugs in
 *                      Microsoft Windows 2000 Active Directory
 *
 * -n<dn>       Use <dn> as the distinguished name for authentication
 *
 * -p<passwd>   Use <passwd> as the password for authentication
 *
 * -a<auth>     Use <auth> as the authentication mechanism. Default is "simple".
 *
 * If neither -s, -l, nor -h has been specified, the default is "-l".
 *
 * The following example inserts the JMX schema from jmx-schema.txt in an Active
 * Directory (using the workaround schema bugs in Microsoft Windows 2000
 * Active Directory), logging in as "cn=administrateur,cn=users,dc=sun,dc=com"
 * with the password "secret":
 *
 *     java -Djava.naming.provider.url=ldap://localhost:389 CreateJmxSchema \
 *          -sad "-ncn=administrator,cn=users,dc=sun,dc=com" -psecret
 */
import javax.naming.*;
import javax.naming.directory.*;
import java.util.Hashtable;

public class CreateJmxSchema {

    protected static String dn, passwd, auth;

    // AD supports auxiliary classes in a peculiar way.
    //
    protected static boolean activeDirectorySchemaBug = false;

    protected static boolean traceLdap = false;
    protected static final int LIST = 0;
    protected static final int UPDATE = 1;

    private static String[] allAttrs = {
        "jmxServiceURL",
        "jmxAgentName",
        "jmxProtocolType",
        "jmxAgentHost",
        "jmxProperty",
        "jmxExpirationDate"};

    private static String[] allOCs = {
        "jmxConnector"};

    public static void main(String[] args) {
        new CreateJmxSchema().run(args, allAttrs, allOCs);
    }

    CreateJmxSchema () {
    }

    protected void run(String[] args, String[] attrIDs, String[] ocIDs) {
        int cmd = processCommandLine(args);
        try {
            DirContext ctx = signOn();
            switch (cmd) {
            case UPDATE:
                updateSchema(ctx, attrIDs, ocIDs);
                break;
            default:
                showSchema(ctx, attrIDs, ocIDs);
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Signs on to directory server using parameters supplied to program.
     * @return The initial context to the server.
     */
    private DirContext signOn() throws NamingException {
        if (dn != null && auth == null) {
            auth = "simple";
        }

        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY,
            "com.sun.jndi.ldap.LdapCtxFactory");

        env.put(Context.REFERRAL, "follow");

        if (auth != null) {
            env.put(Context.SECURITY_AUTHENTICATION, auth);
            env.put(Context.SECURITY_PRINCIPAL, dn);
            env.put(Context.SECURITY_CREDENTIALS, passwd);
        }

        // LDAP protocol tracing
        if (traceLdap) {
            env.put("com.sun.jndi.ldap.trace.ber", System.err);
        }

        return new InitialDirContext(env);
    }

    void showSchema(DirContext ctx, String[] attrs, String[] ocs)
        throws NamingException {
        DirContext attrRoot =
            (DirContext)ctx.getSchema("").lookup("AttributeDefinition");
        printSchema(attrRoot, attrs);
    
        DirContext ocRoot =
            (DirContext)ctx.getSchema("").lookup("ClassDefinition");
        printSchema(ocRoot, ocs);
    }

    private void printSchema(DirContext ctx, String[] ids) {
        for (int i = 0; i < ids.length; i++) {
            try {
                System.out.print(ids[i] + ": ");
                System.out.print(ctx.getAttributes(ids[i]));
            } catch (NamingException e) {
            } finally {
                System.out.println();
            }
        }
    }

    /**
     * Updates the schema:
     *
     * Add new attributes:
     *  jmxServiceURL
     *  jmxAgentName
     *  jmxProtocolType
     *  jmxAgentHost
     *  jmxProperty
     *  jmxExpirationDate
     *
     * Add new object classes:
     *  jmxConnector
     */
    private void updateSchema(DirContext ctx,
                              String[] attrIDs,
                              String[] ocIDs) throws NamingException {

        if (activeDirectorySchemaBug) {
            updateADSchema(ctx);
        } else {
            updateAttributes((DirContext)
                ctx.getSchema("").lookup("AttributeDefinition"), attrIDs);
            updateObjectClasses((DirContext)
                ctx.getSchema("").lookup("ClassDefinition"), ocIDs);
        }

        System.out.println("Please use your directory server's " +
                           "administration tool to verify the " +
                           "correctness of the schema.");
    }

    /**
     * Update attribute definitions.
     */
    protected void updateAttributes(DirContext attrRoot, String[] attrIDs)
        throws NamingException {

        // Get rid of old attr IDs
        //
        for (int i = 0; i < attrIDs.length; i++) {
            attrRoot.destroySubcontext(attrIDs[i]);
        }

        // jmxServiceURL
        //
        Attributes attrs = new BasicAttributes(true);
        attrs.put("NUMERICOID", "1.3.6.1.4.1.42.2.27.11.1.1");
        attrs.put("NAME", "jmxServiceURL");
        attrs.put("DESC", "String representation of a JMX Service URL");
        attrs.put("SYNTAX", "1.3.6.1.4.1.1466.115.121.1.26");
        attrs.put("SINGLE-VALUE", "true");
        attrRoot.createSubcontext("jmxServiceURL", attrs);
        System.out.println("Created jmxServiceURL attribute");

        // jmxAgentName
        //
        attrs = new BasicAttributes(true);
        attrs.put("NUMERICOID", "1.3.6.1.4.1.42.2.27.11.1.2");
        attrs.put("NAME", "jmxAgentName");
        attrs.put("DESC", "Name of the JMX Agent");
        attrs.put("SYNTAX", "1.3.6.1.4.1.1466.115.121.1.26");
        attrs.put("SINGLE-VALUE", "true");
        attrRoot.createSubcontext("jmxAgentName", attrs);
        System.out.println("Created jmxAgentName attribute");

        // jmxProtocolType
        //
        attrs = new BasicAttributes(true);
        attrs.put("NUMERICOID", "1.3.6.1.4.1.42.2.27.11.1.3");
        attrs.put("NAME", "jmxProtocolType");
        attrs.put("DESC", "Protocol used by the registered connector");
        attrs.put("SYNTAX", "1.3.6.1.4.1.1466.115.121.1.26");
        attrs.put("SINGLE-VALUE", "true");
        attrRoot.createSubcontext("jmxProtocolType", attrs);
        System.out.println("Created jmxProtocolType attribute");

        // jmxAgentHost
        //
        attrs = new BasicAttributes(true);
        attrs.put("NUMERICOID", "1.3.6.1.4.1.42.2.27.11.1.4");
        attrs.put("NAME", "jmxAgentHost");
        attrs.put("DESC", "Names or IP Addresses of the host on which the " +
                  "agent is running. When multiple values are given, they " +
                  "should be aliases to the same host.");
        attrs.put("SYNTAX", "1.3.6.1.4.1.1466.115.121.1.26");
        attrRoot.createSubcontext("jmxAgentHost", attrs);
        System.out.println("Created jmxAgentHost attribute");

        // jmxProperty
        //
        attrs = new BasicAttributes(true);
        attrs.put("NUMERICOID", "1.3.6.1.4.1.42.2.27.11.1.5");
        attrs.put("NAME", "jmxProperty");
        attrs.put("DESC", "Java-like property characterizing the registered " +
                  "object. The form of each value should be: " +
                  "\"<property-name>=<value>\". For instance: " +
                  "\"com.sun.jmx.remote.tcp.timeout=200\"");
        attrs.put("SYNTAX", "1.3.6.1.4.1.1466.115.121.1.26");
        attrRoot.createSubcontext("jmxProperty", attrs);
        System.out.println("Created jmxProperty attribute");

        // jmxExpirationDate
        //
        attrs = new BasicAttributes(true);
        attrs.put("NUMERICOID", "1.3.6.1.4.1.42.2.27.11.1.6");
        attrs.put("NAME", "jmxExpirationDate");
        attrs.put("DESC", "Date at which the JMX Service URL will be " +
                  "considered obsolete and may be removed from the " +
                  "directory tree");
        attrs.put("SYNTAX", "1.3.6.1.4.1.1466.115.121.1.24");
        attrs.put("SINGLE-VALUE", "true");
        attrRoot.createSubcontext("jmxExpirationDate", attrs);
        System.out.println("Created jmxExpirationDate attribute");
    }

    /**
     * Update objectclass definitions.
     */
    protected void updateObjectClasses(DirContext ocRoot, String[] ocIDs)
        throws NamingException {

        /* Get rid of old OCs - reverse order */
        for (int i = ocIDs.length - 1; i >= 0; i--) {
            ocRoot.destroySubcontext(ocIDs[i]);
        }

        // jmxConnector
        //
        Attributes attrs = new BasicAttributes(true);
        attrs.put("NUMERICOID", "1.3.6.1.4.1.42.2.27.11.2.1");
        attrs.put("NAME", "jmxConnector");
        attrs.put("DESC", "A class representing a JMX Connector, and " +
                  "containing a JMX Service URL. The jmxServiceURL is " +
                  "not present if the server is not accepting connections");
        attrs.put("SUP", "top");
        attrs.put("AUXILIARY", "true");
        Attribute mandatory = new BasicAttribute("MUST", "jmxAgentName");
        attrs.put(mandatory);
        Attribute optional = new BasicAttribute("MAY", "jmxServiceURL");
        optional.add("jmxAgentHost");
        optional.add("jmxProtocolType");
        optional.add("jmxProperty");
        optional.add("jmxExpirationDate");
        optional.add("description");
        attrs.put(optional);
        ocRoot.createSubcontext("jmxConnector", attrs);
        System.out.println("Created jmxConnector object class");
    }

    /**
     * Updates the Active Directory schema.
     *
     * Modification of the (RFC 2252) schema descriptions is not supported
     * in Active Directory. Instead, the Active Directory (internal) schema
     * must be modified.
     */
    private void updateADSchema(DirContext rootCtx) throws NamingException {

        System.out.println("[updating Active Directory schema ...]");

        // acquire schema context
        DirContext schemaCtx = getADSchema(rootCtx);

        // insert attribute definitions
        insertADAttributes(rootCtx, schemaCtx);

        // insert object class definitions
        insertADObjectClasses(rootCtx, schemaCtx);

        System.out.println("[update completed]\n");
    }

    /**
     * Locates the Active Directory schema.
     * @return A context for the root of the Active Directory schema.
     */
    private DirContext getADSchema(DirContext rootCtx) throws NamingException {

        System.out.println("  [locating the schema]");
        String snc = "schemaNamingContext"; // DSE attribute
        Attributes attrs =
            rootCtx.getAttributes("", new String[]{ snc });
        return (DirContext) rootCtx.lookup((String) attrs.get(snc).get());
    }

    /**
     * Inserts the attribute definitions from jmx-schema.txt into the schema.
     *
     * This method maps the LDAP schema definitions in the jmx-schema.txt onto
     * the proprietary attributes required by the Active Directory schema.
     *
     * The resulting attribute definitions are identical to those of the
     * jmx-schema.txt file.
     */
    protected void insertADAttributes(DirContext rootCtx, DirContext schemaCtx)
        throws NamingException {

        System.out.println("  [inserting new attribute definitions ...]");

        String dn = schemaCtx.getNameInNamespace();
        String attrID;

        // jmxServiceURL
        //
        attrID = new String("jmxServiceURL");
        Attributes attrs1 = new BasicAttributes();

        attrs1.put(new BasicAttribute("adminDescription", attrID));
        attrs1.put(new BasicAttribute("adminDisplayName", attrID));
        attrs1.put(new BasicAttribute("attributeID",
                                      "1.3.6.1.4.1.42.2.27.11.1.1"));
        attrs1.put(new BasicAttribute("attributeSyntax", "2.5.5.5"));
        attrs1.put(new BasicAttribute("cn", attrID));
        attrs1.put(new BasicAttribute("description",
                       "String representation of a JMX Service URL"));
        attrs1.put(new BasicAttribute("distinguishedName",
                                      "CN=" + attrID + "," + dn));
        attrs1.put(new BasicAttribute("isSingleValued", "TRUE"));
        attrs1.put(new BasicAttribute("lDAPDisplayName", attrID));
        attrs1.put(new BasicAttribute("name", attrID));
        attrs1.put(new BasicAttribute("objectCategory",
                                      "CN=Attribute-Schema," + dn));
        attrs1.put(new BasicAttribute("objectClass", "attributeSchema"));
        attrs1.put(new BasicAttribute("oMSyntax", "22"));
        attrs1.put(new BasicAttribute("searchFlags", "0"));
        attrs1.put(new BasicAttribute("systemOnly", "FALSE"));

        schemaCtx.createSubcontext("cn=" + attrID, attrs1);

        System.out.println("    [" + attrID + "]");

        // jmxAgentName
        //
        attrID = new String("jmxAgentName");
        Attributes attrs2 = new BasicAttributes();

        attrs2.put(new BasicAttribute("adminDescription", attrID));
        attrs2.put(new BasicAttribute("adminDisplayName", attrID));
        attrs2.put(new BasicAttribute("attributeID",
                                      "1.3.6.1.4.1.42.2.27.11.1.2"));
        attrs2.put(new BasicAttribute("attributeSyntax", "2.5.5.5"));
        attrs2.put(new BasicAttribute("cn", attrID));
        attrs2.put(new BasicAttribute("description",
                       "Name of the JMX Agent"));
        attrs2.put(new BasicAttribute("distinguishedName",
                                      "CN=" + attrID + "," + dn));
        attrs2.put(new BasicAttribute("isSingleValued", "TRUE"));
        attrs2.put(new BasicAttribute("lDAPDisplayName", attrID));
        attrs2.put(new BasicAttribute("name", attrID));
        attrs2.put(new BasicAttribute("objectCategory",
                                      "CN=Attribute-Schema," + dn));
        attrs2.put(new BasicAttribute("objectClass", "attributeSchema"));
        attrs2.put(new BasicAttribute("oMSyntax", "22"));
        attrs2.put(new BasicAttribute("searchFlags", "0"));
        attrs2.put(new BasicAttribute("systemOnly", "FALSE"));

        schemaCtx.createSubcontext("cn=" + attrID, attrs2);

        System.out.println("    [" + attrID + "]");

        // jmxProtocolType
        //
        attrID = new String("jmxProtocolType");
        Attributes attrs3 = new BasicAttributes();

        attrs3.put(new BasicAttribute("adminDescription", attrID));
        attrs3.put(new BasicAttribute("adminDisplayName", attrID));
        attrs3.put(new BasicAttribute("attributeID",
                                      "1.3.6.1.4.1.42.2.27.11.1.3"));
        attrs3.put(new BasicAttribute("attributeSyntax", "2.5.5.5"));
        attrs3.put(new BasicAttribute("cn", attrID));
        attrs3.put(new BasicAttribute("description",
                       "Protocol used by the registered connector"));
        attrs3.put(new BasicAttribute("distinguishedName",
                                      "CN=" + attrID + "," + dn));
        attrs3.put(new BasicAttribute("isSingleValued", "TRUE"));
        attrs3.put(new BasicAttribute("lDAPDisplayName", attrID));
        attrs3.put(new BasicAttribute("name", attrID));
        attrs3.put(new BasicAttribute("objectCategory",
                                      "CN=Attribute-Schema," + dn));
        attrs3.put(new BasicAttribute("objectClass", "attributeSchema"));
        attrs3.put(new BasicAttribute("oMSyntax", "22"));
        attrs3.put(new BasicAttribute("searchFlags", "0"));
        attrs3.put(new BasicAttribute("systemOnly", "FALSE"));

        schemaCtx.createSubcontext("cn=" + attrID, attrs3);

        System.out.println("    [" + attrID + "]");

        // jmxAgentHost
        //
        attrID = new String("jmxAgentHost");
        Attributes attrs4 = new BasicAttributes();

        attrs4.put(new BasicAttribute("adminDescription", attrID));
        attrs4.put(new BasicAttribute("adminDisplayName", attrID));
        attrs4.put(new BasicAttribute("attributeID",
                                      "1.3.6.1.4.1.42.2.27.11.1.4"));
        attrs4.put(new BasicAttribute("attributeSyntax", "2.5.5.5"));
        attrs4.put(new BasicAttribute("cn", attrID));
        attrs4.put(new BasicAttribute("description",
                       "Names or IP Addresses of the host on which the agent " +
                       "is running. When multiple values are given, they " +
                       "should be aliases to the same host."));
        attrs4.put(new BasicAttribute("distinguishedName",
                                      "CN=" + attrID + "," + dn));
        attrs4.put(new BasicAttribute("isSingleValued", "FALSE"));
        attrs4.put(new BasicAttribute("lDAPDisplayName", attrID));
        attrs4.put(new BasicAttribute("name", attrID));
        attrs4.put(new BasicAttribute("objectCategory",
                                      "CN=Attribute-Schema," + dn));
        attrs4.put(new BasicAttribute("objectClass", "attributeSchema"));
        attrs4.put(new BasicAttribute("oMSyntax", "22"));
        attrs4.put(new BasicAttribute("searchFlags", "0"));
        attrs4.put(new BasicAttribute("systemOnly", "FALSE"));

        schemaCtx.createSubcontext("cn=" + attrID, attrs4);

        System.out.println("    [" + attrID + "]");

        // jmxProperty
        //
        attrID = new String("jmxProperty");
        Attributes attrs5 = new BasicAttributes();

        attrs5.put(new BasicAttribute("adminDescription", attrID));
        attrs5.put(new BasicAttribute("adminDisplayName", attrID));
        attrs5.put(new BasicAttribute("attributeID",
                                      "1.3.6.1.4.1.42.2.27.11.1.5"));
        attrs5.put(new BasicAttribute("attributeSyntax", "2.5.5.5"));
        attrs5.put(new BasicAttribute("cn", attrID));
        attrs5.put(new BasicAttribute("description",
                       "Java-like property characterizing the registered " +
                       "object. The form of each value should be: " +
                       "\"<property-name>=<value>\". For instance: " +
                       "\"com.sun.jmx.remote.tcp.timeout=200\""));
        attrs5.put(new BasicAttribute("distinguishedName",
                                      "CN=" + attrID + "," + dn));
        attrs5.put(new BasicAttribute("isSingleValued", "FALSE"));
        attrs5.put(new BasicAttribute("lDAPDisplayName", attrID));
        attrs5.put(new BasicAttribute("name", attrID));
        attrs5.put(new BasicAttribute("objectCategory",
                                      "CN=Attribute-Schema," + dn));
        attrs5.put(new BasicAttribute("objectClass", "attributeSchema"));
        attrs5.put(new BasicAttribute("oMSyntax", "22"));
        attrs5.put(new BasicAttribute("searchFlags", "0"));
        attrs5.put(new BasicAttribute("systemOnly", "FALSE"));

        schemaCtx.createSubcontext("cn=" + attrID, attrs5);

        System.out.println("    [" + attrID + "]");

        // jmxExpirationDate
        //
        attrID = new String("jmxExpirationDate");
        Attributes attrs6 = new BasicAttributes();

        attrs6.put(new BasicAttribute("adminDescription", attrID));
        attrs6.put(new BasicAttribute("adminDisplayName", attrID));
        attrs6.put(new BasicAttribute("attributeID",
                                      "1.3.6.1.4.1.42.2.27.11.1.6"));
        attrs6.put(new BasicAttribute("attributeSyntax", "2.5.5.11"));
        attrs6.put(new BasicAttribute("cn", attrID));
        attrs6.put(new BasicAttribute("description",
                       "Date at which the JMX Service URL will be considered " +
                       "obsolete and may be removed from the directory tree"));
        attrs6.put(new BasicAttribute("distinguishedName",
                                      "CN=" + attrID + "," + dn));
        attrs6.put(new BasicAttribute("isSingleValued", "TRUE"));
        attrs6.put(new BasicAttribute("lDAPDisplayName", attrID));
        attrs6.put(new BasicAttribute("name", attrID));
        attrs6.put(new BasicAttribute("objectCategory",
                                      "CN=Attribute-Schema," + dn));
        attrs6.put(new BasicAttribute("objectClass", "attributeSchema"));
        attrs6.put(new BasicAttribute("oMSyntax", "24"));
        attrs6.put(new BasicAttribute("searchFlags", "0"));
        attrs6.put(new BasicAttribute("systemOnly", "FALSE"));

        schemaCtx.createSubcontext("cn=" + attrID, attrs6);

        System.out.println("    [" + attrID + "]");

        flushADSchemaMods(rootCtx); // finally
    }

    /**
     * Inserts object class definitions from jmx-schema.txt into the schema.
     *
     * This method maps the LDAP schema definitions in the jmx-schema.txt onto
     * the proprietary attributes required by the Active Directory schema.
     *
     * The resulting object class definitions differ from those of
     * jmx-schema.txt in the following ways:
     *
     *     - The jmxConnector auxiliary class is now defined as structural.
     *     - The jmxConnector class now inherits from javaContainer.
     *
     * The effect of these differences is that Java objects and JMX connectors
     * cannot be mixed-in with other directory entries, they may only be stored
     * as stand-alone entries.
     *
     * The reason for these differences is due to the way auxiliary classes
     * are supported the Active Directory. Only the names of structural
     * classes (not auxiliary) may appear in the object class attribute of
     * an entry. Therefore, the abstract and auxiliary classes in the JMX
     * schema definition are re-defined as structural.
     */
    protected void insertADObjectClasses(DirContext rootCtx,
                                         DirContext schemaCtx)
        throws NamingException {

        System.out.println("  [inserting new object class definitions ...]");

        String dn = schemaCtx.getNameInNamespace();
        String attrID;

        attrID = new String("jmxConnector");
        Attributes attrs1 = new BasicAttributes();

        attrs1.put(new BasicAttribute("adminDescription", attrID));
        attrs1.put(new BasicAttribute("adminDisplayName", attrID));
        attrs1.put(new BasicAttribute("objectClass", "classSchema"));
        attrs1.put(new BasicAttribute("defaultHidingValue", "FALSE"));
        attrs1.put(new BasicAttribute("governsID",
                                      "1.3.6.1.4.1.42.2.27.11.2.1"));
        attrs1.put(new BasicAttribute("lDAPDisplayName", attrID));
        attrs1.put(new BasicAttribute("mustContain", "jmxAgentName"));
        Attribute jmxConnectorMay = new BasicAttribute("mayContain");
        jmxConnectorMay.add("jmxServiceURL");
        jmxConnectorMay.add("jmxProtocolType");
        jmxConnectorMay.add("jmxAgentHost");
        jmxConnectorMay.add("jmxProperty");
        jmxConnectorMay.add("jmxExpirationDate");
        jmxConnectorMay.add("description");
        attrs1.put(jmxConnectorMay);
        attrs1.put(new BasicAttribute("objectClassCategory", "1"));
        attrs1.put(new BasicAttribute("systemOnly", "FALSE"));
        attrs1.put(new BasicAttribute("subclassOf", "javaContainer"));
        attrs1.put(new BasicAttribute("description",
                       "A class representing a JMX Connector, and containing " +
                       "a JMX Service URL. The jmxServiceURL is not present " +
                       "if the server is not accepting connections"));

        schemaCtx.createSubcontext("CN=" + attrID, attrs1);

        System.out.println("    [" + attrID + "]");

        flushADSchemaMods(rootCtx); // finally
    }

    /**
     * Writes schema modifications to the Active Directory schema immediately.
     */
    protected void flushADSchemaMods(DirContext rootCtx)
        throws NamingException {

        rootCtx.modifyAttributes("", new ModificationItem[] {
            new ModificationItem(DirContext.ADD_ATTRIBUTE,
                                 new BasicAttribute("schemaUpdateNow", "1"))
        });
    }

    private int processCommandLine(String[] args) {
        String option;
        boolean schema = false;
        boolean list = false;

        for (int i = 0; i < args.length; i++) {
            option = args[i];
            if (option.startsWith("-h")) {
                printUsage(null);
            }
            if (option.startsWith("-s")) {
                schema = true;
                activeDirectorySchemaBug = option.equals("-sad");
            } else if (option.startsWith("-l")) {
                list = true;
            } else if (option.startsWith("-a")) {
                auth = option.substring(2);
            } else if (option.startsWith("-n")) {
                dn = option.substring(2);
            } else if (option.startsWith("-p")) {
                passwd = option.substring(2);
            } else if (option.startsWith("-trace")) {
                traceLdap = true;
            } else {
                // invalid option
                printUsage("Invalid option");
            }
        }

        if (!schema) {
            return LIST;
        } else {
            return UPDATE;
        }
    }

    protected void printUsage(String msg) {
        printUsageAux(msg, "Jmx");
    }

    protected void printUsageAux(String msg, String key) {
        if (msg != null) {
            System.out.println(msg);
        }

        System.out.print("Usage: ");
        System.out.println("java [-Djava.naming.provider.url=<ldap_server_url>] \\");
        System.out.println("  Create" + key + "Schema [-h|-l|-s[ad]] [-n<dn>] [-p<passwd>] [-a<auth>]");
        System.out.println();
        System.out.println("  -h\t\tPrint the usage message");
        System.out.println("  -l\t\tList the " + key + " schema in the directory");
        System.out.println("  -s[ad]\tUpdate schema:");
        System.out.println("\t\t -s    use with non buggy directory servers");
        System.out.println("\t\t -sad  use workaround for Active Directory schema bug");
        System.out.println("  -n<dn>\tUse <dn> as the distinguished name for authentication");
        System.out.println("  -p<passwd>\tUse <passwd> as the password for authentication");
        System.out.println("  -a<auth>\tUse <auth> as the authentication mechanism");
        System.out.println("\t\t Default is 'simple' if dn specified; otherwise 'none'");
        System.exit(-1);
    }
}
