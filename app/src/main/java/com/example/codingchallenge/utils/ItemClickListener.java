package com.example.codingchallenge.utils;

import com.example.codingchallenge.data.models.Article;

/**
 * Used for sending callback back to main activity when an item in adapter is clicked
 */
public interface ItemClickListener {
    void onItemClick(Article item);
}
