package net.aulang.account.configuration;

import net.aulang.account.factory.HttpsURLConnectionFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfiguration {

    @Bean
    @Qualifier("trustStoreSslSocketFactory")
    public SSLConnectionSocketFactory trustStoreSslSocketFactory() {
        return new SSLConnectionSocketFactory(HttpsURLConnectionFactory.sslSocketFactory(), HttpsURLConnectionFactory.hostnameVerifier());
    }

}
