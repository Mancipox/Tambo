package com.tambo.Controller;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.shape.CutCornerTreatment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.tambo.Utils.Utils;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.tambo.Connection.Connect_Server;
import com.tambo.Model.User;
import com.tambo.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SignIn extends AppCompatActivity {
    private EditText username;
    private EditText firstName;
    private EditText lastName;
    private EditText password;
    private EditText email;
    private  EditText phone;
    private RadioButton radioButton_gender;
    private RadioGroup radioGroup;
    private Button signup_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
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
/*
                    SignUp( new User(email.getText().toString(), username.getText().toString(),
                            firstName.getText().toString(), lastName.getText().toString(), password.getText().toString()
                            , phone.getText().toString(), radioButton_gender.getText().toString()));
                            */
                    final User user_aux= new User(email.getText().toString(), username.getText().toString(),
                            firstName.getText().toString(), lastName.getText().toString(), password.getText().toString()
                            , phone.getText().toString(), radioButton_gender.getText().toString());
                    user_aux.setKarma(10);
                    RequestQueue  queue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest myReq = new StringRequest(Request.Method.POST, CustomItemClickListener.url_server + "ServletUser", new Response.Listener<String>() {
                        public void onResponse(String response) {

                            Boolean r = (Boolean) Utils.fromJson(response, Boolean.class);
                            if (r){
                                Context context = getApplicationContext();
                                CharSequence text = "Usuario creado exitosamente :)";
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                                //Cambio de pantalla a la primera
                                Intent intent = new Intent(SignIn.this, Login.class);
                                startActivity(intent);
                            }else{
                                //Mensaje de error
                                int duration = Toast.LENGTH_SHORT;
                                Context context = getApplicationContext();
                                CharSequence text = "Algo salio mal :(, vuelve a intentarlo";
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                          Mensaje de error
                            int duration = Toast.LENGTH_SHORT;
                            Context context = getApplicationContext();
                            CharSequence text = "Algo salio mal :(, vuelve a intentarlo";
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    }){
                        protected Map<String, String> getParams() {
                            Map<String, String> MyData = new HashMap<String, String>();
                            MyData.put("option", "create");
                            MyData.put("user", Utils.toJson(user_aux));
                            return MyData;
                        }
                    };
                    queue.add(myReq);
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });
    }
    /*
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
            Intent intent = new Intent(SignIn.this, Login.class);
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
*/




}

