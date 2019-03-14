package com.tambo.Controller;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.tambo.Utils.Utils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tambo.Connection.Connect_Server;
import com.tambo.Model.User;
import com.tambo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {


    private EditText email;
    private EditText password;
    private Button button_signup;
    private Button button_ingreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
                final User user_aux= new User (email.getText().toString(),password.getText().toString());
                RequestQueue  queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest myReq = new StringRequest(Request.Method.POST, CustomItemClickListener.url_server + "ServletUser", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("Ingresa a OnResponse de Login");
                        User user_temp = (User) Utils.fromJson(response, User.class);
                        if (user_temp != null) {
                            Intent intent = new Intent(Login.this, YekabeActivity.class);
                            intent.putExtra("user", user_temp);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(),"Correo o contraseña incorrecta",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Error en la petición",Toast.LENGTH_SHORT).show();
                    }
                }){
                    protected Map<String, String> getParams() {
                        Map<String, String> MyData = new HashMap<String, String>();
                        MyData.put("option", "login");
                        MyData.put("user", Utils.toJson(user_aux));
                        return MyData;
                    }

                };
                queue.add(myReq);
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

}
