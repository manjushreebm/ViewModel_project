package com.example.opusapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.opusapp.OptusApplication;
import com.example.opusapp.R;
import com.example.opusapp.common.Constants;
import com.example.opusapp.databinding.FragmentAlbumBinding;
import com.example.opusapp.model.Album;
import com.example.opusapp.view.components.AlbumAdapter;
import com.example.opusapp.viewmodel.AlbumViewModel;
import com.example.opusapp.viewmodel.OptusViewModelsFactory;

import static com.example.opusapp.common.Constants.ProgressState.GENERIC_ERROR;
import static com.example.opusapp.common.Constants.ProgressState.INTERNET_ERROR;
import static com.example.opusapp.common.Constants.ProgressState.LOADING;
import static com.example.opusapp.common.Constants.ProgressState.SUCCESS;

public class AlbumFragment extends Fragment implements AlbumAdapter.OnAlbumSelectListener {

    private final int userId;

    private AlbumViewModel mViewModel;

    private FragmentAlbumBinding binding;

    public AlbumFragment(int userId) {
        this.userId = userId;
    }

    public static AlbumFragment newInstance(int userId) {
        AlbumFragment albumFragment = new AlbumFragment(userId);
        return albumFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_album, container, false);
        binding.userId.setText(getString(R.string.album, userId));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        AlbumAdapter adapter = new AlbumAdapter();
        adapter.setOnAlbumSelectListener(this);
        binding.recyclerView.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentActivity activity = getActivity();
        mViewModel = ViewModelProviders.of(activity, new OptusViewModelsFactory(OptusApplication.getApplication(activity)))
                .get(AlbumViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setViewmodel(mViewModel);

        mViewModel.getProgressState().observeForever(new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == null) return;
                switch (integer) {
                    case INTERNET_ERROR:
                        binding.message.setVisibility(View.VISIBLE);
                        binding.recyclerView.setVisibility(View.GONE);
                        binding.message.setText(R.string.internet_error);
                        break;
                    case GENERIC_ERROR:
                        binding.message.setVisibility(View.VISIBLE);
                        binding.recyclerView.setVisibility(View.GONE);
                        binding.message.setText(R.string.generic_error);
                        break;
                    case LOADING:
                        binding.message.setVisibility(View.VISIBLE);
                        binding.recyclerView.setVisibility(View.GONE);
                        binding.message.setText(R.string.loading_text);
                        break;
                    case SUCCESS:
                        binding.message.setVisibility(View.GONE);
                        binding.recyclerView.setVisibility(View.VISIBLE);
                }
            }
        });

        mViewModel.fetchAlbums(userId);
    }

    @Override
    public void onAlbumSelect(Album album) {
        Intent intent = new Intent(getActivity(), ImageActiviry.class);
        intent.putExtra(Constants.DataParcel.EXTRA_ALBUM, album);
        startActivity(intent);
    }
}
