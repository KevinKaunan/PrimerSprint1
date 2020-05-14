package com.example.primersprint.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.primersprint.R;

public class HomeFragment_abajo extends Fragment {

    private HomeViewModel_abajo homeViewModelAbajo;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModelAbajo =
                ViewModelProviders.of(this).get(HomeViewModel_abajo.class);
        View root = inflater.inflate(R.layout.fragment_home_abajo, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModelAbajo.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
