package com.example.edebisozler.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.edebisozler.activity.roomdb.QuotesDao;
import com.example.edebisozler.activity.roomdb.QuotesFavDatabase;
import com.example.edebisozler.databinding.FragmentPopupBinding;
import com.example.edebisozler.model.Quotes;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PopupFragment extends Fragment {

    FragmentPopupBinding binding;

    private Quotes quotes;

    Bitmap imageViewHolder;

    ArrayList<Quotes> favQuotesList;
    QuotesFavDatabase db;
    QuotesDao quotesDao;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    Date currentTime = Calendar.getInstance().getTime();

    public static PopupFragment newInstance() {
        return new PopupFragment();
    }

    @Override
    public void onStart() {
        super.onStart();

        db = Room.databaseBuilder(getContext(), QuotesFavDatabase.class, "FavQuotes").build();
        quotesDao = db.quotesDao();

        compositeDisposable.add(quotesDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse)
        );
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        quotes = getActivity().getIntent().getParcelableExtra("INFO");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPopupBinding.inflate(inflater, container, false);
        View view = binding.getRoot();



        binding.popupImageText.setText(quotes.getQuotesText());
        binding.popupImageUtterer.setText(quotes.getQuotesUtterer());
        Picasso.get()
                .load(quotes.getQuotesPictures())
                .fit()
                .centerCrop()
                .into(binding.popupImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        binding.popupProgressCircular.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });



        binding.buttonFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Quotes quotesHolder = new Quotes(
                            quotes.getQuotesUtterer(),
                            quotes.getQuotesText(),
                            quotes.getQuotesPictures(),
                            quotes.getQuotesLanguage(),
                            quotes.getQuotesType()
                    );
                    //quotesDao.insert(quotes).subscribeOn(Schedulers.io()).subscribe();
                    compositeDisposable.add(quotesDao.insert(quotes)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe());

                    //Toast.makeText(getContext(),"Söz favorilere eklendi...",Toast.LENGTH_LONG).show();
                } else {
                    Quotes quotesHolder = new Quotes(quotes.getQuotesUtterer(), quotes.getQuotesText());
                    compositeDisposable.add(quotesDao.delete(quotes.getQuotesUtterer())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe());
                    //Toast.makeText(getContext(),"Söz favorilerden çıkarıldı...",Toast.LENGTH_LONG).show();
                }
            }
        });
        binding.buttonCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Popup copy tıklandı");
                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(
                        "label",
                        quotes.getQuotesText()
                                + " - "+quotes.getQuotesUtterer()
                                + " - Edebi Sözler"
                );
                clipboard.setPrimaryClip(clip);

                Toast.makeText(getContext(),"Metin kopyalandı...",Toast.LENGTH_LONG).show();
            }
        });
        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.quotesCard.setDrawingCacheEnabled(true);
                Bitmap b = binding.quotesCard.getDrawingCache();
                MediaStore.Images.Media.insertImage(getContext().getContentResolver(), b, "EdebiSozler" , currentTime.toString());
                Toast.makeText(getContext(),"Görüntü Galeriye Kaydedildi...",Toast.LENGTH_LONG).show();
            }
        });
        binding.buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                binding.quotesCard.setDrawingCacheEnabled(true);
                imageViewHolder = binding.quotesCard.getDrawingCache();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, getImageUri(getContext(),imageViewHolder));
                shareIntent.setType("image/jpeg");
                startActivity(Intent.createChooser(shareIntent, "Share Image"));
            }
        });


        return view;
    }


    private void handleResponse(List<Quotes> favComingList) {
        favQuotesList = new ArrayList<>(favComingList);

        if ((favQuotesList != null)) {
            System.out.println("popup fav dolu");
            for (int i = 0; i <favQuotesList.size() ; i++) {
                if(quotes.getQuotesText().equals(favQuotesList.get(i).getQuotesText())
                        && quotes.getQuotesUtterer().equals(favQuotesList.get(i).getQuotesUtterer())){
                    binding.buttonFavorite.setChecked(true);
                }
            }
        } else {
            System.out.println("popup fav boş");
            binding.buttonFavorite.setChecked(false);
        }

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


}