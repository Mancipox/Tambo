package com.tambo.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tambo.Connection.Connect_Server;
import com.tambo.Model.Class;
import com.tambo.Model.User;
import com.tambo.R;
import com.tambo.Utils.Utils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Queue;

public class ContactsActivity extends AppCompatActivity {
    private User usermain;
    private RecyclerView recyclerView;
    private  View view;

    private ArrayList<User> userInfo;
    private RequestQueue queue;
    private AdapterContactList adapter;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        //Obtengo el usuario
        final Bundle extras = getIntent().getExtras();
        usermain = (User) extras.get("user");
        final String uEmail = usermain.getEmail();
         queue =  Volley.newRequestQueue(getApplicationContext());
        recyclerView = findViewById(R.id.contact_recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        final StringRequest myReq = new StringRequest(Request.Method.GET, Connect_Server.url_server + "ServletContact?option=other&user=" + uEmail, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                Type userType = new TypeToken<ArrayList<User>>(){}.getType();
                Gson gson = new GsonBuilder().create();
                userInfo = gson.fromJson(response, userType);


                 adapter = new AdapterContactList(userInfo,getApplicationContext());

                 recyclerView.setAdapter(adapter);

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));





            }



        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error en la petición", Toast.LENGTH_SHORT).show();
            }


        });
        queue.add(myReq);
    }


}
