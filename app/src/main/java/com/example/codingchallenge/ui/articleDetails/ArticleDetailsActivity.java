package com.example.codingchallenge.ui.articleDetails;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import com.example.codingchallenge.R;
import com.example.codingchallenge.data.models.Article;
import com.example.codingchallenge.databinding.ActivityArticleDetailsBinding;
import com.example.codingchallenge.utils.Utils;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * This Activity displays article details sent via intent from Article List Screen.
 * In this example, ViewModel and LiveData have been used to hold the article data.
 * As LiveData itself is lifecycle aware components, there is no need to use external
 * RxLifecycle library.
 * Shared Preference have been used to store favourite articles. Logic to check for
 * whether an article is marked favorite or not resides in viewmodel.
 * This example demonstrates one way data flow. Each layer has its own role.
 */
@AndroidEntryPoint
public class ArticleDetailsActivity extends AppCompatActivity {

    private ArticleDetailsViewModel viewModel;
    ActivityArticleDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityArticleDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        viewModel = new ViewModelProvider(this).get(ArticleDetailsViewModel.class);
        Article item = (Article) getIntent().getSerializableExtra("item");

        if (item != null) {
            viewModel.setArticle(item);
        }

        bindData();
        setClickListeners();

    }

    private void setClickListeners() {
        binding.ibBack.setOnClickListener(v -> {
            finish();
        });
        binding.ibFavourite.setOnClickListener(v -> {
            viewModel.addOrRemoveFavourite();
        });
    }

    private void bindData() {
        viewModel.article.observe(this, item -> {
            if (item != null) {
                binding.tvTitle.setText(item.getTitle());
                binding.tvBody.setText(item.getDescription());
                binding.tvDate.setText(Utils.formatDateString(item.getPublishedAt()));
                Glide.with(this)
                        .load(item.getUrlToImage())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(binding.ivImage);
            }
        });
        viewModel.isFavourite.observe(this, isFav -> {
            if (isFav) {
                binding.ibFavourite.setImageResource(R.drawable.baseline_favorite_24);
            } else {
                binding.ibFavourite.setImageResource(R.drawable.baseline_favorite_border_24);
            }
        });

    }
}