package com.example.opusapp.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.opusapp.OptusApplication;

public class OptusViewModelsFactory implements ViewModelProvider.Factory {

    private final OptusApplication application;

    public OptusViewModelsFactory(OptusApplication application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass == UsersViewModel.class) {
            return (T) new UsersViewModel(application.getOptusService());
        }

        if (modelClass == AlbumViewModel.class) {
            return (T) new AlbumViewModel(application.getOptusService());
        }

        throw new IllegalStateException("There is no proper class mentioned : " + modelClass.getName());
    }

}
