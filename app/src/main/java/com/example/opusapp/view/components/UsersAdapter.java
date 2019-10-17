package com.example.opusapp.view.components;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.opusapp.R;
import com.example.opusapp.model.User;
import com.example.opusapp.view.UsersFragment;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> implements DataAdapter<User> {

    private List<User> users = new ArrayList<>();

    private OnUserSelectListener listener;

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user_item, parent, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        final User user = users.get(position);
        holder.idView.setText(Integer.toString(user.getId()));
        holder.nameView.setText(user.getName());
        holder.emailView.setText(user.getEmail());
        holder.phoneView.setText(user.getPhone());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onUserSelect(user.getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @Override
    public void setData(List<User> data) {
        this.users.addAll(data);
    }

    public void setOnUserSelectListener(OnUserSelectListener listener) {
        this.listener = listener;
    }

    class UsersViewHolder extends RecyclerView.ViewHolder {

        private final TextView idView, nameView, emailView, phoneView;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            idView = itemView.findViewById(R.id.id);
            nameView = itemView.findViewById(R.id.name);
            emailView = itemView.findViewById(R.id.email);
            phoneView = itemView.findViewById(R.id.phone);
        }
    }

    public interface OnUserSelectListener {
        void onUserSelect(int id);
    }
}
