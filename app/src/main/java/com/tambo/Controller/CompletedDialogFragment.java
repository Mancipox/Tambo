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

import com.tambo.LocalCommunication.DataCommunication;
import com.tambo.R;

/**
 * DialogFragment set as complete a question by student @BD
 */
public class CompletedDialogFragment extends DialogFragment {
    private DataCommunication mCallBack; //To communicate between fragments

    /**
     * Obligatory attach the fragment to main activity to pass information with {@link DataCommunication}
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
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater(); //Create the container to dialog's template
        View layout = inflater.inflate(R.layout.dialog_complete_template, null); //Get the layout of the dialog's template

        builder.setView(layout);

        builder.setPositiveButton("¡Está completa!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Question questemp = mCallBack.getQuestionsStudent().get(mCallBack.getActualPosition());
                //questemp.setState(true); //Assign the karma to user
                //Update database with question and state !!
                Snackbar.make(getActivity().findViewById(android.R.id.content),"Completado",Snackbar.LENGTH_LONG).show(); //Succefull message
            }
        });
        builder.setNegativeButton("No, aún no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Snackbar.make(getActivity().findViewById(android.R.id.content),"Cancelado",Snackbar.LENGTH_LONG).show(); //Succefull message
            }
        });

        return builder.create();
    }
}
