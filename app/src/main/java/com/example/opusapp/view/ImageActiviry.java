package com.example.opusapp.view;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.opusapp.R;
import com.example.opusapp.databinding.ActivityImageBinding;
import com.example.opusapp.model.Album;

import static com.example.opusapp.common.Constants.DataParcel.EXTRA_ALBUM;

public class ImageActiviry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityImageBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_image);

        Album album = getIntent().getParcelableExtra(EXTRA_ALBUM);
        if (album == null) {
            finish();
            return;
        }

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
        }

        binding.userId.setText(getString(R.string.album, album.getAlbumId()));
        binding.id.setText(getString(R.string.photo, album.getAlbumId()));
        binding.title.setText(album.getTitle());
        Glide.with(binding.imageUrl).load(album.getUrl()).into(binding.imageUrl);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Respond to the action bar's Up/Home button
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
