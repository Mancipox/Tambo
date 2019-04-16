package com.tambo.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.tambo.Model.User;
import com.tambo.R;

public class MyProfileActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        final Bundle extras = getIntent().getExtras();
        final User usermain = (User)extras.get("user");
        final String sendtoken = (String) extras.get("token");

        textView= findViewById(R.id.profile_username);
        textView.setText(usermain.getUserName());
        textView= findViewById(R.id.profile_name);
        textView.setText(usermain.getFirstName());
        textView= findViewById(R.id.profile_last_name);
        textView.setText(usermain.getLastName());
        TextView textViewKarma= findViewById(R.id.karma_number);
        textViewKarma.setText(String.valueOf(usermain.getKarma()));
        Toast.makeText(MyProfileActivity.this, "Mi perfil "+usermain.getKarma(), Toast.LENGTH_SHORT).show();
        textView= findViewById(R.id.profile_email);
        textView.setText(usermain.getEmail());
        textView= findViewById(R.id.profile_number);
        textView.setText(usermain.getPhone());
        /*
        textView.setText(usermain.getKarma());


        */


    }
}
