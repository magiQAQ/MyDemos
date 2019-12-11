package com.magi.mydemos.tcp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class MessagePool {

    private static MessagePool instance = new MessagePool();

    private LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

    public static MessagePool getInstance() {
        return instance;
    }

    private MessagePool(){

    }

    public void start(){
        new Thread(){
            @Override
            public void run() {
                while (true){
                    try{
                        String message = queue.take();
                        notifyMessageComing(message);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private void notifyMessageComing(String message) {
        for (MessageComingListener listener : listeners){
            listener.onMessageComing(message);
        }
    }

    public interface MessageComingListener{
        void onMessageComing(String message);
    }

    private List<MessageComingListener> listeners = new ArrayList<>();

    public void addMessageComingListener(MessageComingListener listener){
        listeners.add(listener);
    }

    public void sendMessage(String message){
        try {
            queue.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
