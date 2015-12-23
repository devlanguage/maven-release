package org.basic.net.c20_jmx.jdmk.current.MBeanServerInterceptor;
/*
 * @(#)file      FileMBeanServerInterceptor.java
 * @(#)author    Sun Microsystems, Inc.
 * @(#)version   1.8
 * @(#)lastedit  04/04/21
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

// Java imports
//
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

// JMX imports
//
import javax.management.*; 

// JDMK imports
//
import com.sun.jdmk.ServiceName;
import com.sun.jdmk.interceptor.MBeanServerInterceptor;

/**
 * Implements a FileMBeanServerInterceptor that handles virtual File MBeans.
 * The FileMBeanServerInterceptor mirrors the content of a file system directory,
 * by simulating one MBean per file and directory contained in the
 * mirrored directory.
 *
 * <p>The FileMBeanServerInterceptor owns a reserved domain name. No other
 * interceptor should use that domain name.
 * The {@link MasterMBeanServerInterceptor} uses that domain name in order
 * to route the requests to the FileMBeanServerInterceptor.
 *
 * <p>The FileMBeanServerInterceptor simulates two types of MBeans:
 * <ul>
 *     <li>{@link #VirtualDirObject}: These MBeans represent directory
 *         resources.</li>
 *     <li>{@link #VirtualFileObject}: These MBeans represent file
 *         resources.</li>
 * </ul>
 *
 * {@link #VirtualDirObject} and {@link #VirtualFileObject} have the
 * same attributes, but {@link #VirtualDirObject} has in addition a
 * <tt>public void cd();</tt> operation.
 *
 * <p>The <tt>cd()</tt> operation makes the FileMBeanServerInterceptor change
 * to the directory represented by the {@link #VirtualDirObject} on which
 * the <tt>cd()</tt> operation is invoked.
 *
 * <p>The MBeans simulated by this interceptor are completely virtual.
 * All the logic is contained in the interceptor itself.
 *
 * <p>The FileMBeanServerInterceptor mirrors the content of a directory, i.e.
 * it fakes one MBean per file and subdirectory found in the mirrored
 * directory. The parent directory (..) and the mirrored directory
 * (.) are also represented by one virtual MBean each one.
 *
 * <p>The directory mirrored by the FileMBeanServerInterceptor is set by the
 * <tt>start()</tt> method, and can be later changed by stopping
 * (<tt>stop()</tt>) and restarting (<tt>start()</tt>) the
 * FileMBeanServerInterceptor, or by invoking <tt>cd()</tt> on a virtual MBean
 * representing a directory resource.
 *
 * <p>When <tt>cd()</tt> is invoked, the FileMBeanServerInterceptor sends
 * an MBeanServerNotification.UNREGISTRATION_NOTIFICATION per faked
 * MBean in the current mirrored directory, and then an
 * MBeanServerNotification.REGISTRATION_NOTIFICATION per faked
 * MBean in the new mirrored directory. Invoking <tt>cd()</tt> on the
 * virtual MBean representing the directory currently mirrored has thus
 * the only observable effect of faking the unregistration and
 * re-registration of all the currently faked MBeans.
 *
 * <p>Note that the special character `:', when present in filenames,
 * will be replaced by ';' in the ObjectNames, as `:' is not legal
 * in JMX property values. As a consequence, files and directories
 * whose names contain the ';' character will not be accessible, as
 * the  reverse translation will not work.
 */

public class FileMBeanServerInterceptor implements MBeanServerInterceptor {

    /**
     * The delegate ObjectName.
     */
    protected final static ObjectName delegateName;
    static {
        try {
            delegateName = new ObjectName(ServiceName.DELEGATE);
        } catch (Exception x) {
            throw new IllegalArgumentException(x.getMessage());
        }
    }

    /**
     * The class name returned for virtual MBeans representing
     * directory resources.
     */
    public static final String VirtualDirObject = "VirtualDirObject";

    /**
     * The class name returned for virtual MBeans representing
     * file resources.
     */
    public static final String VirtualFileObject = "VirtualFileObject";

    // The name of the attributes implemented by the virtual MBeans
    //
    private static final String[] attributeNames = new String[] {
        "Directory","File","Read","Write","Path","Size"
    };

    // The MBeanAttributeInfo[] of the attributes implemented by the
    // virtual MBeans
    //
    private static final MBeanAttributeInfo[] attributeInfos = 
        new MBeanAttributeInfo[] {
            new MBeanAttributeInfo("Directory","boolean",
                        "True if this MBean represents a directory resource",
                                   true,false,true),
            new MBeanAttributeInfo("File","boolean",
                             "True if this MBean represents a file resource",
                                   true,false,true),
            new MBeanAttributeInfo("Read","boolean",
                       "True if this MBean represents a readable resource",
                                   true,false,true),
            new MBeanAttributeInfo("Write","boolean",
                       "True if this MBean represents a writable resource",
                                   true,false,true),
            new MBeanAttributeInfo("Path","java.lang.String",
                    "The pathname of the resource represented by this MBean",
                                   true,false,false),
            new MBeanAttributeInfo("Size","long",
                    "The size of the resource represented by this MBean",
                                   true,false,false)
                };

