package com.example.edebisozler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.example.edebisozler.adapter.ViewpagerAdapter;
import com.example.edebisozler.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);



        ViewpagerAdapter adapter = new ViewpagerAdapter(this);
        binding.mainActivityViewpager.setAdapter(adapter);


        new TabLayoutMediator(binding.mainActivityTabLayout,binding.mainActivityViewpager,new TabLayoutMediator.TabConfigurationStrategy(){
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                String tabTitle = "";
                if(position ==0){
                    tabTitle = "Menu";
                }else if(position ==1){
                    tabTitle = "Flow";
                }
                tab.setText(tabTitle);
            }
        }).attach();



    }
}