package com.magi.mydemos.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class ClientTask extends Thread implements MessagePool.MessageComingListener {

    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public ClientTask(Socket socket) {
        try {
            this.socket = socket;
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        try {
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println("read = " + line);
                //转发消息至其他socket
                MessagePool.getInstance().sendMessage(line);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onMessageComing(String message) {
        try {
            outputStream.write(message.getBytes());
            outputStream.write("\n".getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