    // The MBeanOperationInfo[] of the operations implemented by the
    // virtual MBeans representing directory resources.
    //
    private static final MBeanOperationInfo[] dirOperations = 
        new  MBeanOperationInfo[] {
            new MBeanOperationInfo("cd","Change to this directory",null,
                                   "void",MBeanOperationInfo.ACTION)
        };

    // The MBeanInfo of the virtual MBeans representing file resources.
    //
    private static final MBeanInfo fileInfo = 
        new MBeanInfo(VirtualFileObject,
                      "An MBean representing a file resource",
                      attributeInfos,
                      null,null,null);

    // The MBeanInfo of the virtual MBeans representing directory resources.
    //
    private static final MBeanInfo dirInfo = 
        new MBeanInfo(VirtualDirObject,
                      "An MBean representing a directory resource",
                      attributeInfos,
                      null,dirOperations,null);
    
    
    // The domain managed by this interceptor.
    // No other interceptor should create MBeans in this domain.
    //
    private final String fileDomain;
    private final char[] fileDomainChars;
    
    // The MBeanServerDelegate.
    // The FileMBeanServerInterceptor will use the MBeanServerDelegate
    // in order to fake the creation and destruction of MBeans by
    // sending MBeanServerNotifications at startup, and when the 
    // operation "cd" is invoked on a virtual Directory MBean.
    //
    private final MBeanServerDelegate forwarder;

    // The MBeanServer in which this interceptor is inserted.
    // The FileMBeanServerInterceptor will use the MBeanServer in order
    // to evaluate QueryExp objects.
    //
    private final MBeanServer server;

    // The canonical pathname of the directory from which this interceptors
    // fakes its virtual MBeans.
    //
    private String dirName = null;

    // The File object representing the directory from which this 
    // interceptors fakes its virtual MBeans.
    //
    private File fileDir = null;

    /**
     * Return true if the given ObjectName matches the ObjectName pattern.
     * @param name The ObjectName we want to match against the pattern.
     * @param pattern The ObjectName pattern.
     * @return true if <code>name</code> matches the pattern.
     */
    public final static boolean matches(ObjectName name, 
                                        ObjectName pattern) {
        if (pattern == null) return true;
        final String od = name.getDomain();
        if (!(od == null || od.equals(""))) {
            final String domain = pattern.getDomain();
            if (domain == null) return false;
            if (!wildmatch(od.toCharArray(),domain.toCharArray(),0,0)) 
                return false;
        }
        if (pattern.isPropertyPattern()) {
            final Hashtable propertyList = pattern.getKeyPropertyList();
            if (propertyList == null) return true;
            final Set set = propertyList.entrySet();
            if (set == null) return true;
            final int len = set.size();
            if (len == 0) return true;
            final Map.Entry[] entries = 
                (Map.Entry[]) set.toArray(new Map.Entry[len]);
            for (int i=0 ; i < len ; i++) {
                final Map.Entry e = entries[i];
                final String key = (String)e.getKey();
                final String value = (String)e.getValue();
                final String v = name.getKeyProperty(key);
                if (v == null && value != null) return false;
                if (v.equals(value)) continue;
                return false;
            }
            return true;
        } else {
            final String p1 = name.getCanonicalKeyPropertyListString();
            final String p2 = pattern.getCanonicalKeyPropertyListString();
            if (p1 == null) return (p2 == null);
            if (p2 == null) return p1.equals("");
            return (p1.equals(p2));
        }
    }

    /*
     * Tests whether string s is matched by pattern p.
     * Supports "?", "*" each of which may be escaped with "\";
     * Not yet supported: internationalization; "\" inside brackets.<P>
     * Wildcard matching routine by Karl Heuer.  Public Domain.<P>
     * Sligthly Optimized by Java DMK Team.
     */  
    private final static boolean wildmatch(char[] s,char[] p,int si,int pi) {
        char c;
        final int slen = s.length;
        final int plen = p.length;

        while (pi < plen) {            // While still string
            c = p[pi++];
            if (c == '?') {
                if (++si > slen) return false;
            } else if (c == '*') {        // Wildcard
                if (pi >= plen) return true;
                do {
                    if (wildmatch(s,p,si,pi)) return true;
                } while (++si < slen);
                return false;
            } else {
                if (si >= slen || c != s[si++]) return false;
            }
        }
        return (si == slen);
    }


    /**
     * Construct a new FileMBeanServerInterceptor.
     * @param fileDomain The domain managed by this MBeanServerInterceptor.
     * @param forwarder The MBeanServerDelegate through which 
     *                  MBeanServerNotifications must be sent.
     * @param server    The MBeanServer in which this interceptor
     *                  is going to be inserted.
     */
    public FileMBeanServerInterceptor(String fileDomain, 
				      MBeanServerDelegate forwarder,
				      MBeanServer server) {
        this.fileDomain = fileDomain;
        this.fileDomainChars =
            (fileDomain == null ? null : fileDomain.toCharArray());
        this.forwarder = forwarder;
        this.server = server;
        checkInitialization();
    }

