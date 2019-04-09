package com.tambo.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.tambo.R;
import com.tambo.Utils.GifImageView;

public class InformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        TextView textView = findViewById(R.id.textViewInfoApp);
        textView.setText("Tambo - versión 0.0.1");
        /*GifImageView gifImageView = (GifImageView) findViewById(R.id.GifImageView);
        gifImageView.setGifImageResource(R.drawable.fire_animated);*/
    }
}
