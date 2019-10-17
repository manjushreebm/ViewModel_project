package com.example.opusapp.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.opusapp.R;

public class MainActivity extends AppCompatActivity implements ActivityInterfaceContract {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        if (savedInstanceState == null) {
            loadUsersFragment();
        }
    }

    private void loadUsersFragment() {
        setbackbutton(false);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, UsersFragment.newInstance())
                .commitNow();
    }

    @Override
    public void showAlbum(int userId) {
        setbackbutton(true);
        AlbumFragment fragment = AlbumFragment.newInstance(userId);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commitNow();
    }

    private void setbackbutton(boolean show) {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(show);
            supportActionBar.setDisplayShowHomeEnabled(show);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Respond to the action bar's Up/Home button
        if (item.getItemId() == android.R.id.home) {
            loadUsersFragment();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment instanceof AlbumFragment) {
            loadUsersFragment();
            return;
        }
        super.onBackPressed();
    }
}
