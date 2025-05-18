package dev.vink.k8sclient.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import dev.vink.k8sclient.auth.AuthProvider;
import dev.vink.k8sclient.auth.TLSAuthProvider;

public class KubernetesHttpClient {
    private final String apiUrl;
    private final SSLSocketFactory sslSocketFactory;

    public KubernetesHttpClient(String apiUrl, SSLSocketFactory sslSocketFactory) {
        this.apiUrl = apiUrl;
        this.sslSocketFactory = sslSocketFactory;
    }

    public String get(String endpoint) {
        HttpsURLConnection conn = null;
        try {
            URL url = new URL(apiUrl + endpoint);
            conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (sslSocketFactory != null) {
                conn.setSSLSocketFactory(sslSocketFactory);
            }
            int code = conn.getResponseCode();
            if (code < 200 || code > 299) {
                throw new IOException("Server returned error code: " + code);
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder resp = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null)
                resp.append(line);
            in.close();
            return resp.toString();

        } catch (MalformedURLException ex) {
            System.err.println("Exception : " + ex.getLocalizedMessage());
        } catch (IOException ex) {
            System.err.println("Exception : " + ex.getLocalizedMessage());
        } catch (Exception ex) {
            System.err.println("Exception : " + ex.getLocalizedMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.disconnect();
                } catch (Exception ex) {
                    System.err.println("Exception : " + ex.getLocalizedMessage());
                }
            }
        }
        return null;
    }
}
