package com.tambo.Controller;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tambo.Connection.Connect_Server;
import com.tambo.LocalCommunication.DataCommunication;
import com.tambo.Model.Class;
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


public class CalendarFragment extends DialogFragment {
    CompactCalendarView compactCalendar;
    private DataCommunication mCallBack; //To share information between fragments
    private ChooseDateInteracionListener listener;
    private AlertDialog alertDialog;
    private TextView textViewDate;
    private Context context;
    private RequestQueue queue;
    private ArrayList<Class> aClasses;
    private java.text.SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM - yyyy", Locale.getDefault());


    public static CalendarFragment newInstance() {
        CalendarFragment chooseDateDialogFragment = new CalendarFragment();
        Bundle bundle = new Bundle();
        chooseDateDialogFragment.setArguments(bundle);
        return chooseDateDialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.fragment_ucalendar, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        compactCalendar = view.findViewById(R.id.compactcalendar_view);
        textViewDate = view.findViewById(R.id.textViewDate);
        textViewDate.setText(dateFormatMonth.format(Calendar.getInstance(Locale.getDefault()).getTime()));

        Locale spanish = new Locale("es", "ES");
        compactCalendar.setLocale(TimeZone.getDefault(), spanish);
        compactCalendar.setUseThreeLetterAbbreviation(true);

        queue = Volley.newRequestQueue(context);
        aClasses = new ArrayList<Class>();

        StringRequest myReq = new StringRequest(Request.Method.GET, Connect_Server.url_server + "ServletQuestion?option=except&user=" + Utils.toJson(mCallBack.getUser()) + "&authorization=" + mCallBack.getToken(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Type QuestionsType = new TypeToken<ArrayList<Class>>() {
                }.getType();
                Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
                aClasses = gson.fromJson(response, QuestionsType);
                final Hashtable<Long, String> eventos = new Hashtable<Long, String>();
                for (int i = 0; i < aClasses.size(); i++) {


                    Date daux = new Date(aClasses.get(i).getMeet().getMeetingDate().getTime());
                    long fecha = daux.getTime();
                    String descripcion = aClasses.get(i).getDescription();

                    Event event = new Event(Color.rgb(223, 0, 84), fecha, descripcion);
                    eventos.put(fecha, descripcion);
                    compactCalendar.addEvent(event);

                }

                compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {

                    @Override
                    public void onDayClick(Date dateClicked) {

                        if (eventos.get(dateClicked.getTime()) != null) {

                            Toast.makeText(context, "Quedaste de responder  " + eventos.get(dateClicked.getTime()), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "No hay reuniones para este día", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onMonthScroll(Date firstDayOfNewMonth) {
                        textViewDate.setText(dateFormatMonth.format(firstDayOfNewMonth));
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
                        Toast.makeText(context, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(myReq);


        builder.setView(view);

        alertDialog = builder.create();

        return alertDialog;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            this.context = context;
            mCallBack = (DataCommunication) context;
        } catch (ClassCastException e) {

        }
    }


    public interface ChooseDateInteracionListener {
        void stablishNewDate(Calendar date);
    }
}
