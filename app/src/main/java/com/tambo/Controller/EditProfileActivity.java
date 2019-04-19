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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import com.tambo.Connection.Connect_Server;
import com.tambo.Model.User;
import com.tambo.R;
import com.tambo.Utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity implements Validator.ValidationListener {
    @NotEmpty(message = "Por favor ingresa un nombre de usuario")
    private EditText editTextUserName;
    @Email(message = "Email no válido")
    private EditText editTextEmail;
    @NotEmpty(message ="Por favor ingresa tu nombre")
    private EditText editTextName;
    @NotEmpty(message = " Por favor ingresa tu apellido")
    private EditText editTextLastName;
    @Pattern(regex = "[3]{1}[0-9]{9}",message = "Número telefónico no válido")
    private EditText editTextNumber;
    private Button editButton2;

    private RadioButton radioButton_gender;

    private RadioGroup radioGroup;
    private User u;
    private String sendtoken;
    protected Validator validator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        final Bundle extras = getIntent().getExtras();
        u = (User)extras.get("user");
        sendtoken = (String) extras.get("token");
        editButton2=findViewById(R.id.edit_button);
        editTextUserName=findViewById(R.id.edit_username);
        editTextUserName.setText(u.getUserName());
        editTextEmail=findViewById(R.id.edit_email);
        editTextEmail.setText(u.getEmail());
        editTextName=findViewById(R.id.edit_name);
        editTextName.setText(u.getFirstName());
        editTextLastName=findViewById(R.id.edit_last_name);
        editTextLastName.setText(u.getLastName());
        editTextNumber=findViewById(R.id.edit_number);
        editTextNumber.setText(u.getPhone());
        radioGroup = findViewById(R.id.radio_group_edit);
        validator = new Validator(this);
        validator.setValidationListener(this);

        editButton2=findViewById(R.id.edit_profile_button);



        editButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton_gender = findViewById(radioId);
                u= new User(editTextEmail.getText().toString(), editTextUserName.getText().toString(),
                        editTextName.getText().toString(), editTextLastName.getText().toString(),
                        editTextNumber.getText().toString(), radioButton_gender.getText().toString());
                validator.validate();

            }
        });



    }


    @Override
    public void onValidationSucceeded() {


        /*
        final User user_aux= new User(.getText().toString(), username.getText().toString(),
                firstName.getText().toString(), lastName.getText().toString(), password.getText().toString()
                , phone.getText().toString(), radioButton_gender.getText().toString());*/
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest myReq = new StringRequest(Request.Method.POST, Connect_Server.url_server + "ServletUser", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Boolean r = (Boolean) Utils.fromJson(response, Boolean.class);
                if (r) {
                    Toast.makeText(getApplicationContext(), "Datos modificados exitosamente!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditProfileActivity.this, YekabeActivity.class);
                    intent.putExtra("user",u);
                    intent.putExtra("token",sendtoken);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Algo salió mal lo datos no fueron modificador", Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Algo salio mal, es culpa del servidor :( o la petición :c", Toast.LENGTH_SHORT).show();

            }
        }){
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("option", "update");
                MyData.put("user", Utils.toJson(u));
                return MyData;
            }
        };
        queue.add(myReq);


    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);



            EditText et = (EditText) view;
            et.setError(message);

        }

    }

}
