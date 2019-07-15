
/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 2000-2007 Sun Microsystems, Inc. All rights reserved.
 * 
 * The contents of this file are subject to the terms of either the GNU General Public License
 * Version 2 only ("GPL") or the Common Development and Distribution License ("CDDL") (collectively,
 * the "License"). You may not use this file except in compliance with the License. You can obtain a
 * copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html or
 * mq/legal/LICENSE.txt. See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * When distributing the software, include this License Header Notice in each file and include the
 * License file at mq/legal/LICENSE.txt. Sun designates this particular file as subject to the
 * "Classpath" exception as provided by Sun in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information: "Portions Copyrighted
 * [year] [name of copyright owner]"
 * 
 * Contributor(s):
 * 
 * If you wish your version of this file to be governed by only the CDDL or only the GPL Version 2,
 * indicate your decision by adding "[Contributor] elects to include this software in this
 * distribution under the [CDDL or GPL Version 2] license." If you don't indicate a single choice of
 * license, a recipient has the option to distribute your version of this file under either the
 * CDDL, the GPL Version 2 or to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL Version 2 license, then the
 * option applies only if the new code is made subject to such option by the copyright holder.
 */

/*
 * @(#)SimpleClient.java 1.4 07/02/07
 */

import java.util.HashMap;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import com.sun.jndi.rmi.registry.RegistryContext;
import com.sun.messaging.AdminConnectionFactory;
import com.sun.messaging.jms.management.server.BrokerAttributes;
import com.sun.messaging.jms.management.server.MQObjectName;

public class SimpleClient {

    public static void main(String[] args) {

        try {
            AdminConnectionFactory acf;

            /*
             * Create admin connection factory and connect to JMX Connector server using
             * administrator username/password. A JMX connector client object is obtained from this.
             */
            acf = new AdminConnectionFactory();
            HashMap env = new HashMap();
            env.put(JMXConnector.CREDENTIALS, new String[] { "admin", "admin" });
            // JMXConnector jmxc = acf.createConnection("admin", "admin");
            // System.out.println(jmxc.toString());
            // JMXConnector jmxc = acf.createConnection();
            JMXConnector jmxc = JMXConnectorFactory
                    .connect(
                            new JMXServiceURL(
                                    "service:jmx:rmi://localhost:7676/stub/rO0ABXNyAC5qYXZheC5tYW5hZ2VtZW50LnJlbW90ZS5ybWkuUk1JU2VydmVySW1wbF9TdHViAAAAAAAAAAICAAB4cgAaamF2YS5ybWkuc2VydmVyLlJlbW90ZVN0dWLp/tzJi+FlGgIAAHhyABxqYXZhLnJtaS5zZXJ2ZXIuUmVtb3RlT2JqZWN002G0kQxhMx4DAAB4cHc6AAtVbmljYXN0UmVmMgAADzE5Mi4xNjguMTIxLjEyOAAABOUAAAAAAAAAAPToqxcAAAEXfDSFv4AAAHg="),
                            env);

            /*
             * Get MBeanServer interface.
             */
            MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

            /*
             * Create object name of broker config MBean.
             */
            ObjectName objName = new ObjectName(MQObjectName.BROKER_MONITOR_MBEAN_NAME);

            /*
             * Get attributes: InstanceName Version
             */
            System.out.println("Broker Instance Name = "
                    + mbsc.getAttribute(objName, BrokerAttributes.INSTANCE_NAME));
            System.out.println("Broker Version = "
                    + mbsc.getAttribute(objName, BrokerAttributes.VERSION));

            jmxc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
