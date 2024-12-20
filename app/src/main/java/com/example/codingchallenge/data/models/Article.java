package com.example.codingchallenge.data.models;


import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

public class Article implements Serializable {
    private String publishedAt;

    private String author;

    private String urlToImage;

    private String description;

    private Source source;

    private String title;

    private String url;

    private String content;

    public String getPublishedAt() {
        return Objects.requireNonNullElse(this.publishedAt, "");
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrlToImage() {
        return Objects.requireNonNullElse(this.urlToImage, "");
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getDescription() {
        return Objects.requireNonNullElse(this.description, "");
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Source getSource() {
        return this.source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getTitle() {
        return Objects.requireNonNullElse(this.title, "");
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return getTitle().equals(article.getTitle())&&getDescription().equals(article.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle());
    }
    @NonNull
    @Override
    public String toString() {
        return "Article{" +
                "title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", publishedAt='" + getPublishedAt() + '\'' +
                '}';
    }
}