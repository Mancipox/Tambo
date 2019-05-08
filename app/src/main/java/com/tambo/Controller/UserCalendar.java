package com.tambo.Controller;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tambo.Connection.Connect_Server;
import com.tambo.Model.Class;
import com.tambo.Model.User;
import com.tambo.R;
import com.tambo.Utils.Utils;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.util.TimeZone;

@RequiresApi(api = Build.VERSION_CODES.N)
public class UserCalendar extends AppCompatActivity {

    CompactCalendarView compactCalendar;
    private RequestQueue queue;
    private ArrayList<Class> aClasses;
    private java.text.SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle extras = getIntent().getExtras();
        final User usermain = (User) extras.get("user");
        final String token = (String) extras.get("token");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_ucalendar);
        //Configuraciones visuales
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(null);
        compactCalendar = findViewById(R.id.compactcalendar_view);

        Locale spanish = new Locale("es", "ES");
        compactCalendar.setLocale(TimeZone.getDefault(), spanish);
        compactCalendar.setUseThreeLetterAbbreviation(true);
        //Volley
        queue = Volley.newRequestQueue(getApplicationContext());
        aClasses = new ArrayList<Class>();
        //Parte BD:
        StringRequest myReq = new StringRequest(Request.Method.GET, Connect_Server.url_server + "ServletClass?option=calendar&user=" + Utils.toJson(usermain) + "&authorization=" + token, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {


                Type QuestionsType = new TypeToken<ArrayList<Class>>() {
                }.getType();
                Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
                aClasses = gson.fromJson(response, QuestionsType);
                final Hashtable<Long, String> eventos= new Hashtable<Long, String>();
                //  Event ev1 = new Event(Color.rgb(153,102,255), 1554833740000L, "Test 1");
                // Event ev2 = new Event(Color.rgb(255,51,153), 1554920140000L, "Test 1");

                for (int i = 0; i < aClasses.size(); i++) {


                    Date daux = new Date(aClasses.get(i).getMeet().getMeetingDate().getTime());
                    long fecha = daux.getTime();
                    String descripcion = aClasses.get(i).getDescription();

                    Event event = new Event(Color.rgb(223,0,84), fecha, descripcion);
                    eventos.put(fecha, descripcion);
                    compactCalendar.addEvent(event);

                }

                compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {

                    @Override
                    public void onDayClick(Date dateClicked) {
                        Context context = getApplicationContext();

                        if (eventos.get(dateClicked.getTime())!=null){

                            Toast.makeText(context,"Quedaste de responder  "+eventos.get(dateClicked.getTime()),Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(context,"No hay reuniones para este día", Toast.LENGTH_SHORT).show();





                        }
                        //Si hay muchos eventos un mismo día
                        //List<Event> events = compactCalendar.getEvents(dateClicked);
                       /*
                        if (dateClicked.toString().compareTo("Fri Oct 21 00:00:00 AST 2016") == 0) {
                            Toast.makeText(context, "Teacher's Professional Day", Toast.LENGTH_SHORT).show();

                        }*/

                    }

                    @Override
                    public void onMonthScroll(Date firstDayOfNewMonth) {
                        actionBar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));
                    }
                });
                gotoToday();
            }

            public void gotoToday() {

                // Set any date to navigate to particular date
                compactCalendar.setCurrentDate(Calendar.getInstance(Locale.getDefault()).getTime());

            }



        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(myReq);


        }

}