    /**
     * Returns the domain name reserved for this FileMBeanServerInterceptor.
     */
    protected final synchronized String fileDomain() {
        return fileDomain;
    }

    /**
     * Returns the File object representing the directory mirrored
     * by this FileMBeanServerInterceptor.
     */
    protected final synchronized File fileDir() {
        return fileDir;
    }

    /**
     * The MBeanServerDelegate through which MBeanServerNotification
     * will be sent.
     * @return The MBeanServerDelegate.
     */
    protected final synchronized MBeanServerDelegate forwarder() {
        return forwarder;
    }

    /**
     * Check that this FileMBeanServerInterceptor is correctly initialized.
     * @exception IllegalArgumentException if the FileMBeanServerInterceptor
     *            is not correctly initialized.
     */
    protected void checkInitialization() 
        throws IllegalArgumentException {

        // fileDomain must not be null
        //
        if (fileDomain == null)
            throw new IllegalArgumentException("Bad initialization: " +
                            "MBeanServerInterceptor domain must not be null.");
        // MBeanServerDelegate must not be null
        //
        if (forwarder == null) 
            throw new IllegalArgumentException("Bad initialization: " +
                            "MBeanServerDelegate must not be null.");

        // MBeanServer must not be null
        //
        if (server == null) 
            throw new IllegalArgumentException("Bad initialization: " +
                            "MBeanServer must not be null.");
    }

    /**
     * Send a MBeanServerNotification of the given <var>type</var> for
     * each MBean faked by this interceptor.
     * @param type Must be one of 
     *        MBeanServerNotification.REGISTRATION_NOTIFICATION or
     *        MBeanServerNotification.UNREGISTRATION_NOTIFICATION.
     */
    private final void sendMBeanServerNotifications(String type) {
        try {
            final File wd = fileDir();
            if (wd == null) return;
            final File[] list = wd.listFiles();
            final int len = list.length;
            for (int i=0;i<len;i++) {
                try {
                    final File f = list[i];
                    if (f == null) continue;
                    final ObjectName n = getName(f);
                    final Notification notif = 
                        new MBeanServerNotification(type,delegateName,0,n);
                    final MBeanServerDelegate fwd = forwarder();
                    fwd.sendNotification(notif);
                } catch (Exception x) {
                    debug("sendMBeanServerNotifications", 
                          "Virtual MBean failed: " + x);
                }
            }
        } catch (Exception x) {
            debug("sendMBeanServerNotifications", 
                  "Failed to send notifications: " + x);
        }
    }

    /**
     * Create an ObjectName from the given File.
     * The ObjectName is of the form: 
     * <pre>
     *   <i>fileDomain</i>:path=<i>CanonicalPathname</i>
     * </pre>
     * Note that the special character `:' is replaced by ';' in the 
     * ObjectName. As a consequence filenames which already contain
     * the character ';' will not be accessible, as the reverse translation
     * will not work.
     * @param file The {@link java.io.File} object for which an ObjectName
     *        must be constructed.
     * @return the ObjectName of the virtual MBean faked for that 
     *         <var>file</var>.
     * @exception MalformedObjectNameException if the ObjectName cannot
     *     be constructed (e.g. the <i>CanonicalPathname</i> contains
     *     illegal characters such as `,' or `=' etc...).
     * @exception IOException if the <i>CanonicalPathname</i> cannot be
     *     obtained from the given <var>file</var> object.
     */
    protected final ObjectName getName(File file) 
        throws MalformedObjectNameException, IOException {
        final ObjectName n = 
            new ObjectName(fileDomain + ":path="+
                           file.getCanonicalPath().replace(':',';'));
        return n;
    }

