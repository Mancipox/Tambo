package com.tambo.Controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import com.tambo.Utils.Utils;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.tambo.Connection.Connect_Server;
import com.tambo.LocalCommunication.DataCommunication;
import com.tambo.Model.Question;
import com.tambo.Model.User;
import com.tambo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
//Squirrel2*+
/**
 * DialogFragment set as complete a question by student @BD
 */
public class CompletedDialogFragment extends DialogFragment {
    private DataCommunication mCallBack; //To communicate between fragments
    private Context context;

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
        context = getContext();
        builder.setView(layout);
        final Question questemp = mCallBack.getQuestionStudent();

        builder.setPositiveButton("¡Está completa!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //PUT
                if (questemp.getUserAnsw() != null && questemp.getCredit()!=0) {
                    if(questemp.isState()){ //Check if is completed {
                RequestQueue queue = Volley.newRequestQueue(context);
                questemp.setUserAnsw(mCallBack.getUser());
                StringRequest myReq = new StringRequest(Request.Method.POST, CustomItemClickListener.url_server + "ServletQuestion", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       Boolean r = (Boolean) Utils.fromJson(response,Boolean.class);
                       if(r) {
                           Toast.makeText(context, "Completado", Toast.LENGTH_SHORT).show();
                           //Snackbar.make(getActivity().findViewById(android.R.id.content), "Completado", Snackbar.LENGTH_LONG).show();
                       }
                       else{
                           Toast.makeText(context, "Error en el servidor", Toast.LENGTH_SHORT).show();
                           //Snackbar.make(getActivity().findViewById(android.R.id.content), "", Snackbar.LENGTH_LONG).show();
                       }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> MyData = new HashMap<String, String>();
                        MyData.put("option", "student");
                        MyData.put("Question", Utils.toJson(questemp));
                        return MyData;
                    }
                };
                queue.add(myReq);

                    }else {
                        Toast.makeText(context, "Ya diste por completada la pregunta", Toast.LENGTH_SHORT).show();
                        //Snackbar.make(getActivity().findViewById(android.R.id.content), "Ya diste por completada la pregunta", Snackbar.LENGTH_LONG).show(); //Succefull message
                    }
                }else{
                    Toast.makeText(context, "La pregunta aún no ha sido aceptada", Toast.LENGTH_SHORT).show();
                    //Snackbar.make(getActivity().findViewById(android.R.id.content), "La pregunta aún no ha sido aceptada", Snackbar.LENGTH_LONG).show(); //Succefull message
                }

                /*
                if (questemp.getUserAnsw() != null) {
                    if(!questemp.isState()){ //Check if is completed
                        questemp.setState(true);
                        try {
                            connect_server.startConnection();
                            connect_server.setAnsweredQuestion(questemp);
                          //Succefull message
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
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "localhost:8080/ServletQuestion";

            //the request return a single JSON object
            String jsonStr = sh.QuestionInfo(url,question_info);

            Question returnedQues = gson.fromJson(jsonStr,Question.class);
            //The request returns a JSON array
         /*   JSONArray jsonStr = sh.QuestionInfo(url,question_info);
            ArrayList<Question>questionArrayList=null;
            for (int i=0;i<jsonStr.length();i++){
                try {
                    JSONObject qobj =jsonStr.getJSONObject(i);
                    Question question =gson.fromJson(qobj.toString(),Question.class);
                    questionArrayList.add(question);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }*/

            return returnedQues;
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


