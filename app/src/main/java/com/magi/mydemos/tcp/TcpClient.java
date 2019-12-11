package com.magi.mydemos.tcp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class TcpClient {
    private Scanner scanner;

    public TcpClient(){
        scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
    }
    public void start(){
        try{
            Socket socket = new Socket("192.168.1.107",9090);
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

            //输出服务端发送的数据
            new Thread(){
                @Override
                public void run() {
                    String line;
                    try {
                        while ((line = bufferedReader.readLine())!=null){
                            System.out.println(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

            while (true) {
                String s = scanner.next();
                bufferedWriter.write(s);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
