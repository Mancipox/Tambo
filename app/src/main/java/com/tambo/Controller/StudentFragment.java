package com.tambo.Controller;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tambo.Connection.Connect_Server;
import com.tambo.LocalCommunication.DataCommunication;
import com.tambo.Model.Question;
import com.tambo.Model.User;
import com.tambo.Utils.Utils;
import com.tambo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * A simple {@link Fragment} subclass. Represents the student activity @BD
 */
public class StudentFragment extends Fragment implements View.OnClickListener{


    private ArrayList<Question> questions;

    private AdapterQuestionStudent adapter;

    private EditText editTextQuestionTitle;

    private Button buttonPostQuestion;

    private RecyclerView recyclerView;

    private DataCommunication mCallBack; //To pass information between fragments



    public StudentFragment() {
        // Required empty public constructor
    }

    /**
     * Obligatory attach the fragment to main activity to pass information with {@link DataCommunication}
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mCallBack = (DataCommunication) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString()+" must implement DataCommunication");
        }
    }

    /**
     * State onCreateView. Logic of the view here
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        questions = new ArrayList<Question>();
        View view = inflater.inflate(R.layout.fragment_student,container,false);

        editTextQuestionTitle = view.findViewById(R.id.editTextQuestion);

        //Creating the recyclerView
        recyclerView = view.findViewById(R.id.recyclerViewStudent);



        final User user_temp= mCallBack.getUser();

        FloatingActionButton buttonReload = view.findViewById(R.id.fabstudent);
        buttonReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(getContext());
                StringRequest myReq = new StringRequest(Request.Method.GET, CustomItemClickListener.url_server + "ServletQuestion?option=askedBy&user="+Utils.toJson(user_temp), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Type QuestionsType = new TypeToken<ArrayList<Question>>(){}.getType();
                        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
                        questions = gson.fromJson(response, QuestionsType);
                        adapter = new AdapterQuestionStudent(getContext(), questions, new CustomItemClickListener() {
                            @Override
                            public void onItemClick(View v, int position) {
                                mCallBack.setQuestionStudet(questions.get(position));
                                CompletedDialogFragment dialogFragment = new CompletedDialogFragment();
                                dialogFragment.show(getFragmentManager(),"infoComplete");
                            }
                        });
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getContext(), "Ha ocurrido un error conectando al servidor", Toast.LENGTH_SHORT).show();
                            }
                        });
                queue.add(myReq);
            }
        });
        /*RequestFuture<String> future = RequestFuture.newFuture();
        StringRequest request = new StringRequest(Request.Method.GET, CustomItemClickListener.url_server + "ServletQuestion?option=askedBy&user="+Utils.toJson(user_temp), );
        queue.add(request);
        future.setRequest(request);
        String response="";

        try {
            response = future.get(5,TimeUnit.SECONDS); // this will block
            Type QuestionsType = new TypeToken<ArrayList<Question>>(){}.getType();
            Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
            questions = gson.fromJson(response,QuestionsType);
        } catch (InterruptedException e) {
            // exception handling
        } catch (ExecutionException e) {
            // exception handling
        } catch (TimeoutException e) {
            e.printStackTrace();
        }*/
        //GET


        /*try {
            connect_server.startConnection();
            questions= Connect_Server.getQuestionsStudent(mCallBack.getUser());
            if(questions==null){Toast.makeText(getContext(), "Vacío", Toast.LENGTH_SHORT).show();}
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        //Data for test purpouse
        /*try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        //Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
        //if(questions.isEmpty()){ questions= new ArrayList<Question>(); Toast.makeText(getContext(), "Ha ocurrido un error cargando las preguntas", Toast.LENGTH_SHORT).show();};

        //Specify an adapter to recycler view
        adapter = new AdapterQuestionStudent(getContext(), questions, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                mCallBack.setQuestionStudet(questions.get(position));
                CompletedDialogFragment dialogFragment = new CompletedDialogFragment();
                dialogFragment.show(getFragmentManager(),"infoComplete");
            }
        });
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        buttonPostQuestion = (Button) view.findViewById(R.id.buttonPostQuestion);
        buttonPostQuestion.setOnClickListener(this);
        return view;
    }

    /**
     *  Button "Post" question, shows the dialog notice for description and date
     * @param v
     */
    @Override
    public void onClick(View v) {
        DescribeDialogFragment dialogFragment = DescribeDialogFragment.newInstance(new DataCommunication.DialogCallback() {
            @Override
            public void updateRecyclerView(Question question) {
                adapter.setItem(question);
            }

            @Override
            public void updateRecyclerView(boolean state) {
                //Nothing to do
            }
        }); //Create a dialog fragment
        EditText editText = getView().findViewById(R.id.editTextQuestion); //Get the text question
        mCallBack.setQuestionText(editText.getText().toString());
        dialogFragment.show(getFragmentManager(), "infoQuestion"); //Showing the dialog
    }



}
