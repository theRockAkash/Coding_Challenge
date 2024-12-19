package com.example.codingchallenge.ui.articleDetails;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.codingchallenge.data.models.Article;
import com.example.codingchallenge.utils.SharedPreferenceManager;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * Manages add of remove favourite articles locally using SharedPreference manager
 */
@HiltViewModel
public class ArticleDetailsViewModel extends ViewModel {


    private final SharedPreferenceManager preferenceManager;
    private final MutableLiveData<Article> _article = new MutableLiveData<>();
    public LiveData<Article> article = _article;

    private final MutableLiveData<Boolean> _isFavourite = new MutableLiveData<>();
    public LiveData<Boolean> isFavourite = _isFavourite;

    @Inject
    public ArticleDetailsViewModel(SharedPreferenceManager pref) {
        preferenceManager = pref;
    }

    public void setArticle(Article item) {
        _article.setValue(item);
        _isFavourite.setValue(isArticleFavourite(item));
    }

    public void addOrRemoveFavourite() {
        if (Boolean.TRUE.equals(_isFavourite.getValue())) {
            preferenceManager.removeArticle(_article.getValue());
            _isFavourite.setValue(false);
        } else {
            preferenceManager.addArticle(_article.getValue());
            _isFavourite.setValue(true);
        }
    }

    private boolean isArticleFavourite(Article item) {
        return preferenceManager.getArticleList(SharedPreferenceManager.FAVOURITE_ARTICLE_LIST_KEY).contains(item);
    }


}
