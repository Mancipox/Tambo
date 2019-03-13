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

import com.google.gson.Gson;
import com.tambo.Connection.Connect_Server;
import com.tambo.Model.User;
import com.tambo.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class Login extends AppCompatActivity {


    private EditText email;
    private EditText password;
    private Button button_signup;
    private Button button_ingreso;
    public Connect_Server connect_server;
    private static final String TAMBO_REQUEST_URL="";

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
            try {
                URL url = new URL(TAMBO_REQUEST_URL);
            }
            catch (IOException e){
                e.printStackTrace();
            }



            return null;
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
    private String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(Login.class.getSimpleName(), "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(Login.class.getSimpleName(), "Problem retrieving the  JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convierte inpustream a un String que contiene el JSON
     * @param inputStream
     * @return
     * @throws IOException
     */
    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

}
