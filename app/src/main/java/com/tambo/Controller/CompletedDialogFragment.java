package com.tambo.Controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tambo.Connection.Connect_Server;
import com.tambo.LocalCommunication.DataCommunication;
import com.tambo.Model.Question;
import com.tambo.R;

/**
 * DialogFragment set as complete a question by student @BD
 */
public class CompletedDialogFragment extends DialogFragment {
    private DataCommunication mCallBack; //To communicate between fragments

    private Connect_Server connect_server;

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
        connect_server = new Connect_Server();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater(); //Create the container to dialog's template
        View layout = inflater.inflate(R.layout.dialog_complete_template, null); //Get the layout of the dialog's template

        builder.setView(layout);
        final Question questemp = mCallBack.getQuestionStudent();

        builder.setPositiveButton("¡Está completa!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                CompletedDialogFragmentAsyncTask task = new CompletedDialogFragmentAsyncTask();
                task.execute(questemp);
                /*
                if (questemp.getUserAnsw() != null) {
                    if(!questemp.isState()){ //Check if is completed
                        questemp.setState(true);
                        try {
                            connect_server.startConnection();
                            connect_server.setAnsweredQuestion(questemp);
                            Snackbar.make(getActivity().findViewById(android.R.id.content), "Completado", Snackbar.LENGTH_LONG).show(); //Succefull message
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Snackbar.make(getActivity().findViewById(android.R.id.content), "Hubo un problema :(", Snackbar.LENGTH_LONG).show(); //Succefull message
                        }
                    }else Snackbar.make(getActivity().findViewById(android.R.id.content), "Ya diste por completada la pregunta", Snackbar.LENGTH_LONG).show(); //Succefull message
                }else{
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "La pregunta aún no ha sido aceptada", Snackbar.LENGTH_LONG).show(); //Succefull message
                }*/
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
    private class CompletedDialogFragmentAsyncTask extends AsyncTask <Question, Integer, Question>{

        @Override
        protected Question doInBackground(Question... questions) {
            Gson gson = new Gson();
            String question_info = gson.toJson(questions[0]);
            return null;
        }
        protected void onPostExecute (Question response){
            //Se verifica que la petición halla logrado obtener la pregunta


            if (response.getUserAnsw()!=null){
                //Si no ha sido respondida
                if (!response.isState()){
                    //NO SE SI ESTO ACTUALICE DE UNA EN LA BD
                    response.setState(true);

                    int duration = Snackbar.LENGTH_SHORT;
                    CharSequence text = "Completado";
                    View v= getActivity().findViewById(android.R.id.content);
                    Snackbar.make(v,text,duration).show();
                }
                else{
                    int duration = Snackbar.LENGTH_SHORT;
                    CharSequence text = "Ya diste por completada la pregunta";
                    View v= getActivity().findViewById(android.R.id.content);
                    Snackbar.make(v,text,duration).show();

                }

            }
            else{
                int duration = Snackbar.LENGTH_SHORT;
                CharSequence text = "La pregunta no ha sido aceptada";
                View v= getActivity().findViewById(android.R.id.content);
                Snackbar.make(v,text,duration).show();
            }



        }

    }
}


