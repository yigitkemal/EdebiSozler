package com.example.edebisozler.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edebisozler.Quotes;
import com.example.edebisozler.R;
import com.example.edebisozler.databinding.MenuListItemBinding;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MenuRecyclerViewAdapter extends RecyclerView.Adapter<MenuRecyclerViewAdapter.MenuRecyclerViewHolder> implements View.OnClickListener{

    ArrayList<Quotes> quotesList;

    public MenuRecyclerViewAdapter(ArrayList<Quotes> quotesList) {
        this.quotesList = quotesList;
    }


    public class MenuRecyclerViewHolder extends RecyclerView.ViewHolder{

        MenuListItemBinding menuListItemBinding;

        public MenuRecyclerViewHolder(MenuListItemBinding menuListItemBinding) {
            super(menuListItemBinding.getRoot());
            this.menuListItemBinding = menuListItemBinding;
        }
    }



    @Override
    public void onClick(View v) {

    }

    @NonNull
    @Override
    public MenuRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MenuListItemBinding menuListItemBinding = MenuListItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MenuRecyclerViewHolder(menuListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuRecyclerViewHolder holder, int position) {

        holder.menuListItemBinding.menuImageText.setText(quotesList.get(position).getQuotesText().trim());
        Picasso.get()
                .load(R.drawable.image1)
                .into(holder.menuListItemBinding.menuImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.menuListItemBinding.progressCircular.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {
                        System.out.println(" Bir hata var :" + e);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return quotesList.size();
    }



}
