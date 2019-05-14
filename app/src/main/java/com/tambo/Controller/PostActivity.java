package com.tambo.Controller;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.tambo.Connection.Connect_Server;
import com.tambo.Model.Class;
import com.tambo.Model.Meeting;
import com.tambo.Model.Topic;
import com.tambo.Model.User;
import com.tambo.R;
import com.tambo.Utils.Utils;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostActivity extends AppCompatActivity implements Validator.ValidationListener, View.OnClickListener {
    @NotEmpty(message = "Por favor ingresa lo que quieras aprender")
    private EditText editTextQuestion;
    @NotEmpty(message = "Por favor ingresa una descripción")
    private EditText editTextDescription;
    private FloatingActionButton floatingActionButtonPost;
    private Toolbar toolbar;

    private Spinner spinner;

    protected Validator validator;

    private double latitude;
    private double longitude;
    private String address;

    private User usertemp;
    private String token;
    private Date date;
    private String tag;

    private Bundle bundle;
    private Context context;

    private static final String ZERO = "0";
    private static final String DOUBLE_DOTS= ":";
    private static final String SLASH = "/";

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Fecha
    final int currentMonth = c.get(Calendar.MONTH);
    final int currentDay = c.get(Calendar.DAY_OF_MONTH);
    final int currentYear = c.get(Calendar.YEAR);

    //Hora
    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minute = c.get(Calendar.MINUTE);

    //Request code
    private final int REQUEST_PLACE = 125;

    //Widgets
    @NotEmpty(message = "Por favor ingresa una fecha")
    EditText etFecha;
    @NotEmpty(message = "Por favor ingresa una hora")
    EditText etHora;
    @NotEmpty(message = "Por favor ingresa un lugar")
    EditText etPlace;
    ImageButton ibObtenerFecha, ibObtenerHora, ibObtenerLugar;

    //Validation date
    private Date now;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        context = this;
        bundle = getIntent().getExtras();
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextQuestion = findViewById(R.id.editTextQuestion);

        etFecha = (EditText) findViewById(R.id.et_mostrar_fecha_picker);
        etHora = (EditText) findViewById(R.id.et_mostrar_hora_picker);
        etPlace = (EditText) findViewById(R.id.editTextPlace);

        ibObtenerFecha = (ImageButton) findViewById(R.id.button_getDate);
        ibObtenerHora = (ImageButton) findViewById(R.id.button_getTime);
        ibObtenerLugar = (ImageButton)  findViewById(R.id.button_getPos);

        toolbar = (Toolbar) findViewById(R.id.toolbar_post);

        usertemp = (User)bundle.get("usermain");
        token = (String)bundle.get("token");

        floatingActionButtonPost = findViewById(R.id.floatingActionButtonPost);

        spinner = (Spinner) findViewById(R.id.spinner_tag);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.tags_array_post,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tag = String.valueOf(parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Do nothing
            }

        });

        validator = new Validator(this);
        validator.setValidationListener(this);

        date = new Date();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.toolbar_title_post);

        ibObtenerFecha.setOnClickListener(this);
        ibObtenerHora.setOnClickListener(this);
        ibObtenerLugar.setOnClickListener(this);

    }


    @Override
    public void onValidationSucceeded() {
        if (usertemp.getKarma() >= 1) {
            usertemp.setKarma(usertemp.getKarma() - 1);
            now = new Date();

            if(tag.equals("Selecciona una etiqueta"))tag="Otros";
            //TODO: Create a new field to description of a meeting
            Meeting meet = new Meeting(date, editTextDescription.getText().toString());
            Topic topic = new Topic();
            topic.setDescription(tag);

            final Class classtemp = new Class(editTextQuestion.getText().toString(),1,false, meet,usertemp,topic);

            // POST
            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest myReq = new StringRequest(Request.Method.POST, Connect_Server.url_server + "ServletClass", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Boolean r = (Boolean) Utils.fromJson(response, Boolean.class);
                    if (r) {
                        Intent intent=new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("question",classtemp);
                        intent.putExtra("bundle",bundle);
                        setResult(RESULT_OK,intent);
                        if(now.getDay()==date.getDay() && now.getMonth()==date.getMonth() && now.getYear()==date.getYear())
                        Toast.makeText(context, "Clase posteada... Parece que tienes algo de prisa", Toast.LENGTH_SHORT).show();
                        else Toast.makeText(context, "Clase posteada", Toast.LENGTH_SHORT).show();
                        floatingActionButtonPost.setClickable(true);
                        finish();
                    } else {
                        Toast.makeText(context, "Algo salió mal", Toast.LENGTH_SHORT).show();
                        floatingActionButtonPost.setClickable(true);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Algo salió mal", Toast.LENGTH_SHORT).show();
                    floatingActionButtonPost.setClickable(true);
                }
            }) {
                protected Map<String, String> getParams() {
                    Map<String, String> MyData = new HashMap<>();
                    MyData.put("option", "create");
                    MyData.put("Class", Utils.toJson(classtemp));
                    MyData.put("tag",Utils.toJson(tag));
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
        floatingActionButtonPost.setClickable(false);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_getDate:
                getDate(); break;
            case R.id.button_getTime:
                getTime(); break;
            case R.id.button_getPos:
                getPlace(); break;
        }
    }


    private void getDate(){
        DatePickerDialog pickerDate = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                if ((year < currentYear) || (month < currentMonth&& year == currentYear) || (dayOfMonth < currentDay && year == currentYear&& month== currentMonth)){
                    Toast.makeText(context, R.string.error_date, Toast.LENGTH_LONG).show();
                    view.updateDate(currentYear, currentMonth, currentDay);
                    dayOfMonth = currentDay;
                    month =currentMonth;
                    year = currentYear;
                }

                final int actualMonth = month + 1;

                String realDay = (dayOfMonth < 10)? ZERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                String realMont = (actualMonth < 10)? ZERO+ String.valueOf(actualMonth):String.valueOf(actualMonth);


                etFecha.setText(realDay  + SLASH + realMont + SLASH+ year);
                date.setYear(year-1900);
                date.setMonth(month);
                date.setDate(dayOfMonth);

            }

        }, currentYear, currentMonth, currentDay);

        pickerDate.show();

    }

    private void getTime(){
        TimePickerDialog pickerTime = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                String realHour =  (hourOfDay < 9)? String.valueOf(ZERO + hourOfDay) : String.valueOf(hourOfDay);
                String realMinute = (minute < 9)? String.valueOf(ZERO+ minute):String.valueOf(minute);

                String AM_PM;
                if(hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }

                etHora.setText(realHour  + DOUBLE_DOTS + realMinute + " " + AM_PM);
                date.setHours(hourOfDay);
                date.setMinutes(minute);
            }

        }, hora, minute, false);

        pickerTime.show();
    }

    private void getPlace(){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivityForResult(intent, REQUEST_PLACE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        String place = resultData.getStringExtra("address");
        latitude = Double.parseDouble(resultData.getStringExtra("latitude"));
        longitude = Double.parseDouble(resultData.getStringExtra("longitude"));;
        Log.d("Info response post act",place+" - - "+latitude+" - - "+longitude);
        address = place;
        etPlace.setText(place);
    }


}