    /**
     * Return the {@link java.io.File} object associated with the 
     * given <var>name</var>.
     * Note that the special character `:' is replaced by ';' in the 
     * ObjectName. As a consequence filenames which already contain
     * the character ';' will not be accessible, as the reverse translation
     * will not work.
     * @param name The ObjectName from which a {@link java.io.File} must
     *        be derived.
     * @exception InstanceNotFoundException if the given name does not
     *        correspond to any virtual MBean faked by this interceptor.
     */
    protected final File getFile(ObjectName name) 
        throws InstanceNotFoundException {
        try {
            if (isDebugOn())
                debug("getFile", "trying to find file for " + name);
            final File   wd;
            final String pwd;
            synchronized (this) {
                wd   = fileDir;
                pwd  = dirName;
            }

            // Check that:
            // - The interceptor is started (wd != null)
            // - The given name is not null
            // - The given name is within the domain managed by this 
            //   interceptor
            //
            if (wd == null || name == null || 
                !name.getDomain().equals(fileDomain)) {
                debug("getFile", "Interceptor not started or MBean not in "+
                      fileDomain);
                throw new InstanceNotFoundException(name + 
                                                    ": MBean not found.");
            }

            // Get the value of the "path" property.
            //
            final String path = name.getKeyProperty("path").replace(';',':');
            
            // If the "path" property is not present, the given name
            // does not correspond to any MBean faked by this interceptor.
            //
            if (path == null) {         
                debug("getFile", "path property not found");
                throw new InstanceNotFoundException(name.toString() + 
                                                    ": MBean not found.");
            }

            // Get the file pointed to by the "path" property.
            //
            final File file = new File(path);
            
            // Get the canonical pathname of the file pointed to by 
            // the "path" property.
            //
            final String cp = file.getCanonicalPath();

            // Check that the "path" property is the canonical pathname.
            //
            if (!path.equals(cp)) {
                debug("getFile", "Path is not the canonical form: " + cp);
                throw new InstanceNotFoundException(name.toString() + 
                                                    ": MBean not found.");
            }

            // Check that the retrieved file is either:
            // - the directory mirrored by this interceptor, or
            // - the parent directory of the directory mirrored by this 
            //   interceptor, or
            // - a direct child of the directory mirrored by this 
            //   interceptor.
            //
            final File   parentFile  = file.getParentFile();
            final File   parentWd    = wd.getParentFile();
            final String parent = 
                (parentFile!=null?parentFile.getCanonicalPath():"");
            final String dotdot = 
                (parentWd!=null?parentWd.getCanonicalPath():"");
            if ((!cp.equals(pwd))&&(!cp.equals(dotdot))
                &&(!pwd.equals(parent))) {
                debug("getFile", "File not in current scope: " + wd);
                throw new InstanceNotFoundException(name.toString() + 
                                                    ": MBean not found.");
            }

            // Now check that "path" is the only key contained in the
            // given name.
            //
            final String ps = name.getKeyPropertyListString();
            if (!ps.equals("path="+cp.replace(':',';'))) {
                debug("getFile", "Invalid keys: " + ps);
                throw new InstanceNotFoundException(name.toString() + 
                                                    ": MBean not found.");
            }

            // We passed all the checkings: the ObjectName correspond
            // to a virtual MBean faked by this interceptor:
            // we return the associated File.
            //
            return file;
        } catch (InstanceNotFoundException i) {
            throw i;
        } catch (Exception x) {
            // We obviously don't fake an MBean of that name...
            //
            debug("getFile",name.toString() + ": MBean not found.");
            debug("getFile",x);
            throw new InstanceNotFoundException(name.toString() + 
                                                ": MBean not found.");
        }
    }

    /**
     * Changed the directory mirrored by this interceptor to the
     * directory pointed to by the given file.
     * @param file The new directory to mirror.
     * @exception IOException if we can't change to the given directory.
     */
    void cd(File file) throws IOException {
        synchronized(fileDomain) {
            stop();
            final String cdn = file.getCanonicalPath();
            start(cdn);
        }
    }

    /**
     * Starts the FileMBeanServerInterceptor.
     * Fakes the registration of all virtual MBeans.
     * @param dirName The name of the directory that this interceptor
     *        will mirror.
     * @exception IllegalArgumentException if the given directory can't
     *        be mirrored.
     */
    public void start(String dirName) {

        // dirName must not be null
        //
        if (dirName == null)
            throw new IllegalArgumentException("Null parameter.");

        // We will get a canonical representation of dirname, in order to
        // ensure the unicity of the ObjectNames
        //
        final File dir;
        final String cp;
        try {
            final File tmpdir = new File(dirName);
            cp = tmpdir.getCanonicalPath();
            dir = new File(cp);
        } catch (Exception x) {
            // Problem reading the directory => we cannot mirror
            // anything...
            //
            throw new
                IllegalArgumentException(dirName + ": No such directory.");
        }
        
        // The given pathname is not directory => fail
        //
        if (!dir.isDirectory()) throw new 
            IllegalArgumentException(dirName + ": No such directory.");

        // The given directory can be accessed => store its associated 
        // file and canonical name.
        //
        synchronized(this) {
            this.fileDir = dir;
            this.dirName = cp;
        }

        // Send an MBeanServerNotification.REGISTRATION_NOTIFICATION for
        // each MBean faked from the mirrored directory
        //
        sendMBeanServerNotifications(
                      MBeanServerNotification.REGISTRATION_NOTIFICATION);
    }

    /**
     * Stops this FileMBeanServerInterceptor.
     * Fakes the unregistration of all virtual MBeans.
     */
    public void stop() {
        try {
            // Send an MBeanServerNotification.REGISTRATION_NOTIFICATION for
            // each MBean faked from the mirrored directory
            //
            sendMBeanServerNotifications(
                     MBeanServerNotification.UNREGISTRATION_NOTIFICATION);
        } finally {
            synchronized(this) {
                this.fileDir = null;
                this.dirName = null;
            }
        }
    }
    
    // Always rejected.
    //
    public final ObjectInstance createMBean(final String className, 
                                            final ObjectName name, 
                                            final Object params[], 
                                            final String signature[]) 
        throws ReflectionException, InstanceAlreadyExistsException, 
        MBeanRegistrationException, MBeanException, 
        NotCompliantMBeanException {

        // We do not accept the creation of new files...
        //
        final RuntimeException x = 
            new UnsupportedOperationException(name.getDomain() + 
                            ": Can't register an MBean in that domain.");
        throw new
            MBeanRegistrationException(x,"Registration failed.");
    }

