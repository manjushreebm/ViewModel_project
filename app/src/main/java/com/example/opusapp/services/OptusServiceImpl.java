package com.example.opusapp.services;

import android.content.Context;

import com.example.opusapp.R;
import com.example.opusapp.model.Album;
import com.example.opusapp.model.User;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class OptusServiceImpl implements OptusService {

    private final Service service;

    private OptusServiceImpl(Context context) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.base_url))
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(Service.class);
    }

    public static OptusServiceImpl getInstance(Context context) {
        return new OptusServiceImpl(context);
    }

    public Single<List<User>> fetchUsers() {
        return service.getUsers().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<Album>> getAlbums(int id) {
        return service.getPhotos(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    interface Service {

        @GET("/users")
        Single<List<User>> getUsers();

        @GET("/photos")
        Single<List<Album>> getPhotos(@Query("albumId") int id);

    }

}
