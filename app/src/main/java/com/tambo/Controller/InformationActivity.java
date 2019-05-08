package com.tambo.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.widget.TextView;

import com.tambo.R;
import com.tambo.Utils.GifImageView;

public class InformationActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView textViewVersion;
    private TextView textViewChanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        toolbar = (Toolbar) findViewById(R.id.toolbar_info);

        textViewVersion = findViewById(R.id.textViewInfoApp);
        textViewChanges = findViewById(R.id.textViewChanges);

        textViewVersion.setText(getText(R.string.tag_version));
        textViewChanges.setText(getText(R.string.tag_changes));

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.toolbar_title_info);
        /*GifImageView gifImageView = (GifImageView) findViewById(R.id.GifImageView);
        gifImageView.setGifImageResource(R.drawable.fire_animated);*/
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}