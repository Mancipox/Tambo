package com.tambo.Controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.tambo.Utils.Utils;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tambo.Connection.Connect_Server;
import com.tambo.LocalCommunication.DataCommunication;
import com.tambo.Model.Meeting;
import com.tambo.Model.Question;
import com.tambo.Model.User;
import com.tambo.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * DialogFragment to set a date and decribe a question by student @BD
 */
public class DescribeDialogFragment extends DialogFragment implements Validator.ValidationListener {
    DataCommunication mCallBack; //To communicate between fragments
    /**
     * Text viewed in the dialog
     */
    private TextView textQuestion;
    /**
     * Calendar viewed in the dialog
     */
    private CalendarView calendarView;
    /**
     * Text field to add a description
     */
    @NotEmpty(message = "Por favor ingresa una descripción")
    private EditText textDescription;
    private Context context;
    protected Validator validator;
    private static DataCommunication.DialogCallback dialogCallback;

    public static DescribeDialogFragment newInstance(DataCommunication.DialogCallback dialogCallback){
        DescribeDialogFragment.dialogCallback = dialogCallback;
        DescribeDialogFragment dialogFragment = new DescribeDialogFragment();
        Bundle nBundle = new Bundle();
        dialogFragment.setArguments(nBundle);
        return dialogFragment;
    }

    /**
     * Obligatory attach the fragment to main activity to pass information with {@link DataCommunication}
     *
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
                    + " must implement NoticeDialogListener and DataCommunication");
        }
    }

    /**
     * Create the dialog and listen the positive/negative operations
     *
     * @param savedInstanceState
     * @return
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //connect_server = new Connect_Server();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); //Create the AlertDialog
        LayoutInflater inflater = getActivity().getLayoutInflater(); //Create the container to dialog's template
        View layout = inflater.inflate(R.layout.dialog_describe_template, null); //Get the layout of the dialog's template
        context = getContext();

        //Initialice attributes
        textQuestion = layout.findViewById(R.id.textQuestionDialog);
        calendarView = layout.findViewById(R.id.calendarView);
        textDescription = layout.findViewById(R.id.editTextUbicationQuestion);

        //Set info obtained from main activity in the layout of dialog
        textQuestion.setText(textQuestion.getText() + mCallBack.getQuestionText());
        builder.setView(layout); //Show it
        //Validator Stuff
        validator = new Validator(this);
        validator.setValidationListener(this);
        validator.validate();

        //Set button "Enviar"
        builder.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Check if the user has enougth karma to do the question !!
                final User usertemp = mCallBack.getUser();
                if (usertemp.getKarma() >= 1) {
                    usertemp.setKarma(usertemp.getKarma() - 1);
                    Meeting meet = new Meeting(new Date(calendarView.getDate()), textDescription.getText().toString());
                    final Question questemp = new Question(usertemp, false, mCallBack.getQuestionText(), 1, meet);
                    // POST

                    RequestQueue queue = Volley.newRequestQueue(getContext());
                    StringRequest myReq = new StringRequest(Request.Method.POST, Connect_Server.url_server + "ServletQuestion", new Response.Listener<String>()  {
                        @Override
                        public void onResponse(String response) {
                            Boolean r = (Boolean) Utils.fromJson(response, Boolean.class);
                            if (r) {
                                dialogCallback.updateRecyclerView(questemp);
                                Toast.makeText(context, "Pregunta enviada", Toast.LENGTH_SHORT).show();
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
                            MyData.put("authorization", mCallBack.getToken());
                            return MyData;
                        }
                    };
                    queue.add(myReq);

                } else {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "Insuficiente karma", Snackbar.LENGTH_LONG).show(); //Error message
                }
            }
        });

        //Set button "Cancelar"
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Cancel, return back
                //Send the fragment to main activity
                Snackbar.make(getActivity().findViewById(android.R.id.content), "Pregunta cancelada", Snackbar.LENGTH_LONG).show();

            }
        });
        return builder.create(); //Show the dialog
    }

    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(context);



            EditText et = (EditText) view;
            et.setError(message);
        }

    }
}

/**
 * Biblio from DialogFragment
 * https://developer.android.com/guide/topics/ui/dialogs?hl=es-419
 */