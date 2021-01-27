package com.example.alpha.bank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
public class PostYesterday {

    private String disclaimer;
    private String license;
    private Long timestamp;
    private String base;
    @JsonIgnoreProperties
    public Rates rates;


    @Override
    public String toString() {
        return "Post{" +
                "disclaimer='" + disclaimer + '\'' +
                ", license='" + license + '\'' +
                ", timestamp=" + timestamp +
                ", base='" + base + '\'' +
                ", rates=" + rates +
                '}';
    }
}
