package com.example.edebisozler.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.room.Room;

import com.example.edebisozler.activity.roomdb.QuotesDao;
import com.example.edebisozler.activity.roomdb.QuotesFavDatabase;
import com.example.edebisozler.adapter.MenuRecyclerViewAdapter;
import com.example.edebisozler.model.Example;
import com.example.edebisozler.model.Quotes;
import com.example.edebisozler.adapter.FlowRecyclerViewAdapter;
import com.example.edebisozler.databinding.FragmentFlowBinding;
import com.example.edebisozler.service.EdebiSozlerAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FlowFragment extends Fragment {

    ArrayList<Quotes> quotesArrayList = new ArrayList<>();
    ArrayList<Quotes> favQuotesList;
    
    FragmentFlowBinding binding;
    FlowRecyclerViewAdapter flowRecyclerViewAdapter;

    //https://thetreemedia.com/edebi_sozler_app/all_quotes.php
    public String BASE_URL = "https://thetreemedia.com/edebi_sozler_app/";
    Retrofit retrofit;

    QuotesFavDatabase db;
    QuotesDao quotesDao;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public static FlowFragment newInstance(){
        return new FlowFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = Room.databaseBuilder(getContext(), QuotesFavDatabase.class, "FavQuotes").build();
        quotesDao = db.quotesDao();

        compositeDisposable.add(quotesDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse)
        );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentFlowBinding.inflate(inflater,container,false);
        View view = binding.getRoot();


        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS).build();

        Gson gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL).client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        loadData();


        return view;
    }

    private void loadData(){
        EdebiSozlerAPI edebiSozlerAPI = retrofit.create(EdebiSozlerAPI.class);
        Call<Example> call = edebiSozlerAPI.getData();

        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if(response.isSuccessful()){
                    List<Quotes> responseList = response.body().getEdebiSozler();
                    quotesArrayList = new ArrayList<>(responseList);

                    for(Quotes q: responseList){
                        Log.e("-----------","---------");
                        Log.e("quotes id",q.getQuotesUtterer());;
                    }

                    binding.recyclerviewFlow.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false));
                    if(favQuotesList != null){
                        flowRecyclerViewAdapter = new FlowRecyclerViewAdapter(quotesArrayList,favQuotesList);
                    }else{
                        flowRecyclerViewAdapter = new FlowRecyclerViewAdapter(quotesArrayList);
                    }

                    if(isAdded())
                        binding.recyclerviewFlow.setAdapter(flowRecyclerViewAdapter);
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                System.out.println("bir sorun var "+t);
            }
        });
    }


    private void handleResponse(List<Quotes> favComingList) {
        favQuotesList = new ArrayList<>(favComingList);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }




}