    // Always rejected.
    //
    public final ObjectInstance createMBean(final String className, 
                                      final ObjectName name, 
                                      final ObjectName loaderName, 
                                      final Object params[], 
                                      final String signature[]) 
        throws ReflectionException, InstanceAlreadyExistsException,
        MBeanRegistrationException, MBeanException, 
        NotCompliantMBeanException, InstanceNotFoundException {

        // We do not accept the creation of new files...
        //

        final RuntimeException x = 
            new UnsupportedOperationException(name.getDomain() + 
                            ": Can't register an MBean in that domain.");
        throw new
            MBeanRegistrationException(x,"Registration failed.");
    }

    /**
     * Construct a new ObjectInstance for the given file.
     * @param name The ObjectName of the faked MBean
     * @param file The {@link java.io.File} associated with the 
     *        faked MBean.
     * @return The faked ObjectInstance.
     */
    final ObjectInstance getObjectInstance(final ObjectName name,
                                                  final File file) 
        throws InstanceNotFoundException {

        final String clazz; 
        if (file.isDirectory()) {
            clazz = VirtualDirObject;
        } else {
            clazz = VirtualFileObject;
        }
        return new ObjectInstance(name,clazz);
    }

    // Get the file associated with the given name and fake
    // a new ObjectInstance from that.
    //
    public final ObjectInstance getObjectInstance(final ObjectName name) 
        throws InstanceNotFoundException {

        // This will throw InstanceNotFoundException if the given name
        // does not correspond to any MBean faked by this interceptor.
        //
        final File file = getFile(name);

        // Get the ObjectInstance from the returned file.
        //
        return getObjectInstance(name,file);
    }

