package com.example.incivismogym.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.incivismogym.databinding.FragmentHomeBinding;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private HomeViewModel binding;
    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



        return null;
    }



    public LiveData<String> getText() {
        return mText;
    }
}