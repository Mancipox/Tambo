package com.tambo.Controller;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.ActionBar;import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tambo.Connection.Connect_Server;
import com.tambo.LocalCommunication.DataCommunication;
import com.tambo.Model.Question;
import com.tambo.Model.User;
import com.tambo.R;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.github.sundeepk.compactcalendarview.domain.Event;
import com.tambo.Utils.Utils;

public class UserCalendarFragment extends Fragment {
    //de la libreria importada
    CompactCalendarView compactCalendar;
    private User mainUser;
    private DataCommunication mCallBack;
    private ArrayList<Question> questions;

    private SimpleDateFormat dateFormatMonth= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    private RequestQueue queue;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mCallBack = (DataCommunication) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString()+" must implement DataCommunication");
        }
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mainUser = mCallBack.getUser();
        View view = inflater.inflate(R.layout.fragment_ucalendar, container, false);

        final ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(null);
        compactCalendar = (getActivity()).findViewById(R.id.compactcalendar_view);
        Locale spanish = new Locale("es", "ES");
        compactCalendar.setLocale(TimeZone.getDefault(), spanish);
        compactCalendar.setUseThreeLetterAbbreviation(true);

        queue = Volley.newRequestQueue(getContext());
        questions = new ArrayList<Question>();

        //Obtener de la BD todos los eventos de un usuario
        StringRequest myReq = new StringRequest(Request.Method.GET, Connect_Server.url_server + "ServletQuestion?option=all&user=" + Utils.toJson(mainUser) + "&authorization=" + mCallBack.getToken(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Type QuestionsType = new TypeToken<ArrayList<Question>>() {
                }.getType();
                Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
                questions = gson.fromJson(response, QuestionsType);
                final Hashtable<Long, String> eventos= new Hashtable<Long, String>();
              //  Event ev1 = new Event(Color.rgb(153,102,255), 1554833740000L, "Test 1");
               // Event ev2 = new Event(Color.rgb(255,51,153), 1554920140000L, "Test 1");
                for (int i = 0; i <= questions.size(); i++) {
                    Date daux = new Date(questions.get(i).getMeet().getMeetingDate().getTime());
                    long fecha = daux.getTime();
                    String descripcion = questions.get(i).getDescription();
                    Event event = new Event(Color.rgb(153,102,255), fecha, descripcion);
                    eventos.put(fecha, descripcion);
                    compactCalendar.addEvent(event);
                }
                compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {

                    @Override
                    public void onDayClick(Date dateClicked) {
                        Context context = getActivity().getApplicationContext();
                        if (eventos.get(dateClicked.getTime())!=null){
                            Toast.makeText(context," "+eventos.get(dateClicked.getTime()),Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(myReq);


        //Creacion de eventos para el calendario
        /*
        Date d = new Date(1554248666);

        Event event= new Event(Color.RED,d.getTime(),"Evento de prueba");
        compactCalendar.addEvent(event);

        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {

            @Override
            public void onDayClick(Date dateClicked) {
               Context context = getActivity().getApplicationContext();

                if (dateClicked.toString().compareTo("Fri Oct 21 00:00:00 AST 2016") == 0) {
                    Toast.makeText(context, "Teacher's Professional Day", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                actionBar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));
            }

        });
        gotoToday();
        return  view;
    }
    public void gotoToday() {

        // Set any date to navigate to particular date
        compactCalendar.setCurrentDate(Calendar.getInstance(Locale.getDefault()).getTime());

    }
    */
        return view;
    }

}
