package com.magi.mydemos.tcp;

import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {
    public void start(){
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(9090);
            MessagePool.getInstance().start();

            while (true) {
                //从连接请求队列中去除一个连接
                Socket socket = serverSocket.accept();

                System.out.println("ip = " + socket.getInetAddress().getHostAddress() + " ,port = " + socket.getPort() + " is online...");

                ClientTask clientTask = new ClientTask(socket);
                MessagePool.getInstance().addMessageComingListener(clientTask);
                clientTask.start();
            }


        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
