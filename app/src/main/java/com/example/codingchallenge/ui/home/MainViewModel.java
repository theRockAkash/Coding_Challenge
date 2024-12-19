package com.example.codingchallenge.ui.home;

import static com.example.codingchallenge.utils.SharedPreferenceManager.ARTICLE_LIST_KEY;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.codingchallenge.data.ApiService;
import com.example.codingchallenge.data.models.Article;
import com.example.codingchallenge.data.models.ArticlesResponse;
import com.example.codingchallenge.utils.ConnectivityManagerUtil;
import com.example.codingchallenge.utils.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * ViewModel is responsible for calling APIService or Local cache based on network connectivity
 * if data is available in cache, it loads immediately, then tries for new data from API if
 * network is available
 */
@HiltViewModel
public class MainViewModel extends ViewModel {

    private List<Article> completeList=new ArrayList<>();
    private final MutableLiveData<List<Article>> _articleList = new MutableLiveData<>();
    public LiveData<List<Article>> articleList = _articleList;
    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    public LiveData<Boolean> isLoading = _isLoading;
    private final MutableLiveData<String> _error = new MutableLiveData<>();
    public LiveData<String> error = _error;

    private final ConnectivityManagerUtil connectivityManager;
    private final SharedPreferenceManager preferenceManager;
    private final ApiService api;

    @Inject
    public MainViewModel(ApiService api,ConnectivityManagerUtil managerUtil,SharedPreferenceManager preferenceManager) {
        this.api = api;
        connectivityManager=managerUtil;
        this.preferenceManager=preferenceManager;
        getArticles();

    }

    public void getArticles() {
        List<Article> list= preferenceManager.getArticleList(ARTICLE_LIST_KEY);
        if(list!=null){
            completeList=list;
            _articleList.postValue(list);
        }
        if(connectivityManager.isNetworkConnected())
        {
            api.getArticles().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            _isLoading.postValue(true);
                            _error.postValue("");
                        }

                        @Override
                        public void onNext(ArticlesResponse articlesResponse) {
                            completeList=articlesResponse.getArticles();
                            preferenceManager.saveArticleList(completeList,ARTICLE_LIST_KEY);
                            _articleList.postValue(articlesResponse.getArticles());
                        }

                        @Override
                        public void onError(Throwable e) {
                            _isLoading.postValue(false);
                            _error.postValue(e.getLocalizedMessage());
                           // e.printStackTrace();
                        }

                        @Override
                        public void onComplete() {
                            _isLoading.postValue(false);
                        }
                    });
        }
    }

    public void onSearchTextChanged(String query) {
        List<Article> result = new ArrayList<>();
        for (Article article : completeList) {
            if (article.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    (article.getDescription().toLowerCase().contains(query.toLowerCase()) )||
                    article.getPublishedAt().toLowerCase().contains(query.toLowerCase())) {
                result.add(article);
            }
        }
         _articleList.setValue(result);
    }
}
