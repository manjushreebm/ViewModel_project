package com.example.opusapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.opusapp.common.Constants;
import com.example.opusapp.model.User;
import com.example.opusapp.services.OptusService;

import java.net.ConnectException;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class UsersViewModel extends ViewModel {

    private static final String TAG = "UsersViewModel";

    private final OptusService optusService;

    private MutableLiveData<List<User>> usersList = new MutableLiveData<>();

    private MutableLiveData<Integer> progressState = new MutableLiveData<>();

    public UsersViewModel(OptusService optusService) {
        this.optusService = optusService;
    }

    public LiveData<List<User>> getUsersList() {
        return usersList;
    }

    public LiveData<Integer> getProgressState() {
        return progressState;
    }

    public void fetchUsers() {
        optusService.fetchUsers().subscribe(new SingleObserver<List<User>>() {

            Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            private void dispose() {
                if (disposable != null && !disposable.isDisposed()) {
                    disposable.dispose();
                }
            }

            @Override
            public void onSuccess(List<User> users) {
                usersList.postValue(users);
                progressState.postValue(Constants.ProgressState.SUCCESS);
                dispose();
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof ConnectException) {
                    progressState.postValue(Constants.ProgressState.INTERNET_ERROR);
                } else {
                    progressState.postValue(Constants.ProgressState.GENERIC_ERROR);
                }
                Log.e(TAG, e.getMessage(), e);
                dispose();
            }
        });
    }

    public void fetchAlbums(int userId) {
    }
}
