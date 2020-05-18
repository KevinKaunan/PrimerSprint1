package com.example.primersprint.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EditarPerfilViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EditarPerfilViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Panatlla principal del perfil.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}