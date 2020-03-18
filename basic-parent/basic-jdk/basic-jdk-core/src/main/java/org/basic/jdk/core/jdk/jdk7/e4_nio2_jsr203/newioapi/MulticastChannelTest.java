package org.basic.jdk.core.jdk.jdk7.e4_nio2_jsr203.newioapi;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.StandardProtocolFamily;
import java.net.StandardSocketOptions;
import java.nio.channels.DatagramChannel;
import java.nio.channels.MembershipKey;

import org.junit.Test;

/**
 * <pre>
 *
 * </pre>
 */
public class MulticastChannelTest {
    @Test
    public void MulticastChannel() throws Exception {
        NetworkInterface networkInterface = NetworkInterface.getByName("hme0");

        DatagramChannel dc = DatagramChannel.open(StandardProtocolFamily.INET).setOption(StandardSocketOptions.SO_REUSEADDR, true)
                .bind(new InetSocketAddress(5000)).setOption(StandardSocketOptions.IP_MULTICAST_IF, networkInterface);

        InetAddress group = InetAddress.getByName("225.4.5.6");

        MembershipKey key = dc.join(group, networkInterface);
    }
}
