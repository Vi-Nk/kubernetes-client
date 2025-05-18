package dev.vink.k8sclient.auth;

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

public class TLSAuthProvider implements AuthProvider {
    String caData;
    String clientData;
    String clientKey;
    SSLSocketFactory socketFactory = null;

    public TLSAuthProvider(String caData, String clientData, String clientKey) {
        this.caData = caData;
        this.clientData = clientData;
        this.clientKey = clientKey;
    }

    @Override
    public void addAuth(HttpURLConnection conn) {
        // DoNothing
    }

    @Override
    public SSLSocketFactory getSslSocketFactory() throws Exception {
        if (socketFactory != null)
            return socketFactory;

        // Decode Base64-encoded CA and client data
        byte[] caDataBytes = Base64.decodeBase64(caData);
        byte[] clientDataBytes = Base64.decodeBase64(clientData);
        byte[] clientKeyBytes = Base64.decodeBase64(clientKey);

        // Create truststore and trust CA cert
        X509Certificate caCertificate = (X509Certificate) CertificateFactory.getInstance("X.509")
                .generateCertificate(new ByteArrayInputStream(caDataBytes));
        KeyStore javaKeyStore = KeyStore.getInstance("JKS");
        javaKeyStore.load(null);
        javaKeyStore.setCertificateEntry("caData", caCertificate);
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(javaKeyStore);

        // Create Keystore and add client cert
        X509Certificate clientCertificate = (X509Certificate) CertificateFactory.getInstance("X.509")
                .generateCertificate(new ByteArrayInputStream(clientDataBytes));

        PrivateKey key = parseECPrivateKey(clientKeyBytes);
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(null, null);
        keyStore.setKeyEntry("clientData", key, "".toCharArray(), new Certificate[] { clientCertificate });
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(keyStore, "".toCharArray());

        // Init SSL context
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), new SecureRandom());
        socketFactory = sslContext.getSocketFactory();
        return socketFactory;
    }

    private static PrivateKey parseECPrivateKey(byte[] keyBytes) throws Exception {
        try (PemReader pemReader = new PemReader(new StringReader(new String(keyBytes)))) {
            PemObject pemObject = pemReader.readPemObject();
            byte[] keyContent = pemObject.getContent();

            // Check if the key is in traditional OpenSSL EC format
            if (pemObject.getType().equals("EC PRIVATE KEY")) {
                // Parse the key using BouncyCastle's ASN.1 utilities
                org.bouncycastle.asn1.sec.ECPrivateKey ecPrivateKey = org.bouncycastle.asn1.sec.ECPrivateKey
                        .getInstance(keyContent);

                // Include domain parameters (e.g., secp256r1) in the AlgorithmIdentifier
                org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier = new org.bouncycastle.asn1.x509.AlgorithmIdentifier(
                        org.bouncycastle.asn1.x9.X9ObjectIdentifiers.id_ecPublicKey,
                        org.bouncycastle.asn1.sec.SECObjectIdentifiers.secp256r1); // Adjust curve as needed

                // Wrap the EC private key in a PKCS#8 structure
                PrivateKeyInfo privateKeyInfo = new PrivateKeyInfo(algorithmIdentifier, ecPrivateKey);

                // Convert to Java's PrivateKey using KeyFactory
                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyInfo.getEncoded());
                return KeyFactory.getInstance("EC").generatePrivate(keySpec);
            } else {
                throw new IllegalArgumentException("Unsupported key format: " + pemObject.getType());
            }
        }
    }
}
