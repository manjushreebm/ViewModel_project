package com.example.opusapp.services;

import com.example.opusapp.model.Album;
import com.example.opusapp.model.User;

import java.util.List;

import io.reactivex.Single;

public interface OptusService {

    Single<List<User>> fetchUsers();

    Single<List<Album>> getAlbums(int id);

}
