package com.aiportal.student_portal.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class AppProperties {

   
    @Value("${app.recommender.url}")
    private String recommenderUrl;

   
    public String getRecommenderUrl() {
        return recommenderUrl;
    }

    
    
    @PostConstruct
    public void init() {
        System.out.println("Recommender URL loaded: " + recommenderUrl);
    }
}
