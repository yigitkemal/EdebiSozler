package com.example.edebisozler.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.edebisozler.R;
import com.example.edebisozler.activity.roomdb.QuotesDao;
import com.example.edebisozler.activity.roomdb.QuotesFavDatabase;
import com.example.edebisozler.adapter.FavoriteRecyclerViewAdapter;
import com.example.edebisozler.adapter.MenuRecyclerViewAdapter;
import com.example.edebisozler.databinding.ActivityFavoriteBinding;
import com.example.edebisozler.model.Example;
import com.example.edebisozler.model.Quotes;
import com.example.edebisozler.service.EdebiSozlerAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import retrofit2.Retrofit;


public class FavoriteActivity extends AppCompatActivity {

    ArrayList<Quotes> quotesArrayList = new ArrayList<>();
    ArrayList<Quotes> favQuotesList;

    FavoriteRecyclerViewAdapter favoriteRecyclerViewAdapter;
    ActivityFavoriteBinding binding;

    //https://thetreemedia.com/edebi_sozler_app/all_quotes.php
    public String BASE_URL = "https://thetreemedia.com/edebi_sozler_app/";
    Retrofit retrofit;

    QuotesFavDatabase db;
    QuotesDao quotesDao;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Toolbar toolbar = binding.toolbar2;
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        db = Room.databaseBuilder(this, QuotesFavDatabase.class, "FavQuotes").build();
        quotesDao = db.quotesDao();

        compositeDisposable.add(quotesDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(FavoriteActivity.this::handleResponse)
        );

            if(favQuotesList != null){

            }


    }



    private void handleResponse(List<Quotes> quotes) {
        favQuotesList = new ArrayList<>(quotes);

        if(favQuotesList != null) {
            binding.recyclerviewFavorite.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
            favoriteRecyclerViewAdapter = new FavoriteRecyclerViewAdapter(favQuotesList);

            binding.recyclerviewFavorite.setAdapter(favoriteRecyclerViewAdapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

}