package com.example.codingchallenge.data.models;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

public class ArticlesResponse implements Serializable {
  private List<Article> articles;

  public List<Article> getArticles() {
    return this.articles;
  }

  public void setArticles(List<Article> articles) {
    this.articles = articles;
  }

}
