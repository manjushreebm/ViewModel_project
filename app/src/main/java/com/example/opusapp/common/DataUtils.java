package com.example.opusapp.common;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.opusapp.view.components.DataAdapter;

import java.util.List;

public class DataUtils {

    @BindingAdapter("data")
    public static <T> void setRecyclerViewProperties(RecyclerView recyclerView, LiveData<List<T>> liveData) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        List<T> value = liveData.getValue();
        if (adapter == null || value == null) {
            return;
        }
        ((DataAdapter) adapter).setData(value);
    }

}
