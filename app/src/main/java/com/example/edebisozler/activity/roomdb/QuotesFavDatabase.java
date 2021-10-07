package com.example.edebisozler.activity.roomdb;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.edebisozler.model.Quotes;

@Database(entities = {Quotes.class}, version = 1)
public abstract class QuotesFavDatabase extends RoomDatabase {
    public abstract QuotesDao quotesDao();
}


