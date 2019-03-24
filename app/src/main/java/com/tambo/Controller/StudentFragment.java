package com.tambo.Controller;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


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

    private TextView textViewKarma;

    private User mainUser; //Main user after login

    private RequestQueue queue;




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

        textViewKarma = view.findViewById(R.id.textkarma);
        mainUser = mCallBack.getUser();


        FloatingActionButton buttonKarma = view.findViewById(R.id.fabkarma);
        buttonKarma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadCoinsByUser();
            }
        });


        reloadCoinsByUser();

        queue = Volley.newRequestQueue(getContext());

        //FloatingActionButton buttonReload = view.findViewById(R.id.fabstudent);

        //Petition added
        reloadQuestionsByUser();

        /*buttonReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadQuestionsByUser();
            }
        });*/

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

        buttonPostQuestion = view.findViewById(R.id.buttonPostQuestion);
        buttonPostQuestion.setOnClickListener(this);

        //Start reload in background
        //How to doenst change the actual position when refresh
        scheduleReload(5000);

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
                editTextQuestionTitle.setText("");
                reloadCoinsByUser();
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


    public void reloadQuestionsByUser(){
        StringRequest myReq = new StringRequest(Request.Method.GET, Connect_Server.url_server + "ServletQuestion?option=askedBy&user="+Utils.toJson(mainUser), new Response.Listener<String>() {
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

    public void reloadCoinsByUser(){
        textViewKarma.setText("$ "+mainUser.getKarma());
    }

    public void scheduleReload(final int periodCalled){
        final Handler handler = new Handler();
        Runnable runnabler = new Runnable() {
            @Override
            public void run() {
                reloadQuestionsByUser();
                reloadCoinsByUser();
                handler.postDelayed(this, periodCalled);
            }
        };
        handler.postDelayed(runnabler,periodCalled);


        getActivity().runOnUiThread(runnabler);

    }

}