    /**
     * Apply a query.
     * @param name The ObjectName of the faked MBean
     * @param file The {@link java.io.File} associated with the 
     *        faked MBean.
     * @param query The QueryExp to apply
     * @return the result of the query evaluation.
     */
    private final boolean query(final ObjectName name, 
                                final File file,
                                final QueryExp query) {
        if (query == null) return true;

        try {
            query.setMBeanServer(server);
            return query.apply(name);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Add a new ObjectInstance to the given <var>result</var> Set
     * if the virtual MBean identified by <var>file</var> matches
     * the given <var>pattern</var> and <var>query</var>.
     * @param pattern The ObjectName pattern to match with the faked MBean.
     * @param query The QueryExp to apply.
     * @param file The {@link java.io.File} associated with the 
     *        faked MBean.
     * @param result The Set in which to add the faked ObjectInstance if
     *   the virtual MBean identified by <var>file</var> matches
     *   the given <var>pattern</var> and <var>query</var>.
     */
    final void addMBean(final ObjectName pattern, final QueryExp query, 
                        final File file, final Set result) {
        try {
            if (file == null) return;

            // Get the faked ObjectName corresponding to file.
            //
            final ObjectName n = getName(file);

            // Check whether the file & name match the pattern & query
            //
            if ((!matches(n,pattern)) || (!query(n,file,query))) return; 

            // Pattern matched and query matched => add the ObjectInstance
            // to the result Set.
            //
            result.add(getObjectInstance(n,file));
        } catch (Exception x) {
            debug("queryMBeans", 
                  "Virtual MBean failed: " + x);
        }
    }

    // Parses the directory mirrored by this interceptor
    // in order to find the list of faked MBeans.
    //
    public final Set queryMBeans(final ObjectName name, 
                                 final QueryExp query) {

        final File wd = fileDir();

        // No directory mirrored (interceptor stopped) => return an
        // empty Set.
        //
        if (wd == null) return new HashSet(0);

        // If name is not null, check that the domain matches this
        // interceptor domain.
        //
        if (name != null && 
            !wildmatch(fileDomainChars,
                       name.getDomain().toCharArray(), 0, 0)) 
            return new HashSet(0);
                
        // Build a new empty Set.
        //
        final File[] list = wd.listFiles();
        final int len = list.length;
        final HashSet result = new HashSet(len+2);

        // check whether the parent directory of the mirrored directory 
        // matches.
        //
        addMBean(name,query,wd.getParentFile(),result);

        // check whether the mirrored directory matches.
        //
        addMBean(name,query,wd,result);

        // check whether the files and directory contained in the mirrored
        // directory match.
        //
        for (int i=0;i<len;i++) {
            addMBean(name,query,list[i],result);
        }

        // Finally return the result.
        //
        return result;
    }

    /**
     * Add a new ObjectName to the given <var>result</var> Set
     * if the virtual MBean identified by <var>file</var> matches
     * the given <var>pattern</var> and <var>query</var>.
     * @param pattern The ObjectName pattern to match with the faked MBean.
     * @param query The QueryExp to apply.
     * @param file The {@link java.io.File} associated with the 
     *        faked MBean.
     * @param result The Set in which to add the faked ObjectName if
     *   the virtual MBean identified by <var>file</var> matches
     *   the given <var>pattern</var> and <var>query</var>.
     */
    final void addName(final ObjectName pattern, final QueryExp query, 
                       final File file, final Set result) {
        try {
            if (file == null) return;

            // Get the faked ObjectName corresponding to file.
            //
            final ObjectName n = getName(file);

            // Check whether the file & name match the pattern & query
            //
            if ((!matches(n,pattern)) || (!query(n,file,query))) return; 

            // Pattern matched and query matched => add the ObjectName
            // to the result Set.
            //
            result.add(n);

        } catch (Exception x) {
            debug("queryNames", 
                  "Virtual MBean failed: " + x);
        }
    }

    // Parses the directory mirrored by this interceptor
    // in order to find the list of faked MBeans.
    //
    public final Set queryNames(final ObjectName name, 
                                final QueryExp query) {

        final File wd = fileDir();

        // No directory mirrored (interceptor stopped) => return an
        // empty Set.
        //
        if (wd == null) return new HashSet(0);

        // If name is not null, check that the domain matches this
        // interceptor domain.
        //
        if (name != null && 
            !wildmatch(fileDomainChars,
                       name.getDomain().toCharArray(), 0, 0)) 
            return new HashSet(0);
                
        // Build a new empty Set.
        //
        final File[] list = wd.listFiles();
        final int len = list.length;
        final HashSet result = new HashSet(len+2);

        // check whether the parent directory of the mirrored directory 
        // matches.
        //
        addName(name,query,wd.getParentFile(),result);

        // check whether the mirrored directory matches.
        //
        addName(name,query,wd,result);

        // check whether the files and directory contained in the mirrored
        // directory match.
        //
        for (int i=0;i<len;i++) {
            addName(name,query,list[i],result);
        }

        // Finally return the result.
        //
        return result;
    }   

    // Get the file domain associated with this interceptor.
    //
    public final String getDefaultDomain() {
        return fileDomain;
    }

    // Get the file domain associated with this interceptor.
    //
    public final String[] getDomains() {
        return new String[] { fileDomain };
    }

    // Parses the directory mirrored by this interceptor in order
    // to count the number of faked MBean.
    //
    public Integer getMBeanCount()  {

        final File wd = fileDir();
        if (wd == null) return new Integer(0);

        final File[] list = wd.listFiles();
        
        // The number of MBeans faked by this MBean interceptor is:
        //  1 MBean for each file and directory in the mirrored directory,
        // +1 MBean for the parent directory,
        // +1 MBean for the mirrored directory itself.
        //
        return new Integer(list.length+2);
    }

    // Get the file associated with the given name in order
    // to check if it corresponds to an MBean faked by this
    // interceptor.
    //
    public final boolean isRegistered(final ObjectName name) {
        try {
            // This will throw InstanceNotFoundException if the given name
            // does not correspond to any MBean faked by this interceptor.
            //
            final File file = getFile(name);

            // If we get here, it means that the name correspond to one of 
            // the MBeans we are faking => return true.
            //
            return true;
        } catch (Exception x) {
            
            // If we get here, it means we are not faking any MBean of that
            // name => return false.
            //
            return false;
        }
    }

    // Get the file associated with the given name and see if
    // it corresponds to the given className.
    //
    public final boolean isInstanceOf(final ObjectName name, 
                                      final String className) 
        throws InstanceNotFoundException {

        // This will throw InstanceNotFoundException if the given name
        // does not correspond to any MBean faked by this interceptor.
        //
        final File file = getFile(name);

        // We have only two types of MBean: Directory and Files...
        // Since the MBean class is faked, we can only perform a
        // string comparison with the given className....
        //
        if (file.isDirectory()) 
            return VirtualDirObject.equals(className);
        if (file.isFile())
            return VirtualFileObject.equals(className);
        return false;
    }

    // Always reject.
    //
    public final ObjectInstance registerMBean(final Object object, 
                                              final ObjectName name)
        throws InstanceAlreadyExistsException,
               MBeanRegistrationException, NotCompliantMBeanException {
        
        // We do not support creation of new files...
        //
        final RuntimeException x = 
            new UnsupportedOperationException(name.getDomain() + 
                            ": Can't register an MBean in that domain.");
        throw new
            MBeanRegistrationException(x,"Registration failed.");
    }

    // No faked MBean is a NotificationBroadcaster => Always reject.
    //
    public final void addNotificationListener(final ObjectName name, 
                                        final NotificationListener listener, 
                                        final NotificationFilter filter, 
                                        final Object handback)
        throws InstanceNotFoundException {

        throw new InstanceNotFoundException("No broadcaster by that name");

    }

    // No faked MBean is a NotificationBroadcaster => Always reject.
    //
    public final void addNotificationListener(final ObjectName name, 
                                        final ObjectName listener, 
                                        final NotificationFilter filter, 
                                        final Object handback)
        throws InstanceNotFoundException {

        throw new InstanceNotFoundException("No broadcaster by that name");
    }

    // No faked MBean is a NotificationBroadcaster => Always reject.
    //
    public final void removeNotificationListener(
					final ObjectName name,
					final NotificationListener listener)
        throws InstanceNotFoundException, ListenerNotFoundException {

        throw new InstanceNotFoundException("No broadcaster by that name");
    }

    // No faked MBean is a NotificationBroadcaster => Always reject.
    //
    public final void removeNotificationListener(
					final ObjectName name,
					final ObjectName listener)
	throws InstanceNotFoundException, ListenerNotFoundException {

        throw new InstanceNotFoundException("No broadcaster by that name");
    }

    // No faked MBean is a NotificationBroadcaster => Always reject.
    //
    public final void removeNotificationListener(
					final ObjectName name,
					final NotificationListener listener,
					final NotificationFilter filter,
					final Object handback)
       throws InstanceNotFoundException, ListenerNotFoundException {

        throw new InstanceNotFoundException("No broadcaster by that name");
    }

    // No faked MBean is a NotificationBroadcaster => Always reject.
    //
    public final void removeNotificationListener(
					final ObjectName name,
					final ObjectName listener,
					final NotificationFilter filter,
					final Object handback)
       throws InstanceNotFoundException, ListenerNotFoundException {

        throw new InstanceNotFoundException("No broadcaster by that name");
    }

    // Always reject.
    //
    public final void unregisterMBean(final ObjectName name)
        throws InstanceNotFoundException, MBeanRegistrationException {

        // We always show the content of the mirrored directory.
        //
        final RuntimeException x = 
            new UnsupportedOperationException(name.getDomain() + 
                            ": Can't unregister an MBean from that domain.");
        throw new
            MBeanRegistrationException(x,"Unregistration failed.");
    }

    /**
     * Get the requested <var>attribute</var> from the given 
     * <var>file</var> obtained from <var>name</var>.
     * @param name The ObjectName of the faked MBean
     * @param file The {@link java.io.File} associated with the 
     *        faked MBean.
     * @param attribute The attribute we want to get.
     */
    private final Object getAttribute(final ObjectName name, 
                                      final File file,
                                      final String attribute)
        throws MBeanException, AttributeNotFoundException, 
        InstanceNotFoundException, ReflectionException {
        
        try {
            // attribute must not be null
            //
            if (attribute == null) 
                throw new AttributeNotFoundException("null");

            // Extract the requested attribute from file
            //
            if (attribute.equals("Directory")) 
                return new Boolean(file.isDirectory());
            else if (attribute.equals("File"))
                return new Boolean(file.isFile());
            else if (attribute.equals("Read"))
                return new Boolean(file.canRead());
            else if (attribute.equals("Write"))
                return new Boolean(file.canWrite());
            else if (attribute.equals("Path"))
                return file.getPath();
            else if (attribute.equals("Size"))
                return new Long(file.length());

            // Unknown attribute
            //
            else 
                throw new AttributeNotFoundException("null");

        } catch (AttributeNotFoundException x) {
            throw x;
        } catch (JMRuntimeException j) {
            throw j;
        } catch (Exception x) {
            throw new MBeanException(x,"Failed to get " + attribute);
        }
    }

    // Get the file associated with the given name and then
    // get the requested attribute from that file.
    //
    public final Object getAttribute(final ObjectName name, 
                                     final String attribute)
        throws MBeanException, AttributeNotFoundException, 
        InstanceNotFoundException, ReflectionException {

        // This will throw InstanceNotFoundException if the given name
        // does not correspond to any MBean faked by this interceptor.
        //
        final File file = getFile(name);

        // Get the requested attribute from the retrieved file.
        //
        return getAttribute(name,file,attribute);
    }

    // Get the file associated with the given name and then
    // get the requested attributes from that file.
    //
    public final AttributeList getAttributes(final ObjectName name, 
                                             final String[] attributes)
        throws InstanceNotFoundException, ReflectionException {

        // This will throw InstanceNotFoundException if the given name
        // does not correspond to any MBean faked by this interceptor.
        //
        final File file = getFile(name);

        // If attributes is null, the get all attributes.
        //
        final String[] attn = (attributes==null?attributeNames:attributes);

        // Prepare the result list.
        //
        final int len = attn.length;
        final AttributeList list = new AttributeList(len);

        // Get each requested attribute.
        //
        for (int i=0;i<len;i++) {
            try {
                final Attribute a = 
                    new Attribute(attn[i],getAttribute(name,file,attn[i]));
                list.add(a);
            } catch (Exception x) {
                // Skip the attribute that couldn't be obtained.
                //
                debug("getAttributes",name + ": Attribute " + attn[i] +
                      " not found.");
            }
        }

        // Finally return the result.
        //
        return list; 
    }

    // Try to get the file associated with the given name, and then
    // always fail: there are no writable attributes.
    //
    public final void setAttribute(final ObjectName name, 
                                   final Attribute attribute)
        throws InstanceNotFoundException, AttributeNotFoundException,
        InvalidAttributeValueException, MBeanException, ReflectionException {

        // This will throw InstanceNotFoundException if the given name
        // does not correspond to any MBean faked by this interceptor.
        //
        final File file = getFile(name);

        // Now we will always fail:
        // Either because the attribute is null or because it is not
        // accessible (or does not exist).
        //
        final String attname = (attribute==null?null:attribute.getName());
        if (attname == null) {
            final RuntimeException r = 
                new IllegalArgumentException("Attribute name cannot be null");
            throw new RuntimeOperationsException(r, 
                "Exception occurred trying to invoke the setter on the MBean");
        }  
        
        // This is a hack: we call getAttribute in order to generate an
        // AttributeNotFoundException if the attribute does not exist.
        //
        Object val = getAttribute(name,file,attname);

        // If we reach this point, we know that the requested attribute
        // exists. However, since all attributes are read-only, we throw
        // an AttributeNotFoundException.
        //
        throw new AttributeNotFoundException(attname + " not accessible");
    }


    // Try to get the file associated with the given name, and then
    // always return an empty list: there are no writable attributes.
    //
    public final AttributeList setAttributes(final ObjectName name, 
                                             final AttributeList attributes)
        throws InstanceNotFoundException, ReflectionException  {

        // This will throw InstanceNotFoundException if the given name
        // does not correspond to any MBean faked by this interceptor.
        //
        final File file = getFile(name);

        // Now return an empty list.
        //
        return new AttributeList(0);
    }

    // Get the file associated with the given name and then
    // check wheter the requested operation can be applied on that file.
    // Note that we implement only one operation, called cd(), which
    // is only available on directory object (faked VirtualDirObject MBeans).
    //
    public final Object invoke(final ObjectName name, 
                               final String operationName, 
                               final Object params[], 
                               final String signature[])
        throws InstanceNotFoundException, MBeanException, 
        ReflectionException {

        // This will throw InstanceNotFoundException if the given name
        // does not correspond to any MBean faked by this interceptor.
        //
        final File file = getFile(name);

        // Check that operation name is not null.
        //
        if (operationName == null) {
            final RuntimeException r = 
              new IllegalArgumentException("Operation name  cannot be null");
            throw new RuntimeOperationsException(r, 
            "Exception occurred trying to invoke the operation on the MBean");
        } 

        // Makes it possible to call attribute getters through invoke.
        //
        if (operationName.startsWith("get") && 
            (params==null || params.length==0) &&
            (signature==null || signature.length==0)) {
            try {
                return getAttribute(name,file,operationName.substring(3));
            } catch (AttributeNotFoundException x) {
                throw new ReflectionException(
                          new NoSuchMethodException(operationName), 
                          "The operation with name " + operationName + 
                          " could not be found");
            }
        }

        // We have only one operation, called "cd", and implemented
        // only for directory objects...
        //
        if (operationName.equals("cd") && file.isDirectory()) {

            // Ok. Got a directory, need to perform "cd".
            //
            try {
                
                // perform cd.
                //
                cd(file);

                // No result (cd returns void) => return null.
                //
                return null;
            } catch (Exception x) {
                // cd operation failed.
                //
                throw new 
                    MBeanException(x,"Failed to invoke " + operationName);
            }
        } else {

            // No operation of that name...
            //
            throw new ReflectionException(
                          new NoSuchMethodException(operationName), 
                          "The operation with name " + operationName + 
                          " could not be found");
        }
    }

    // Get the file associated with the given name and then
    // return the associated MBeanInfo
    //
    public final MBeanInfo getMBeanInfo(final ObjectName name)
        throws InstanceNotFoundException, IntrospectionException, 
        ReflectionException   {

        // This will throw InstanceNotFoundException if the given name
        // does not correspond to any MBean faked by this interceptor.
        //
        final File file = getFile(name);

        // We got only two faked classes:
        //    VirtualDirObject  => dirInfo
        //    VirtualFileObject => fileInfo
        //
        if (file.isDirectory()) return dirInfo;
        return fileInfo;
    }

    // This method is actually meaningless in this context...
    //
    public final ClassLoader getClassLoader(final ObjectName loaderName)
        throws InstanceNotFoundException {
	return null;
    }

    // This method is actually meaningless in this context...
    //
    public final ClassLoader getClassLoaderFor(final ObjectName name)
        throws InstanceNotFoundException {
	return getMBeanClassLoader(name);
    }

    // This method is actually meaningless in this context...
    //
    public final ClassLoader getMBeanClassLoader(final ObjectName name)
        throws InstanceNotFoundException {

        // This will throw InstanceNotFoundException if the given name
        // does not correspond to any MBean faked by this interceptor.
        //
        final File file = getFile(name);

        // Return the system class loader, since our faked MBean are
        // backed up by File objects...
        //
        return file.getClass().getClassLoader();
    }

    //
    // Debug
    //

    private static final boolean debugOn = false;
    private static final String debugTag = "FileMBeanServerInterceptor";

    private final static boolean isDebugOn() {
        return debugOn;
    }

    private static final void debug(String func, String info) {
        if (isDebugOn())
            System.out.println(debugTag + "::" + func + "::" + info);
    }

    private static final void debug(String func, Throwable t) {
        if (isDebugOn()) {
            System.out.println(debugTag + "::" + func + "::" + t.getMessage());
            t.printStackTrace();
        }
    }
}
