package com.example.codingchallenge.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.codingchallenge.R;
import com.example.codingchallenge.data.models.Article;
import com.example.codingchallenge.data.models.NetworkState;
import com.example.codingchallenge.databinding.ActivityMainBinding;
import com.example.codingchallenge.ui.articleDetails.ArticleDetailsActivity;
import com.example.codingchallenge.utils.ConnectivityManagerUtil;
import com.example.codingchallenge.utils.ItemClickListener;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * This Activity contains List of articles fetched from Mock API and displayed
 * using DiffUtil adapter which optimizes how recycler view creates views for items.
 * In this example, ViewModel and LiveData have been used to communicate between API
 * service and UI layer. As LiveData itself is lifecycle aware components, there is
 * no need to use external RxLifecycle library.
 * This example demonstrates one way data flow. Each layer has its own role.
 * It also monitors network connection using a manager class
 * Dependency for ConnectivityManagerUtil class has been injected using Dagger-Hilt
 */
@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements ItemClickListener {

    @Inject
    public ConnectivityManagerUtil connectivityManager;

    private MainViewModel viewModel;
    private ActivityMainBinding binding;
    private ArticleAdapter articleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //create viewModel Instance
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        //set Adapter to recyclerview
        articleAdapter = new ArticleAdapter(this);
        binding.recyclerView.setAdapter(articleAdapter);

        //bind observers for data change
        setObservers();
        setClickListeners();
        setNetworkObserver();
    }

    private void setNetworkObserver() {
        connectivityManager.startNetworkMonitoring();
        connectivityManager.networkState.observe(this, networkState -> {
            if (networkState == NetworkState.CONNECTED) {
                binding.tvNetworkState.setText(R.string.back_online);
                binding.tvNetworkState.setBackgroundColor(getResources().getColor(R.color.colorGreen, getTheme()));
                binding.tvNetworkState.setVisibility(View.VISIBLE);
                new Handler().postDelayed(() -> {
                    //hide it after 2 seconds
                    viewModel.getArticles();
                    binding.tvNetworkState.setVisibility(View.GONE);
                }, 2000);
            } else {
                binding.tvNetworkState.setText(R.string.no_network);
                binding.tvNetworkState.setBackgroundColor(getResources().getColor(R.color.colorRed, getTheme()));
                binding.tvNetworkState.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setClickListeners() {

        binding.ibSearch.setOnClickListener(v -> {
            if (binding.etSearch.getVisibility() == View.VISIBLE) {
                binding.etSearch.setVisibility(View.GONE);
                binding.etSearch.getEditText().setText("");
            } else {
                binding.etSearch.getEditText().setText("");
                binding.etSearch.setVisibility(View.VISIBLE);
            }
        });
        // Listen for search input change
        binding.etSearch.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.onSearchTextChanged(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setObservers() {
        viewModel.articleList.observe(this, articles -> articleAdapter.submitList(articles));

        //Listen for network loading
        viewModel.isLoading.observe(this, isLoading -> {
            if (isLoading)
                binding.loader.setVisibility(View.VISIBLE);
            else binding.loader.setVisibility(View.GONE);
        });

        //Listen for any error
        viewModel.error.observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }

        });
    }


    @Override
    public void onItemClick(Article item) {
        Intent intent = new Intent(this, ArticleDetailsActivity.class);
        intent.putExtra("item", item);
        startActivity(intent);
    }
}