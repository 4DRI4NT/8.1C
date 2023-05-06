package com.example.a81c;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a81c.data.DatabaseHelper;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    DatabaseHelper db;
    Button playButton, addButton, playlistButton;
    EditText urlText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        playButton = findViewById(R.id.playButton);
        addButton = findViewById(R.id.addToPlaylistButton);
        playlistButton = findViewById(R.id.playlistButton);
        urlText = findViewById(R.id.urlEditText);

        db = new DatabaseHelper(this);

        // collect primary key
        Intent intent = getIntent();
        String fullname = intent.getStringExtra("name");

        //create and hide fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PlaylistFragment fragment = (PlaylistFragment) fragmentManager.findFragmentById(R.id.fragmentContainerView);
        fragmentTransaction.hide(fragment).commit();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check for valid url
                if (URLUtil.isValidUrl(urlText.getText().toString())) {
                    db.addToPlaylist(fullname, urlText.getText().toString());

                    //confirm addition to playlist
                    Toast.makeText(HomeActivity.this, "URL added to playlist", Toast.LENGTH_LONG).show();
                    urlText.setText("");
                } else {
                    Toast.makeText(HomeActivity.this, "URL is not valid", Toast.LENGTH_LONG).show();
                }
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check for valid url
                if (URLUtil.isValidUrl(urlText.getText().toString())) {
                    //create and start PlayActivity, passing primary key
                    Intent playIntent = new Intent(HomeActivity.this, PlayActivity.class);
                    playIntent.putExtra("url", urlText.getText().toString());
                    startActivity(playIntent);

                } else {
                    Toast.makeText(HomeActivity.this, "URL is not valid", Toast.LENGTH_LONG).show();
                }
            }
        });

        playlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //remove buttons from over fragment
                hideButtons();

                //check for items in playlist
                List<String> playlistList = db.playlistToList(fullname);

                if (playlistList == null) {
                    Toast.makeText(HomeActivity.this, "No items in playlist", Toast.LENGTH_LONG).show();
                } else {
                    //create fragment
                    PlaylistFragment playlistFragment = new PlaylistFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();

                    //bundle list for fragment
                    Bundle bundle = new Bundle();
                    bundle.putString("name", fullname);
                    playlistFragment.setArguments(bundle);

                    //show fragment
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainerView, playlistFragment).addToBackStack("").commit();
                }
            }
        });
    }

    //button visibility methods
    public void hideButtons() {
        playButton.setVisibility(View.INVISIBLE);
        addButton.setVisibility(View.INVISIBLE);
        playlistButton.setVisibility(View.INVISIBLE);
    }
    public void showButtons() {
        playButton.setVisibility(View.VISIBLE);
        addButton.setVisibility(View.VISIBLE);
        playlistButton.setVisibility(View.VISIBLE);
    }
}