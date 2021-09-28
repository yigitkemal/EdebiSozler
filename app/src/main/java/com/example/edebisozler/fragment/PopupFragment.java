package com.example.edebisozler.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.edebisozler.model.Quotes;
import com.example.edebisozler.databinding.FragmentPopupBinding;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class PopupFragment extends Fragment {

    FragmentPopupBinding binding;

    private Quotes quotes;

    public static PopupFragment newInstance() {
        return new PopupFragment();
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



        return view;
    }
}