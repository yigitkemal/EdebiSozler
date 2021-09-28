package com.example.edebisozler.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edebisozler.model.Quotes;
import com.example.edebisozler.databinding.FlowListItemBinding;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FlowRecyclerViewAdapter extends RecyclerView.Adapter<FlowRecyclerViewAdapter.FlowRecyclerViewHolder> {

    ArrayList<Quotes> quotesList;

    public FlowRecyclerViewAdapter(ArrayList<Quotes> quotesList) { this.quotesList = quotesList; }


    public class FlowRecyclerViewHolder extends RecyclerView.ViewHolder {

        FlowListItemBinding binding;

        public FlowRecyclerViewHolder(FlowListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public FlowRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FlowListItemBinding binding = FlowListItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new FlowRecyclerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FlowRecyclerViewHolder holder, int position) {
        holder.binding.flowImageText.setText(quotesList.get(position).getQuotesText().trim());
        Picasso.get()
                .load(quotesList.get(position).getQuotesPictures())
                .into(holder.binding.flowImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.binding.flowProgressCircular.setVisibility(View.INVISIBLE);
                        System.out.println("düzgün çalıştı");
                    }
                    @Override
                    public void onError(Exception e) {
                        System.out.println("burada bir hata var");
                    }
                });
    }

    @Override
    public int getItemCount() {
        return quotesList.size();
    }
}
