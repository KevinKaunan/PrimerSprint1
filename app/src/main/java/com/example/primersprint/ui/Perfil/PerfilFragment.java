package com.example.primersprint.ui.Perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.primersprint.R;

public class PerfilFragment extends Fragment {

    private PerfilViewModel PerfilViewModel;
//creo q esto se puede eliminar cambio de perfil a login
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PerfilViewModel =
                ViewModelProviders.of(this).get(PerfilViewModel.class);
        View root = inflater.inflate(R.layout.activity_login, container, false);

        return root;
    }
}
