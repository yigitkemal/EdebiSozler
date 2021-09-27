package com.example.edebisozler.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edebisozler.MainActivity;
import com.example.edebisozler.PopupActivity;
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
                .load(quotesList.get(position).getQuotesPictures())
                .into(holder.menuListItemBinding.menuImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.menuListItemBinding.menuProgressCircular.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {
                        System.out.println(" Bir hata var :" + e);
                    }
                });


        holder.menuListItemBinding.menuImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(" tıklanan motivasyon sözü : "+quotesList.get(position).getQuotesText());
                Intent intent = PopupActivity.newIntent(holder.itemView.getContext(),quotesList.get(position));
                holder.itemView.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return quotesList.size();
    }



}
