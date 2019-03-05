package com.tambo.Controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.tambo.Connection.Connect_Server;
import com.tambo.Model.User;
import com.tambo.R;

public class Signup extends AppCompatActivity {
    private EditText username;
    private EditText firstName;
    private EditText lastName;
    private EditText password;
    private EditText email;
    private  EditText phone;
    private RadioButton radioButton_gender;
    private RadioGroup radioGroup;
    private Button signup_button;
    public Connect_Server connect_server;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        connect_server = new Connect_Server();
        connect_server.startConnection();
        email = findViewById(R.id.R_email);
        username = findViewById(R.id.R_username);
        firstName = findViewById(R.id.R_name);
        lastName = findViewById(R.id.R_lastname);
        phone = findViewById(R.id.R_phone);
        password = findViewById(R.id.R_password);

        radioGroup = findViewById(R.id.radioGroup);
        //Detecto que botón del radio button clicke


        signup_button = findViewById(R.id.Button_SignUp);
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    int radioId = radioGroup.getCheckedRadioButtonId();
                    radioButton_gender = findViewById(radioId);
                    //System.out.println("intento de conexion, sexo: "+radioButton_gender.getText().toString());
                    SignUp( new User(email.getText().toString(), username.getText().toString(),
                            firstName.getText().toString(), lastName.getText().toString(), password.getText().toString()
                            , phone.getText().toString(), radioButton_gender.getText().toString()));
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });
    }
    public void SignUp(User user) throws InterruptedException {

        if (connect_server.addUser(user))
        {
            //Mensaje de Usuario creado
            Context context = getApplicationContext();
            CharSequence text = "Usuario creado exitosamente :)";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            //Cambio de pantalla a la primera
            Intent intent = new Intent(Signup.this, Login.class);
            startActivity(intent);
        }
        else {
            //Mensaje de error
            int duration = Toast.LENGTH_SHORT;
            Context context = getApplicationContext();
            CharSequence text = "Algo salio mal :(, vuelve a intentarlo";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }


}

