package com.example.codingchallenge.data;

import com.example.codingchallenge.data.models.ArticlesResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {
    @GET("398641f3-1105-4e9e-b089-b3aa186d758b")
    Observable<ArticlesResponse> getArticles();
}
