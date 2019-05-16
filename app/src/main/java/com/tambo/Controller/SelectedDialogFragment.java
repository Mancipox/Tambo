package com.tambo.Controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tambo.Connection.Connect_Server;
import com.tambo.LocalCommunication.DataCommunication;
import com.tambo.Model.Class;
import com.tambo.R;
import com.tambo.Utils.Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * DialogFragment to select a question by professor @BD
 */
public class SelectedDialogFragment extends DialogFragment {

    private DataCommunication mCallBack; //To share information between fragments
    private Context context;

    private static DataCommunication.DialogCallback dialogCallback;

    private TextView textViewName;
    private TextView textViewAskBy;
    private TextView textViewPlace;
    private TextView textViewDate;

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Fecha
    final int currentMonth = c.get(Calendar.MONTH);
    final int currentDay = c.get(Calendar.DAY_OF_MONTH);
    final int currentYear = c.get(Calendar.YEAR);


    public static SelectedDialogFragment newInstance(DataCommunication.DialogCallback dialogCallback){
        SelectedDialogFragment.dialogCallback = dialogCallback;
        SelectedDialogFragment dialogFragment = new SelectedDialogFragment();
        Bundle nBundle = new Bundle();
        dialogFragment.setArguments(nBundle);
        return dialogFragment;
    }

    /**
     * Override the Fragment.onAttach() method to instantiate the {@link DataCommunication}
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mCallBack = (DataCommunication) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement DataCommunication");
        }
    }


    /**
     * Create the dialog and listen the positive/negative operations
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater(); //Create the container to dialog's template
        View layout = inflater.inflate(R.layout.dialog_select_template, null); //Get the layout of the dialog's template
        textViewName = layout.findViewById(R.id.textSelectDialog);
        textViewAskBy = layout.findViewById(R.id.question_askedby);
        textViewPlace = layout.findViewById(R.id.question_place);
        textViewDate = layout.findViewById(R.id.question_date);
        context = getContext();

        final Class classSelected = mCallBack.getClassProfessor();
        textViewName.setText(getText(R.string.textDialogSelect)+" "+ classSelected.getDescription());
        textViewAskBy.setText(getText(R.string.textDialogSelectAskBy)+" "+ classSelected.getUserAnsw().getUserName());
        textViewPlace.setText(getText(R.string.textDialogSelectPlace)+" "+ classSelected.getMeet().getPlace());
        textViewDate.setText(getText(R.string.textDialogSelectDate)+" "+new SimpleDateFormat("dd/MM/yyyy").format(classSelected.getMeet().getMeetingDate()));

        builder.setView(layout);

        builder.setPositiveButton("¡Lo acepto!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO:Check if the class is before today's date
                if(classSelected.getTeacherEmail()==null /*&& validateDate(classSelected)*/){
                    classSelected.setTeacherEmail(mCallBack.getUser());
//                    dialogCallback.updateRecyclerView(classSelected.isState());
                   // questionSelected.setState(true);
                    RequestQueue queue = Volley.newRequestQueue(getContext());
                    /*String token=null;
                    try {
                        FileInputStream fis = context.openFileInput("TokenFile");
                        token = String.valueOf(fis.read());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    final String finalToken = token;*/
                    StringRequest myReq = new StringRequest(Request.Method.POST, Connect_Server.url_server + "ServletClass", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Boolean r=(Boolean) Utils.fromJson(response, Boolean.class);
                            if (r){
                                dialogCallback.updateRecyclerView(false);
                                Toast.makeText(context, "Aceptado", Toast.LENGTH_SHORT).show();
                                //Snackbar.make(getActivity().findViewById(android.R.id.content), "Pregunta enviada", Snackbar.LENGTH_LONG).show(); //Succefull message
                            }
                            else{
                                Toast.makeText(context, "Algo salió mal", Toast.LENGTH_SHORT).show();
                                //Snackbar.make(getActivity().findViewById(android.R.id.content),"Algo salio mal",Snackbar.LENGTH_SHORT).show();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context,"ERROR RESPONSE",Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        protected Map<String, String> getParams() {
                            Map<String, String> MyData = new HashMap<>();
                          //  MyData.put("option","teacher");
                            MyData.put("Class", Utils.toJson(classSelected));
                           // MyData.put("authorization ", finalToken);
                            return MyData;
                        }
                    };
                    queue.add(myReq);
                }else{
                    Toast.makeText(context, "Esta pregunta ya está aceptada", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("No, no me interesa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Snackbar.make(getActivity().findViewById(android.R.id.content),"Cancelado",Snackbar.LENGTH_LONG).show(); //Succefull message
            }
        });

        return builder.create();
    }


    public boolean validateDate(Class classSelected){
        Date dateClass = classSelected.getMeet().getMeetingDate();
        if(dateClass.getDay()<=currentDay && dateClass.getMonth()<=currentMonth && dateClass.getYear()<=currentYear) {
            Log.d("Date log","The date of the class is before of today's date");
            return false;
        }
        else {
            Log.d("Date log","The date of the class is after of today's date");
            return true;
        }
    }
}
