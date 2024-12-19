package com.example.codingchallenge.ui.home;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.codingchallenge.R;
import com.example.codingchallenge.data.models.Article;
import com.example.codingchallenge.utils.ItemClickListener;
import com.example.codingchallenge.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter to render items in recyclerview
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ItemViewHolder> {

    private final List<Article> itemList = new ArrayList<>();
    private final ItemClickListener listener;

    public ArticleAdapter(ItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_article_item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Article item = itemList.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvBody.setText(item.getDescription());
        holder.tvDate.setText(Utils.formatDateString(item.getPublishedAt()));
        Glide.with(holder.ivImage)
                .load(item.getUrlToImage())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.ivImage);

        //set click listener
        holder.itemView.setOnClickListener(v -> {
            listener.onItemClick(item);
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void submitList(List<Article> newList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ItemDiffCallback(itemList, newList));
        itemList.clear();
        itemList.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvBody, tvDate;
        ImageView ivImage;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvDate = itemView.findViewById(R.id.tvDate);
            ivImage = itemView.findViewById(R.id.ivImage);
        }
    }

    /**
     * DiffUtil Callback to compare two lists
     * Provides optimised way of submitting list to adapter
     * Useful in cases where list changes frequently e.g. searching articles
     */
    public static class ItemDiffCallback extends DiffUtil.Callback {

        private final List<Article> oldList;
        private final List<Article> newList;

        public ItemDiffCallback(List<Article> oldList, List<Article> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).getTitle().equals(newList.get(newItemPosition).getTitle());
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            Article oldItem = oldList.get(oldItemPosition);
            Article newItem = newList.get(newItemPosition);

            return oldItem.getTitle().equals(newItem.getTitle()) && oldItem.getDescription().equals(newItem.getDescription());
        }
    }
}

