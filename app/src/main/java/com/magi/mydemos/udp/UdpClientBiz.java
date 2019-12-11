package com.magi.mydemos.udp;


import androidx.annotation.NonNull;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UdpClientBiz {
    private String serverIp = "192.168.1.6";
    private InetAddress serverAddress;
    private int serverPort = 7777;
    private DatagramSocket socket;

    public UdpClientBiz(){
        try {
            serverAddress = InetAddress.getByName(serverIp);
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public interface OnMessageReturnedListener{
        void onMessageReturned(String message);
        void onException(Exception exception);
    }

    public void sendMessage(final String message, @NonNull final OnMessageReturnedListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    byte[] clientMsgBytes = message.getBytes();
                    DatagramPacket clientPacket = new DatagramPacket(clientMsgBytes,
                            clientMsgBytes.length,serverAddress,serverPort);
                    socket.send(clientPacket);

                    //receive message
                    byte[] buf = new byte[1024];
                    DatagramPacket serverMsgPacket = new DatagramPacket(buf,buf.length);
                    socket.receive(serverMsgPacket);
                    String serverMsg = new String(serverMsgPacket.getData(), 0, serverMsgPacket.getLength());

                    listener.onMessageReturned(serverMsg);
                } catch (IOException e) {
                    listener.onException(e);
                }
            }
        }).start();
    }

    public void onDestory(){
        if (socket !=null){
            socket.close();
        }
    }


}
