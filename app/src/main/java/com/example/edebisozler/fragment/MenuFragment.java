package com.example.edebisozler.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager2.adapter.FragmentViewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.edebisozler.Quotes;
import com.example.edebisozler.R;
import com.example.edebisozler.adapter.MenuRecyclerViewAdapter;
import com.example.edebisozler.databinding.MenuFragmentBinding;

import java.util.ArrayList;

public class MenuFragment extends Fragment {

    MenuFragmentBinding binding;

    ArrayList<Quotes> quotesArrayList = new ArrayList<>();
    MenuRecyclerViewAdapter menuRecyclerViewAdapter;

    public static MenuFragment newInstance() {
        return new MenuFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = MenuFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        Quotes quotes1 = new Quotes("Albert Einstein", "Hayal gücü bilgiden kuvvetlidir.", "https://img.freepik.com/free-photo/wide-angle-shot-single-tree-growing-clouded-sky-during-sunset-surrounded-by-grass_181624-22807.jpg?size=626&ext=jpg");
        Quotes quotes2 = new Quotes("Mark Twain", "İlerleyebilmenin sırrı başlamaktır.", "https://i.pinimg.com/originals/a7/3d/6e/a73d6e4ac85c6a822841e449b24c78e1.jpg");
        Quotes quotes3 = new Quotes("Arianna Huffington", "Dünyada ne zaman bir şey yaparsan yap; eleştiri olacak.", "https://cdn.pixabay.com/photo/2015/12/01/20/28/road-1072823__340.jpg");
        Quotes quotes4 = new Quotes("Jack Ma", "Gerçek başarısızlık, hiçbir şey denemeden öylece oturmaktır.", "https://pbs.twimg.com/media/E9YJOcmWQAczMrY.jpg");
        Quotes quotes5 = new Quotes("Albert Einstein", "Hayal gücü bilgiden kuvvetlidir.", "https://img.freepik.com/free-photo/wide-angle-shot-single-tree-growing-clouded-sky-during-sunset-surrounded-by-grass_181624-22807.jpg?size=626&ext=jpg");
        Quotes quotes6 = new Quotes("Mark Twain", "İlerleyebilmenin sırrı başlamaktır.", "https://i.pinimg.com/originals/a7/3d/6e/a73d6e4ac85c6a822841e449b24c78e1.jpg");
        Quotes quotes7 = new Quotes("Arianna Huffington", "Dünyada ne zaman bir şey yaparsan yap; eleştiri olacak.", "https://cdn.pixabay.com/photo/2015/12/01/20/28/road-1072823__340.jpg");
        Quotes quotes8 = new Quotes("Jack Ma", "Gerçek başarısızlık, hiçbir şey denemeden öylece oturmaktır.", "https://pbs.twimg.com/media/E9YJOcmWQAczMrY.jpg");



        quotesArrayList.add(quotes1);
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



        System.out.println(quotesArrayList.size() + "-----------------------------------------------");

        binding.recyclerviewMenu.setLayoutManager(new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL));
        menuRecyclerViewAdapter = new MenuRecyclerViewAdapter(quotesArrayList);

        if (isAdded())
            binding.recyclerviewMenu.setAdapter(menuRecyclerViewAdapter);

        System.out.println(menuRecyclerViewAdapter.getItemCount() + "*******************************************");


        return view;
    }


}