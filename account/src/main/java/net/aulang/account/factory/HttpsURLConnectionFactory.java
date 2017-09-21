package net.aulang.account.factory;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.jasig.cas.client.ssl.HttpURLConnectionFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.security.cert.X509Certificate;

/**
 * HTTP连接工厂，忽略HTTPS证书
 */
public class HttpsURLConnectionFactory implements HttpURLConnectionFactory {
    @Override
    public HttpURLConnection buildHttpURLConnection(URLConnection conn) {
        if (conn instanceof HttpsURLConnection) {
            HttpsURLConnection httpsConnection = (HttpsURLConnection) conn;
            httpsConnection.setSSLSocketFactory(sslSocketFactory());
            httpsConnection.setHostnameVerifier(hostnameVerifier());
        }
        return (HttpURLConnection) conn;
    }

    public static ClientHttpRequestFactory clientHttpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory(httpClient());
    }

    public static CloseableHttpClient httpClient() {
        SSLConnectionSocketFactory sslFactory = new SSLConnectionSocketFactory(sslContext(),
                hostnameVerifier());
        return HttpClients.custom().setSSLSocketFactory(sslFactory).build();
    }

    public static SSLSocketFactory sslSocketFactory() {
        return sslContext().getSocketFactory();
    }

    public static SSLContext sslContext() {
        try {
            return SSLContexts.custom().loadTrustMaterial(null, trustStrategy()).build();
        } catch (Exception e) {
            return SSLContexts.createDefault();
        }
    }

    public static TrustStrategy trustStrategy() {
        return (X509Certificate[] chain, String authType) -> true;
    }

    public static HostnameVerifier hostnameVerifier() {
        return (hostname, sslSession) -> true;
    }
}