package com.example.edebisozler.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class Quotes implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "quotes_utterer")
    @SerializedName("quotes_utterer")
    private String quotesUtterer;

    @SerializedName("quotes_text")
    private String quotesText;

    @SerializedName("quotes_pictures")
    private String quotesPictures;

    @SerializedName("language")
    private String quotesLanguage;

    @SerializedName("quotes_type")
    private String quotesType;

    @Ignore
    public Quotes(String utterer, String quotesText, String quotesPictures) {
        this.quotesUtterer = utterer;
        this.quotesText = quotesText;
        this.quotesPictures = quotesPictures;
    }

    @Ignore
    public Quotes(String quotesUtterer, String quotesText, String quotesPictures, String quotesLanguage, String quotesType) {
        this.quotesUtterer = quotesUtterer;
        this.quotesText = quotesText;
        this.quotesPictures = quotesPictures;
        this.quotesLanguage = quotesLanguage;
        this.quotesType = quotesType;
    }

    public Quotes( String quotesUtterer,String quotesText) {
        this.quotesUtterer = quotesUtterer;
        this.quotesText = quotesText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuotesUtterer() {
        return quotesUtterer;
    }

    public void setQuotesUtterer(String quotesUtterer) {
        this.quotesUtterer = quotesUtterer;
    }

    public String getQuotesText() {
        return quotesText;
    }

    public void setQuotesText(String quotesText) {
        this.quotesText = quotesText;
    }

    public String getQuotesPictures() {
        return quotesPictures;
    }

    public void setQuotesPictures(String quotesPictures) {
        this.quotesPictures = quotesPictures;
    }

    public String getQuotesLanguage() {
        return quotesLanguage;
    }

    public void setQuotesLanguage(String quotesLanguage) {
        this.quotesLanguage = quotesLanguage;
    }

    public String getQuotesType() {
        return quotesType;
    }

    public void setQuotesType(String quotesType) {
        this.quotesType = quotesType;
    }

    protected Quotes(Parcel in) {
        quotesUtterer = in.readString();
        quotesPictures = in.readString();
        quotesText = in.readString();
    }

    public static final Creator<Quotes> CREATOR = new Creator<Quotes>() {
        @Override
        public Quotes createFromParcel(Parcel in) {
            return new Quotes(in);
        }

        @Override
        public Quotes[] newArray(int size) {
            return new Quotes[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(quotesUtterer);
        dest.writeString(quotesPictures);
        dest.writeString(quotesText);
    }
}
