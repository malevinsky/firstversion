package com.example.alpha.bank.controllers;

import com.example.alpha.bank.*;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 *  created by malevinsky
 *  email: malevwork@gmail.com
 *  telegram: @theos_deus
 *  date: 06.02.2021
 */

@Controller
public class MainController {
    /**
     * Я использовала две ссылки - за сегодня и за определенную дату. Чтобы узнать курс за вчера нужно вписать в POSTS_API_URL_YESTERDAY нужное число.
     */
    public static final String POSTS_API_URL = "https://openexchangerates.org/api/latest.json?app_id=6e30f6727b474120b5816cef72283cfd";
    public static final String POSTS_API_URL_YESTERDAY = "https://openexchangerates.org/api/historical/2021-01-05.json?app_id=6e30f6727b474120b5816cef72283cfd";

    @GetMapping("/")
    public String rich(Model model) throws IOException, InterruptedException {
        /**
         * Контроллер. В зависимости от чисел выводим нужную страницу html с гифкой;
         * REST API гифок - https://developers.giphy.com;
         * В html-файлах я использовала фреймворк Bootstrap.
         */
        float fin = 0;
        fin = MainController.http(fin);
        if (fin == 1) {
            model.addAttribute("name", fin);
            return "poor";
        }
        else {
            model.addAttribute("name", fin);
            return "rich";
        }
    }
    public static int http(float fin) throws IOException, InterruptedException {

        float today = func(POSTS_API_URL);
        float yesterday = func(POSTS_API_URL_YESTERDAY);
        return whichisbigger(today, yesterday);
    }
    public static float func(String API_URL) throws IOException, InterruptedException {
        /**
         * По смыслу разделю эту функцию на три части:
         * 1. Нам нужно достать данные из https://openexchangerates.org
         * 2. Преобразовываем http запрос в объект Post и Rates с информацией из json'a(это можно сделать проще через JSONObject, но я решила попробовать сделать более сложным путём и создать классы с переменными)
         * 3. Когда мы получаем значения из RUB, нам нужно преобразовать из в float.
         */
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(API_URL))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        Post posts = mapper.readValue(response.body(), Post.class);

        Rates rubles = posts.rates;
        String str = rubles.toString();
        String str2 = str.replaceAll("\\s","");
        float number = Float.parseFloat(str2);
        System.out.println("Сегодня курс рубля к доллару: " + number);

        return number;
    }

    public static int whichisbigger(float today, float yesterday) {
        /**
         * Сравниваем курс за сегодня и завтра.
         */
        if (today > yesterday)
            return 1;
        else
            return 0;
    }


}