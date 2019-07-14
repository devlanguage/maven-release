package org.basic.gui.swing.iplocation;

public class TestIP {

    
    public static void main(String[] args) {

        IPSeeker seeker = IPSeeker.getInstance();
        String ip1 = "11.161.04.1";
        System.out.println(seeker.getAddress(ip1));

    }
}