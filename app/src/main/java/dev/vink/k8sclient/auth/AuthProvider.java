package dev.vink.k8sclient.auth;

import java.net.HttpURLConnection;

import javax.net.ssl.SSLSocketFactory;

public interface AuthProvider {
    void addAuth(HttpURLConnection conn);
    SSLSocketFactory getSslSocketFactory() throws Exception;
}
