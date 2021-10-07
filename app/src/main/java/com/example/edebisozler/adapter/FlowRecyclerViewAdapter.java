package com.example.edebisozler.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.edebisozler.activity.MainActivity;
import com.example.edebisozler.activity.roomdb.QuotesDao;
import com.example.edebisozler.activity.roomdb.QuotesFavDatabase;
import com.example.edebisozler.model.Quotes;
import com.example.edebisozler.databinding.FlowListItemBinding;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

public class FlowRecyclerViewAdapter extends RecyclerView.Adapter<FlowRecyclerViewAdapter.FlowRecyclerViewHolder> {

    ArrayList<Quotes> quotesList;
    ArrayList<Quotes> favQuotesList;

    QuotesFavDatabase db;
    QuotesDao quotesDao;

    //izinler için yapılan tanımlamalar
    ActivityResultLauncher<String> permissionLauncher;
    Date currentTime = Calendar.getInstance().getTime();

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public FlowRecyclerViewAdapter(ArrayList<Quotes> quotesList, ArrayList<Quotes> favQuotesList) {
        this.quotesList = quotesList;
        this.favQuotesList = favQuotesList;
    }

    public FlowRecyclerViewAdapter(ArrayList<Quotes> quotesArrayList) {
        this.quotesList = quotesArrayList;
    }


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
        db = Room.databaseBuilder(parent.getContext(), QuotesFavDatabase.class, "FavQuotes").build();
        quotesDao = db.quotesDao();

        FlowListItemBinding binding = FlowListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FlowRecyclerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FlowRecyclerViewHolder holder, int position) {
        holder.binding.flowImageText.setText(quotesList.get(position).getQuotesText().trim());
        holder.binding.flowImageUtterer.setText(quotesList.get(position).getQuotesUtterer().trim());
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

        for (int i = 0; i < favQuotesList.size(); i++) {
            if ((quotesList.get(position).getQuotesText().equals(favQuotesList.get(i).getQuotesText()))
                    && (quotesList.get(position).getQuotesUtterer().equals(favQuotesList.get(i).getQuotesUtterer()))) {
                holder.binding.buttonFavorite.setChecked(true);
                System.out.println("değerler eşittir");
            }else{
                System.out.println("değerler eşit değildir");
            }
        }

        holder.binding.buttonFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Quotes quotes = new Quotes(
                            quotesList.get(position).getQuotesUtterer(),
                            quotesList.get(position).getQuotesText(),
                            quotesList.get(position).getQuotesPictures(),
                            quotesList.get(position).getQuotesLanguage(),
                            quotesList.get(position).getQuotesType()
                    );
                    //quotesDao.insert(quotes).subscribeOn(Schedulers.io()).subscribe();
                    compositeDisposable.add(quotesDao.insert(quotes)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe());
                } else {
                    Quotes quotes = new Quotes(quotesList.get(position).getQuotesUtterer(), quotesList.get(position).getQuotesText());
                    compositeDisposable.add(quotesDao.delete(quotesList.get(position).getQuotesUtterer())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe());
                }
            }
        });
        holder.binding.buttonCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) holder.itemView.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(
                        "label",
                        quotesList.get(position).getQuotesText()
                        + " - "+quotesList.get(position).getQuotesUtterer()
                        + " - Edebi Sözler"
                );
                clipboard.setPrimaryClip(clip);

                Toast.makeText(holder.itemView.getContext(),"Metin kopyalandı...",Toast.LENGTH_LONG).show();
            }
        });

        holder.binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.binding.relativeCard.setDrawingCacheEnabled(true);
                Bitmap b = holder.binding.relativeCard.getDrawingCache();
                MediaStore.Images.Media.insertImage(holder.itemView.getContext().getContentResolver(), b, "EdebiSozler" , currentTime.toString());
                Toast.makeText(holder.itemView.getContext(),"Görüntü Galeriye Kaydedildi...",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return quotesList.size();
    }



}


/*

holder.itemView.setDrawingCacheEnabled(true);
                    Bitmap b = holder.itemView.getDrawingCache();
                    try {
                        b.compress(Bitmap.CompressFormat.JPEG,95, new FileOutputStream(
                                Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+ UUID.randomUUID().toString()+".jpg")
                        );
                        Toast.makeText(holder.itemView.getContext(),"Fotoğraf kaydedildi",Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(holder.itemView.getContext(),"Fotoğraf kaydedilemedi...",Toast.LENGTH_LONG).show();
                    }





                    holder.itemView.setDrawingCacheEnabled(true);
                    Bitmap b = holder.itemView.getDrawingCache();
                    try {
                        b.compress(Bitmap.CompressFormat.JPEG,95, new FileOutputStream(
                                Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+ UUID.randomUUID().toString()+".jpg")
                        );
                        Toast.makeText(holder.itemView.getContext(),"Fotoğraf kaydedildi",Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(holder.itemView.getContext(),"Fotoğraf kaydedilemedi...",Toast.LENGTH_LONG).show();
                    }




 */








