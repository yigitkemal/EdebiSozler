package com.example.edebisozler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.edebisozler.databinding.ActivityPopupBinding;
import com.example.edebisozler.fragment.PopupFragment;

public class PopupActivity extends AppCompatActivity {

    ActivityPopupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPopupBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.popup_activity_framelayout);


        if(fragment == null){
            fragment = PopupFragment.newInstance();
            fragmentManager.beginTransaction().add(R.id.popup_activity_framelayout,fragment).commit();
        }


    }

    public static Intent newIntent(Context context, Quotes quotes){
        Intent intent = new Intent(context,PopupActivity.class);
        intent.putExtra("INFO",quotes);
        return intent;
    }

}