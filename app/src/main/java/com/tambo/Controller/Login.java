package com.tambo.Controller;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.tambo.Connection.Connect_Server;
import com.tambo.Model.User;
import com.tambo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Login extends AppCompatActivity {


    private EditText email;
    private EditText password;
    private Button button_signup;
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
        button_ingreso = findViewById(R.id.login_button);
        button_signup=findViewById(R.id.button2);

        button_ingreso.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /**
                try{
                    login(email.getText().toString(),password.getText().toString());

                }catch (Exception e){
                    e.printStackTrace();
                }
                 */
                User user_aux= new User (email.getText().toString(),password.getText().toString());
                LoginAsyncTask task = new LoginAsyncTask();
                task.execute(user_aux);
            }


        });
        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    StartSignUp(v);

                }catch (Exception e){

                }
            }
        });

    }

    public void StartSignUp(View view){
        Intent intent = new Intent(Login.this, SignIn.class);
        startActivity(intent);
    }
/*
    public void login(String mail, String password) throws InterruptedException {
        if (connect_server.isUser(new User(mail,password))){
            User usermain = connect_server.getUser(mail);
            Intent intent = new Intent(Login.this, YekabeActivity.class);
            intent.putExtra("user",usermain);
            startActivity(intent);
        }
        else {
            Toast.makeText(this,"Correo o contraseña incorrecta",Toast.LENGTH_SHORT).show();
        }
    }*/

    private class LoginAsyncTask extends AsyncTask<User, Integer, User>{



        @Override
        protected User doInBackground(User... users) {

            //JSON al objeto y envio de petición
            Gson gson = new Gson();
            String credenciales =gson.toJson(users[0]);
            // Instantiate the RequestQueue.
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "localhost:8080/ServletUser";
            String jsonStr = sh.LoginRequest(url,credenciales);

            User returnedUser = gson.fromJson(jsonStr,User.class);




            return returnedUser;
        }
        protected void  onPostExecute(User response){
            if(response.getEmail()!=null){

                Intent intent = new Intent(Login.this, YekabeActivity.class);
                //Busqueda con JSON de usuario
                intent.putExtra("user",response.getEmail());
                startActivity(intent);

            }
            else {
                int duration = Toast.LENGTH_SHORT;
                Context context = getApplicationContext();
                CharSequence text = "Correo o contraseña erronea";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            }
        }
        /*
             protected void  onPostExecute(Boolean response){
            if(response==true){

                Intent intent = new Intent(Login.this, YekabeActivity.class);
                startActivity(intent);

            }
            else {
                Toast.makeText(getApplicationContext(),"Correo o contraseña erronea",Toast.LENGTH_SHORT).show();
            }
        }
         */
    }

}
