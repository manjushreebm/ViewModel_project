package com.example.opusapp.common;

public @interface Constants {

    @interface ProgressState {
        int LOADING = 0;
        int SUCCESS = 1;
        int INTERNET_ERROR = -1;
        int GENERIC_ERROR = -2;
    }

    @interface DataParcel {

        String EXTRA_ALBUM = "EXTRA_ALBUM";
    }

}
