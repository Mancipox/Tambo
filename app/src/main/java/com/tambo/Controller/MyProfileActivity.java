package com.tambo.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.tambo.Model.User;
import com.tambo.R;

public class MyProfileActivity extends AppCompatActivity {
    private TextView textView;
    private Button editButton;
    private ImageView imageProfile;
    private Toolbar toolbar;

    private User usermain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        final Bundle extras = getIntent().getExtras();
        usermain = (User)extras.get("user");
        final String sendtoken = (String) extras.get("token");
        editButton=findViewById(R.id.edit_button);
        imageProfile = findViewById(R.id.image_profile_section);

        toolbar = (Toolbar) findViewById(R.id.toolbar_profile);

        textView= findViewById(R.id.profile_username);
        textView.setText(usermain.getUserName());
        textView= findViewById(R.id.profile_name);
        textView.setText(usermain.getFirstName());
        textView= findViewById(R.id.profile_last_name);
        textView.setText(usermain.getLastName());
        TextView textViewKarma= findViewById(R.id.karma_number);
        textViewKarma.setText(String.valueOf(usermain.getKarma()));
    //    Toast.makeText(MyProfileActivity.this, "Mi perfil "+usermain.getKarma(), Toast.LENGTH_SHORT).show();
        textView= findViewById(R.id.profile_email);
        textView.setText(usermain.getEmail());
        textView= findViewById(R.id.profile_number);
        textView.setText(usermain.getPhone());

        editButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProfileActivity.this, EditProfileActivity.class);
                intent.putExtra("user", usermain);
                intent.putExtra("token",sendtoken);
                startActivity(intent);

            }


    });
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        imageProfile.setImageResource((usermain.getGender().equals("Masculino"))?R.drawable.user_mal:R.drawable.user_fem);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}

