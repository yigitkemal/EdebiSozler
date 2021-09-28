package com.example.edebisozler.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Quotes implements Parcelable {

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

    public Quotes(String utterer, String quotesText, String quotesPictures) {
        this.quotesUtterer = utterer;
        this.quotesText = quotesText;
        this.quotesPictures = quotesPictures;
    }

    public Quotes(String quotesUtterer, String quotesText, String quotesPictures, String quotesLanguage, String quotesType) {
        this.quotesUtterer = quotesUtterer;
        this.quotesText = quotesText;
        this.quotesPictures = quotesPictures;
        this.quotesLanguage = quotesLanguage;
        this.quotesType = quotesType;
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

    public String getUtterer() {
        return quotesUtterer;
    }

    public String getQuotesText() {
        return quotesText;
    }

    public String getQuotesPictures() {
        return quotesPictures;
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
