package com.magi.mydemos.tcp;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class TcpClientBiz {

    private Scanner scanner;
    private InputStream inputStream;
    private OutputStream outputStream;
    private OnMessageComingListener listener;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Socket socket;

    public interface OnMessageComingListener {
        void onMsgComing(String message);

        void onError(Exception ex);
    }

    public void setOnMsgComingListener(OnMessageComingListener listener) {
        this.listener = listener;
    }

    public TcpClientBiz() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    socket = new Socket("10.0.2.2", 9090);
                    inputStream = socket.getInputStream();
                    outputStream = socket.getOutputStream();
                    readServerMessage();
                } catch (final IOException e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (listener != null) {
                                listener.onError(e);
                            }
                        }
                    });
                }

            }
        }.start();

        scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
    }

    private void readServerMessage() throws IOException {
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            final String finalLine = line;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (listener != null) {
                        listener.onMsgComing(finalLine);
                    }
                }
            });
        }
    }

    public void sendMessage(final String msg) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                    bufferedWriter.write(msg);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onDestroy() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
