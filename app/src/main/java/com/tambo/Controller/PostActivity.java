package com.tambo.Controller;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.tambo.Connection.Connect_Server;
import com.tambo.LocalCommunication.DataCommunication;
import com.tambo.Model.Meeting;
import com.tambo.Model.Question;
import com.tambo.Model.User;
import com.tambo.R;
import com.tambo.Utils.Utils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostActivity extends AppCompatActivity implements Validator.ValidationListener {
    @NotEmpty(message = "Por favor ingresa una pregunta")
    private EditText editTextQuestion;
    @NotEmpty(message = "Por favor ingresa una descripción")
    private EditText editTextDescription;
    private CalendarView calendarView;
    private FloatingActionButton floatingActionButtonPost;

    protected Validator validator;

    private User usertemp;
    private String token;

    private Bundle bundle;
    private Context context;
    private static DataCommunication.DialogCallback dialogCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        context = this;
        bundle = getIntent().getExtras();
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextQuestion = findViewById(R.id.editTextQuestion);
        calendarView = findViewById(R.id.calendarView);

        usertemp = (User)bundle.get("usermain");
        token = (String)bundle.get("token");

        floatingActionButtonPost = findViewById(R.id.floatingActionButtonPost);
        validator = new Validator(this);
        validator.setValidationListener(this);
    }


    @Override
    public void onValidationSucceeded() {
        if (usertemp.getKarma() >= 1) {
            usertemp.setKarma(usertemp.getKarma() - 1);
            Meeting meet = new Meeting(new Date(calendarView.getDate()), editTextDescription.getText().toString());
            final Question questemp = new Question(usertemp, false, editTextQuestion.getText().toString(), 1, meet);
            // POST
            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest myReq = new StringRequest(Request.Method.POST, Connect_Server.url_server + "ServletQuestion", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Boolean r = (Boolean) Utils.fromJson(response, Boolean.class);
                    if (r) {
                        Intent intent=new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("question",questemp);
                        intent.putExtra("bundle",bundle);
                        setResult(2,intent);
                        Toast.makeText(context, "Pregunta enviada", Toast.LENGTH_SHORT).show();
                        finish();
                        //Snackbar.make(getActivity().findViewById(android.R.id.content), "Pregunta enviada", Snackbar.LENGTH_LONG).show(); //Succefull message
                    } else {
                        Toast.makeText(context, "Algo salió mal", Toast.LENGTH_SHORT).show();
                        //Snackbar.make(getActivity().findViewById(android.R.id.content),"Algo salio mal",Snackbar.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                protected Map<String, String> getParams() {
                    Map<String, String> MyData = new HashMap<>();
                    MyData.put("option", "create");
                    MyData.put("Question", Utils.toJson(questemp));
                    MyData.put("authorization", token);
                    return MyData;
                }
            };
            queue.add(myReq);
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Insuficiente karma", Snackbar.LENGTH_LONG).show(); //Error message
        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            EditText et = (EditText) view;
            et.setError(message);
        }
        Snackbar.make(findViewById(android.R.id.content), "No llenaste todos los campos", Snackbar.LENGTH_LONG).show(); //Error message
    }

    public void onClickPost(View v) {
        validator.validate();
    }
}
