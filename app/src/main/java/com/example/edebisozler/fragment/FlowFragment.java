package com.example.edebisozler.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edebisozler.Quotes;
import com.example.edebisozler.R;
import com.example.edebisozler.adapter.FlowRecyclerViewAdapter;
import com.example.edebisozler.databinding.FlowListItemBinding;
import com.example.edebisozler.databinding.FragmentFlowBinding;

import java.util.ArrayList;

public class FlowFragment extends Fragment {

    ArrayList<Quotes> quotesArrayList = new ArrayList<>();
    
    FragmentFlowBinding binding;
    FlowRecyclerViewAdapter flowRecyclerViewAdapter;

    public static FlowFragment newInstance(){
        return new FlowFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentFlowBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        Quotes quotes1 = new Quotes("Albert Einstein", "Hayal gücü bilgiden kuvvetlidir.", "https://img.freepik.com/free-photo/wide-angle-shot-single-tree-growing-clouded-sky-during-sunset-surrounded-by-grass_181624-22807.jpg?size=626&ext=jpg");
        Quotes quotes4 = new Quotes("Jack Ma", "Gerçek başarısızlık, hiçbir şey denemeden öylece oturmaktır.", "https://pbs.twimg.com/media/E9YJOcmWQAczMrY.jpg");

        quotesArrayList.add(quotes1);
        quotesArrayList.add(quotes4);
        quotesArrayList.add(quotes1);
        quotesArrayList.add(quotes4);


        System.out.println(quotesArrayList.size() + "-----------------------------------------------");
        binding.recyclerviewFlow.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false));
        flowRecyclerViewAdapter = new FlowRecyclerViewAdapter(quotesArrayList);

        if(isAdded())
        binding.recyclerviewFlow.setAdapter(flowRecyclerViewAdapter);

        System.out.println(flowRecyclerViewAdapter.getItemCount() + "*******************************************");


        return view;
    }
}