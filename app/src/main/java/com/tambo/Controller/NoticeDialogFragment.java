package com.tambo.Controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import com.tambo.LocalCommunication.DataCommunication;
import com.tambo.Model.Question;
import com.tambo.Model.User;
import com.tambo.R;

import java.util.Date;


/**
 * This class send information to main activity from DialogFragment
 * @author mancipox
 */
public class NoticeDialogFragment extends DialogFragment {
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
    private EditText textDescription;
    private User user;


    DataCommunication mCallBack;

    /**
     * The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it.
     */
    public interface NoticeDialogListener {
        void onDialogPositiveClick(DialogFragment dialog);
        void onDialogNegativeClick(DialogFragment dialog);
    }


    /**
     * This is an instance of the interface to deliver action events to the main activity
     */
    NoticeDialogListener mListener;


    /**
     * Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
            mCallBack = (DataCommunication) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener and DataCommunication");
        }
    }

    /**
     * Create the dialog based in information passed from root activity
     * @param savedInstanceState
     * @return
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); //Create the AlertDialog
        LayoutInflater inflater = getActivity().getLayoutInflater(); //Create the container to dialog's template
        View layout = inflater.inflate(R.layout.dialog_template, null); //Get the layout of the dialog's template

        //Initialice attributes
        textQuestion = layout.findViewById(R.id.textQuestionDialog);
        calendarView = layout.findViewById(R.id.calendarView);
        textDescription = layout.findViewById(R.id.editTextUbicationQuestion);

        //Set info obtained from main activity in the layout of dialog
        textQuestion.setText(textQuestion.getText()+mCallBack.getQuestionText());
        builder.setView(layout); //Show it

        //Set button "Enviar"
        builder.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Check if the user has enougth karma to do the question !!
                if(mCallBack.getUser().createQuestion(15,textDescription.getText().toString(),10,5, (String.valueOf(calendarView.getDate())),textDescription.getText().toString())){
                    Question question = new Question(15,user,null, false, textDescription.getText().toString(), 5,null); //Question created
                    mCallBack.addQuestionStudent(question);//Added to list
                    mCallBack.getAdapterQuestion().notifyDataSetChanged(); //Realod RecyclerView
                    Snackbar.make(getActivity().findViewById(android.R.id.content),"Pregunta enviada",Snackbar.LENGTH_LONG).show(); //Succefull message

                    //Send the question to server !!
                    //Update the status of the user !!

                }else{
                    Snackbar.make(getActivity().findViewById(android.R.id.content),"Insuficiente karma",Snackbar.LENGTH_LONG).show(); //Error message
                }

                mListener.onDialogPositiveClick(NoticeDialogFragment.this); //The user has pushed the "Enviar" button, the main activity now knows it
            }
        });

        //Set button "Cancelar"
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Cancel, return back
                //Send the fragment to main activity
                Snackbar.make(getActivity().findViewById(android.R.id.content),"Pregunta cancelada",Snackbar.LENGTH_LONG).show();
                mListener.onDialogNegativeClick(NoticeDialogFragment.this); //The user has pushed the "Cancelar" button, the main activity now knows it

            }
        });
        return builder.create(); //Show the dialog
    }
}
/**
 * Biblio from DialogFragment
 * https://developer.android.com/guide/topics/ui/dialogs?hl=es-419
 */