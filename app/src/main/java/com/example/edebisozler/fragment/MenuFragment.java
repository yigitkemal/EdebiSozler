package com.example.edebisozler.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.edebisozler.model.Example;
import com.example.edebisozler.model.Quotes;
import com.example.edebisozler.adapter.MenuRecyclerViewAdapter;
import com.example.edebisozler.databinding.MenuFragmentBinding;
import com.example.edebisozler.service.EdebiSozlerAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuFragment extends Fragment {

    MenuFragmentBinding binding;

    ArrayList<Quotes> quotesArrayList = new ArrayList<>();
    MenuRecyclerViewAdapter menuRecyclerViewAdapter;

    //https://thetreemedia.com/edebi_sozler_app/all_quotes.php
    public String BASE_URL = "https://thetreemedia.com/edebi_sozler_app/";
    Retrofit retrofit;

    public static MenuFragment newInstance() {
        return new MenuFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = MenuFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();



        Gson gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
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

                    /*for(Quotes q: responseList){
                        Log.e("-----------","---------");
                        Log.e("quotes id",q.getUtterer());;
                    }*/

                    binding.recyclerviewMenu.setLayoutManager(new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL));
                    menuRecyclerViewAdapter = new MenuRecyclerViewAdapter(quotesArrayList);

                    if (isAdded())
                        binding.recyclerviewMenu.setAdapter(menuRecyclerViewAdapter);


                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                System.out.println("bir sorun var "+t);
            }
        });
    }


}



/*
        Quotes quotes1 = new Quotes("Albert Einstein", "Hayal gücü bilgiden kuvvetlidir.", "https://img.freepik.com/free-photo/wide-angle-shot-single-tree-growing-clouded-sky-during-sunset-surrounded-by-grass_181624-22807.jpg?size=626&ext=jpg");
        Quotes quotes2 = new Quotes("Mark Twain", "İlerleyebilmenin sırrı başlamaktır.", "https://i.pinimg.com/originals/a7/3d/6e/a73d6e4ac85c6a822841e449b24c78e1.jpg");
        Quotes quotes3 = new Quotes("Arianna Huffington", "Dünyada ne zaman bir şey yaparsan yap; eleştiri olacak.", "https://cdn.pixabay.com/photo/2015/12/01/20/28/road-1072823__340.jpg");
        Quotes quotes4 = new Quotes("Jack Ma", "Gerçek başarısızlık, hiçbir şey denemeden öylece oturmaktır.", "https://pbs.twimg.com/media/E9YJOcmWQAczMrY.jpg");
        Quotes quotes5 = new Quotes("Albert Einstein", "Hayal gücü bilgiden kuvvetlidir.", "https://img.freepik.com/free-photo/wide-angle-shot-single-tree-growing-clouded-sky-during-sunset-surrounded-by-grass_181624-22807.jpg?size=626&ext=jpg");
        Quotes quotes6 = new Quotes("Mark Twain", "İlerleyebilmenin sırrı başlamaktır.", "https://i.pinimg.com/originals/a7/3d/6e/a73d6e4ac85c6a822841e449b24c78e1.jpg");
        Quotes quotes7 = new Quotes("Arianna Huffington", "Dünyada ne zaman bir şey yaparsan yap; eleştiri olacak.", "https://cdn.pixabay.com/photo/2015/12/01/20/28/road-1072823__340.jpg");
        Quotes quotes8 = new Quotes("Jack Ma", "Gerçek başarısızlık, hiçbir şey denemeden öylece oturmaktır.", "https://pbs.twimg.com/media/E9YJOcmWQAczMrY.jpg","TR","İlham");
        Quotes quotes9 = new Quotes("Jack Ma", "test bu bir test sözüdür sadfsdfsdf adsfsdfsdf sdfsdf.", "https://images.pexels.com/photos/417074/pexels-photo-417074.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500","TR","Motivasyon");
        quotesArrayList.add(quotes1);
        quotesArrayList.add(quotes9);
        quotesArrayList.add(quotes2);
        quotesArrayList.add(quotes3);
        quotesArrayList.add(quotes4);
        quotesArrayList.add(quotes5);
        quotesArrayList.add(quotes6);
        quotesArrayList.add(quotes7);
        quotesArrayList.add(quotes8);
        quotesArrayList.add(quotes1);
        quotesArrayList.add(quotes2);
        quotesArrayList.add(quotes3);
        quotesArrayList.add(quotes4);
        quotesArrayList.add(quotes5);
        quotesArrayList.add(quotes6);
        quotesArrayList.add(quotes7);
        quotesArrayList.add(quotes8);
*/
