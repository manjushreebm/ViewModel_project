package com.example.opusapp.view.components;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.opusapp.R;
import com.example.opusapp.model.Album;

import java.util.ArrayList;
import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> implements DataAdapter<Album> {

    private List<Album> users = new ArrayList<>();

    private OnAlbumSelectListener listener;

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_album_item, parent, false);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        final Album album = users.get(position);
        holder.titleView.setText(album.getTitle());
        Glide.with(holder.imageView).load(album.getThumbnailUrl()).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onAlbumSelect(album);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @Override
    public void setData(List<Album> data) {
        this.users.addAll(data);
    }

    public void setOnAlbumSelectListener(OnAlbumSelectListener listener) {
        this.listener = listener;
    }

    class AlbumViewHolder extends RecyclerView.ViewHolder {

        private final TextView titleView;
        private final ImageView imageView;

        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.image_thumb);
        }
    }

    public interface OnAlbumSelectListener {
        void onAlbumSelect(Album album);
    }
}
