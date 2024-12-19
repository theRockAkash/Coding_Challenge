package com.example.codingchallenge.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.codingchallenge.data.models.Article;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.qualifiers.ApplicationContext;

/**
 * Preference class to manage the favourite articles locally and cache response from API
 * to load it later when there is no network
 */
public class SharedPreferenceManager {

    private static final String PREF_NAME = "article_prefs";
    public static final String FAVOURITE_ARTICLE_LIST_KEY = "fav_article_list";
    public static final String ARTICLE_LIST_KEY = "article_list";
    private final SharedPreferences sharedPreferences;
    private final Gson gson;

    @Inject
    public SharedPreferenceManager(@ApplicationContext Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.gson = new Gson();
    }

    /**
     * Function to save the favourite list of articles
     */
    public void saveArticleList(List<Article> articleList,String key) {
        // Convert the list of articles to JSON
        String json = gson.toJson(articleList);

        // Save the JSON string in SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, json);
        editor.apply();
    }

    /**
     * Function to get the list of articles from SharedPreferences
     * @return List of Article
     */
    public List<Article> getArticleList(String key) {
        // Retrieve the JSON string from SharedPreferences
        String json = sharedPreferences.getString(key, null);

        // If no data is found, return an empty list
        if (json == null) {
            return new ArrayList<>();
        }

        // Convert the JSON string back to a list of Article objects
        Type type = new TypeToken<List<Article>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    /**
     * Function to add an article to the list and save it back
     * @param article
     */
    public void addArticle(Article article) {
        List<Article> articleList = getArticleList(FAVOURITE_ARTICLE_LIST_KEY);

        // Add the new article to the list
        articleList.add(article);

        // Save the updated list back to SharedPreferences
        saveArticleList(articleList,FAVOURITE_ARTICLE_LIST_KEY);
    }

    /**
     * Function to remove an article from the list and save the updated list
     * @param article
     */
    public void removeArticle(Article article) {
        List<Article> articleList = getArticleList(FAVOURITE_ARTICLE_LIST_KEY);

        // Remove the article from the list
        articleList.remove(article);

        // Save the updated list back to SharedPreferences
        saveArticleList(articleList,FAVOURITE_ARTICLE_LIST_KEY);
    }
}

