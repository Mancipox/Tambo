package com.tambo.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Password;

import com.tambo.Utils.Utils;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.tambo.Connection.Connect_Server;
import com.tambo.Model.User;
import com.tambo.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Login extends AppCompatActivity implements Validator.ValidationListener {


    private Toolbar toolbar;

    @Email (message = "Email no válido")
    private EditText email;
    @Password(message = "Contraseña no válida")
    private EditText password;
    private TextView textViewRegistro;
    private Button button_ingreso;
    private Validator validator;
    protected boolean validated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email= findViewById(R.id.email);
        password=findViewById(R.id.input_password);
        button_ingreso = findViewById(R.id.login_button);
        textViewRegistro = findViewById(R.id.link_signup);

        validator = new Validator(this);
        validator.setValidationListener(this);



        button_ingreso.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                validator.validate();
                /*
                 try{
                 login(email.getText().toString(),password.getText().toString());

                 }catch (Exception e){
                 e.printStackTrace();
                 }
                 */
                final User user_aux= new User (email.getText().toString().trim(),password.getText().toString());
                RequestQueue  queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest myReq = new StringRequest(Request.Method.POST, Connect_Server.url_server + "ServletUser", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String[] respSplit = response.split("/");
                        String token= respSplit[1];

                        User user_temp = (User) Utils.fromJson(respSplit[0], User.class);
                        if (user_temp != null) {
                            Intent intent = new Intent(Login.this, YekabeActivity.class);
                            intent.putExtra("user", user_temp);
                            intent.putExtra("token",token);
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


        textViewRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartSignUp(v);
            }
        });
    }

    public void StartSignUp(View view){
        Intent intent = new Intent(Login.this, SignIn.class);
        startActivity(intent);
    }

    @Override
    public void onValidationSucceeded() {
        validated = true;

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        validated=false;
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            EditText et = (EditText) view;
            et.setError(message);
        }


    }

}
