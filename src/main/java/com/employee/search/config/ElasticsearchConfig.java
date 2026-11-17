
package com.employee.search.config;

import java.io.File;

import javax.net.ssl.SSLContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.transport.TransportUtils;

@Configuration
public class ElasticsearchConfig {

    @Value("${elasticsearch.url}")
    private String url;

    @Value("${elasticsearch.username}")
    private String username;

    @Value("${elasticsearch.password}")
    private String password;

    @Value("${elasticsearch.certificate}")
    private String certificate;

    @Bean(destroyMethod = "close")
    public ElasticsearchClient elasticsearchClient()
            throws Exception {

        File caCertificate =
                new File("src/main/resources/" + certificate);

        SSLContext sslContext =
                TransportUtils.sslContextFromHttpCaCrt(
                        caCertificate
                );

        return ElasticsearchClient.of(client -> client
                .host(url)
                .usernameAndPassword(username, password)
                .sslContext(sslContext)
        );
    }
}
