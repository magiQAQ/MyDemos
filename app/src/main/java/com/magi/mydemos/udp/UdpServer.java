package com.magi.mydemos.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class UdpServer {

    private InetAddress inetAddress;
    private int port = 7777;
    private DatagramSocket socket;

    private Scanner scanner;

    public UdpServer() {
        try {
            inetAddress = InetAddress.getLocalHost();
            socket = new DatagramSocket(port, inetAddress);

            scanner = new Scanner(System.in);
            scanner.useDelimiter("\n");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        while (true) {

            try {
                byte[] buf = new byte[1024];
                DatagramPacket receivedPacket = new DatagramPacket(buf, buf.length);
                socket.receive(receivedPacket);

                InetAddress address = receivedPacket.getAddress();
                int port = receivedPacket.getPort();
                String clientMsg = new String(receivedPacket.getData(), 0, receivedPacket.getLength());

                System.out.println("address = " + address + " ,port = " + port + " ,msg = " + clientMsg);

                String returnedMsg = scanner.next();
                byte[] returnedMsgBytes = returnedMsg.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(returnedMsgBytes,
                        returnedMsgBytes.length,receivedPacket.getSocketAddress());
                socket.send(sendPacket);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


}
