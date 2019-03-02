package com.tambo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {


    private EditText email;
    private EditText password;
    private Button button_ingreso;
    public Connect_Server connect_server;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        connect_server= new Connect_Server();
        connect_server.startConnection();
        email= findViewById(R.id.email);
        password=findViewById(R.id.password);
        button_ingreso=findViewById(R.id.login_button);
        button_ingreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    login(email.getText().toString(),password.getText().toString());

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    public void login(String mail, String password) throws InterruptedException {
        if (connect_server.isUser(new User (mail, password))){
            //Cambio de pantalla PROVISIONAL
            Intent intent = new Intent(Login.this, Signup.class);
            startActivity(intent);
       }
       else {
            Toast.makeText(this,"Correo o contraseña incorrecta",Toast.LENGTH_SHORT).show();
        }
    }
}
