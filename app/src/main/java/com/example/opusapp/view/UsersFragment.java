package com.example.opusapp.view;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.opusapp.OptusApplication;
import com.example.opusapp.R;
import com.example.opusapp.databinding.FragmentUserBinding;
import com.example.opusapp.view.components.UsersAdapter;
import com.example.opusapp.viewmodel.UsersViewModel;
import com.example.opusapp.viewmodel.OptusViewModelsFactory;

import static com.example.opusapp.common.Constants.ProgressState.GENERIC_ERROR;
import static com.example.opusapp.common.Constants.ProgressState.INTERNET_ERROR;
import static com.example.opusapp.common.Constants.ProgressState.LOADING;
import static com.example.opusapp.common.Constants.ProgressState.SUCCESS;

public class UsersFragment extends Fragment implements UsersAdapter.OnUserSelectListener {

    private UsersViewModel mViewModel;

    private FragmentUserBinding binding;

    public static UsersFragment newInstance() {
        return new UsersFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        UsersAdapter adapter = new UsersAdapter();
        adapter.setOnUserSelectListener(this);
        binding.recyclerView.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentActivity activity = getActivity();
        mViewModel = ViewModelProviders.of(activity, new OptusViewModelsFactory(OptusApplication.getApplication(activity)))
                .get(UsersViewModel.class);
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

        mViewModel.fetchUsers();
    }

    @Override
    public void onUserSelect(int id) {
        ((ActivityInterfaceContract) getActivity()).showAlbum(id);
    }
}
