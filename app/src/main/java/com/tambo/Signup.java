package com.tambo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Signup extends AppCompatActivity {
    private EditText username;
    private EditText firstName;
    private EditText lastName;
    private EditText password;
    private EditText email;
    private  EditText phone;
    private EditText gender;
    private Button signup_button;
    private int karma=5;
    public Connect_Server connect_server;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        connect_server= new Connect_Server();
        connect_server.startConnection();
        email= findViewById(R.id.R_email);
        username= findViewById(R.id.R_username);
        firstName=findViewById(R.id.R_name);
        lastName=findViewById(R.id.R_lastname);
        phone=findViewById(R.id.R_phone);
        password=findViewById(R.id.R_password);
        signup_button= findViewById(R.id.Button_SignUp);
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                

            }
        });



    }
}
