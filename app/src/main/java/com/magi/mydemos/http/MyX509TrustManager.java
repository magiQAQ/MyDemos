package com.magi.mydemos.http;


import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;


public class MyX509TrustManager implements X509TrustManager {

    private X509Certificate serverCert;

    MyX509TrustManager(X509Certificate serverCert) {
        this.serverCert = serverCert;
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        for (X509Certificate certificate : chain){
            certificate.checkValidity();
            try {
                certificate.verify(certificate.getPublicKey());
            } catch (Exception e) {
                throw new CertificateException(e);
            }
        }
    }

    @Override
    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
