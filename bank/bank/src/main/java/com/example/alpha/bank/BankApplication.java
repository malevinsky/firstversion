package com.example.alpha.bank;

import com.example.alpha.bank.controllers.MainController;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;


@SpringBootApplication
public class BankApplication {

    //Добавляем url за сегодня и вчера. Ссылки придётся обновлять вручную, возможно это можно как-то автоматизировать.
	public static final String POSTS_API_URL = "https://openexchangerates.org/api/latest.json?app_id=6e30f6727b474120b5816cef72283cfd";
    public static final String POSTS_API_URL_YESTERDAY = "https://openexchangerates.org/api/historical/2021-01-04.json?app_id=6e30f6727b474120b5816cef72283cfd";

    public float aaa = 0;

	public static void main(String[] args) throws IOException, InterruptedException {

	    //Считываем сегодняшние JSON'ы.
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.GET()
				.header("accept", "application/json")
				.uri(URI.create(POSTS_API_URL))
				.build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		//Считываем вчерашние JSON'ы.
        HttpClient client_y = HttpClient.newHttpClient();
        HttpRequest request_y = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(POSTS_API_URL_YESTERDAY))
                .build();
        HttpResponse<String> response_y = client_y.send(request_y, HttpResponse.BodyHandlers.ofString());

        //Преобразуем  сегодняшние JSON'ы в JAVA объект.
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
		Post posts = mapper.readValue(response.body(), Post.class);

        //Преобразуем  вчерашние JSON'ы в JAVA объект.
        ObjectMapper mapper_y = new ObjectMapper();
        mapper_y.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper_y.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        PostYesterday posts_y = mapper.readValue(response_y.body(), PostYesterday.class);


        Rates rubles = posts.rates;
        String str = rubles.toString();
        String str2 = str.replaceAll("\\s","");
        float today = Float.parseFloat(str2);
        //System.out.println(today);

        Rates rubles_y = posts_y.rates;
        String str_y = rubles_y.toString();
        String str2_y = str_y.replaceAll("\\s","");
        float yesterday = Float.parseFloat(str2_y);
        
        SpringApplication.run(BankApplication.class, args);

	}
}
