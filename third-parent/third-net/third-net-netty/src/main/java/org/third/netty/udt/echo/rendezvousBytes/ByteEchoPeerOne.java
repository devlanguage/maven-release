package org.third.netty.udt.echo.rendezvousBytes;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.third.netty.udt.echo.rendezvous.Config;

import io.netty.util.internal.SocketUtils;

/**
 * UDT Byte Stream Peer
 * <p/>
 * Sends one message when a connection is open and echoes back any received data
 * to the server. Simply put, the echo client initiates the ping-pong traffic
 * between the echo client and server by sending the first message to the
 * server.
 * <p/>
 */
public class ByteEchoPeerOne extends ByteEchoPeerBase {

    public ByteEchoPeerOne(int messageSize, SocketAddress myAddress, SocketAddress peerAddress) {
        super(messageSize, myAddress, peerAddress);
    }

    public static void main(String[] args) throws Exception {
        final int messageSize = 64 * 1024;
        final InetSocketAddress myAddress = SocketUtils.socketAddress(Config.hostOne, Config.portOne);
        final InetSocketAddress peerAddress = SocketUtils.socketAddress(Config.hostTwo, Config.portTwo);
        new ByteEchoPeerOne(messageSize, myAddress, peerAddress).run();
    }
}
