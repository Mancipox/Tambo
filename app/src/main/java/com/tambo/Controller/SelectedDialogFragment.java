package com.tambo.Controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.tambo.Connection.Connect_Server;
import com.tambo.LocalCommunication.DataCommunication;
import com.tambo.Model.Question;
import com.tambo.R;


/**
 * DialogFragment to select a question by professor @BD
 */
public class SelectedDialogFragment extends DialogFragment {

    private DataCommunication mCallBack; //To share information between fragments

    private Connect_Server connect_server;

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
        connect_server = new Connect_Server();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater(); //Create the container to dialog's template
        View layout = inflater.inflate(R.layout.dialog_select_template, null); //Get the layout of the dialog's template
        TextView textView = layout.findViewById(R.id.textSelectDialog);

        final Question questionSelected = mCallBack.getQuestionProfessor();
        textView.setText(textView.getText()+" "+questionSelected.toString());

        builder.setView(layout);

        builder.setPositiveButton("¡Lo acepto!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                questionSelected.setUserAnsw(mCallBack.getUser());
                questionSelected.setState(true);
                try {
                    connect_server.startConnection();
                    connect_server.setUserAnswerQuestion(questionSelected);
                    Snackbar.make(getActivity().findViewById(android.R.id.content),"Aceptado",Snackbar.LENGTH_LONG).show(); //Succefull message
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Snackbar.make(getActivity().findViewById(android.R.id.content),"Hubo un problema :(",Snackbar.LENGTH_LONG).show(); //Succefull message
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
}
