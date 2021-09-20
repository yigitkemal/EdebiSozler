package com.example.edebisozler;

import android.os.Parcel;
import android.os.Parcelable;

public class Quotes implements Parcelable {

    private String quotesUtterer;
    private String quotesText;
    private String quotesPictures;

    public Quotes(String utterer, String quotesText, String quotesPictures) {
        this.quotesUtterer = utterer;
        this.quotesText = quotesText;
        this.quotesPictures = quotesPictures;
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
