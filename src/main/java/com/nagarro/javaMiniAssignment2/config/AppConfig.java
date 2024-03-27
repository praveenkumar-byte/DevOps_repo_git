package com.nagarro.javaMiniAssignment2.config;

import com.nagarro.javaMiniAssignment2.strategy.SortingStrategyFactory;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.TimeUnit;

@Configuration
public class AppConfig {
    @Value("${app.randomUserUrl}")
    private String api1Url;

    @Value("${app.NationalityUrl}")
    private String api2Url;

    @Value("${app.genderUrl}")
    private String api3Url;

    @Bean
    @Qualifier("randomUserWebClient")
    public WebClient randomUserWebClient() {
        return WebClient.builder()
                .baseUrl(api1Url)
                .clientConnector(new ReactorClientHttpConnector(getTimeout(2000)))
                .build();
    }

    @Bean
    @Qualifier("nationalizeWebClient")
    public WebClient nationalizeWebClient() {
        return WebClient.builder()
                .baseUrl(api2Url)
                .clientConnector(new ReactorClientHttpConnector(getTimeout(2000)))
                .build();
    }

    @Bean
    @Qualifier("genderizeWebClient")
    public WebClient genderizeWebClient() {
        return WebClient.builder()
                .baseUrl(api3Url)
                .clientConnector(new ReactorClientHttpConnector(getTimeout(2000)))
                .build();
    }

    private HttpClient getTimeout(int time) {
        return HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, time)
                .doOnConnected(conn -> conn
                        .addHandler(new ReadTimeoutHandler(2000, TimeUnit.MILLISECONDS))
                        .addHandler(new WriteTimeoutHandler(2000, TimeUnit.MILLISECONDS)));
    }
    @Bean
    public SortingStrategyFactory sortingStrategyFactory() {
        return new SortingStrategyFactory();
    }
}
