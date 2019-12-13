package com.magi.mydemos.http;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

public class HttpUtils {

    public interface OnResponseListener{
        void onSuccess(String content);
        void onFail(Exception e);
    }

    private static Handler handler = new Handler(Looper.getMainLooper());

    public static void doGet(final Context context, final String urlString, final OnResponseListener listener){
        new Thread(new Runnable() {
            private InputStream inputStream;
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

                    SSLContext sslContext = SSLContext.getInstance("TLS");

                    X509Certificate certificate = getCert(context);
                    TrustManager[] trustManagers = {new MyX509TrustManager(certificate)};
                    sslContext.init(null, trustManagers, new SecureRandom());
                    connection.setSSLSocketFactory(sslContext.getSocketFactory());

                    connection.setHostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            HostnameVerifier defaultHostnameVerifier = HttpsURLConnection.getDefaultHostnameVerifier();
                            return defaultHostnameVerifier.verify("*.12306.cn",session);
                        }
                    });

                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setReadTimeout(2000);
                    connection.setConnectTimeout(2000);
                    connection.connect();

                    inputStream = connection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(inputStream);
                    final char[] chars = new char[2048];
                    int len;
                    final StringBuilder builder = new StringBuilder();
                    while ((len=reader.read(chars))!=-1){
                        builder.append(new String(chars,0,len));
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onSuccess(builder.toString());
                        }
                    });
                } catch (IOException | NoSuchAlgorithmException | KeyManagementException e) {
                    e.printStackTrace();
                } finally {
                    try{
                        if (inputStream !=null){
                            inputStream.close();
                        }
                    } catch (IOException e){
                        //ignore 已经进行过非空判断了
                    }
                }
            }
        }).start();

    }

    private static X509Certificate getCert(Context context) {
        try {
            InputStream inputStream = context.getAssets().open("srca.cer");
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            return (X509Certificate)certificateFactory.generateCertificate(inputStream);
        } catch (IOException | CertificateException e) {
            e.printStackTrace();
            return null;
        }
    }
}
