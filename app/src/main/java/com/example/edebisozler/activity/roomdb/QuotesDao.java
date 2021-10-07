package com.example.edebisozler.activity.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.edebisozler.model.Quotes;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;


@Dao
public interface QuotesDao {

    @Query("SELECT * FROM quotes")
    Flowable<List<Quotes>> getAll();

    @Insert
    Completable insert(Quotes quotes);

    @Query("DELETE FROM Quotes WHERE quotes_utterer = :utterer")
    Completable delete(String utterer);

    @Query("DELETE FROM quotes")
    void nukeTable();

}
