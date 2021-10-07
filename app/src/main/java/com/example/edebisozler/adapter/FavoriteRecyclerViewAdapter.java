package com.example.edebisozler.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.edebisozler.databinding.MenuListItemBinding;


import com.example.edebisozler.databinding.ActivityFavoriteBinding;
import com.example.edebisozler.model.Quotes;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavoriteRecyclerViewAdapter extends RecyclerView.Adapter<FavoriteRecyclerViewAdapter.FavoriteViewHolder> {

    ArrayList<Quotes> favoritesList;

    public FavoriteRecyclerViewAdapter(ArrayList<Quotes> favoritesList) {
        this.favoritesList = favoritesList;
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {

        MenuListItemBinding menuListItemBinding;

        public FavoriteViewHolder(MenuListItemBinding menuListItemBinding) {
            super(menuListItemBinding.getRoot());
            this.menuListItemBinding = menuListItemBinding;
        }
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MenuListItemBinding menuListItemBinding = MenuListItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new FavoriteViewHolder(menuListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
            holder.menuListItemBinding.menuImageText.setText(favoritesList.get(position).getQuotesText());
            Picasso.get().load(favoritesList.get(position).getQuotesPictures()).into(holder.menuListItemBinding.menuImage, new Callback() {
                @Override
                public void onSuccess() {
                    holder.menuListItemBinding.menuProgressCircular.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onError(Exception e) {
                    System.out.println("Favorite adapterda görsel alınamadı.");
                }
            });

    }

    @Override
    public int getItemCount() {
        return favoritesList.size();
    }



}
