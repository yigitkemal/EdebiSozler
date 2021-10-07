package com.example.edebisozler.service;

import com.example.edebisozler.model.Example;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EdebiSozlerAPI {

    @GET("all_quotes.php")
    Call<Example> getData();

    @GET("all_quotes.php")
    Call<Example> getFavorites(String quotesUtterer, String quotesText);

}
