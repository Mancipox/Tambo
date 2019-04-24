package com.tambo.Controller;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tambo.Connection.Connect_Server;
import com.tambo.LocalCommunication.DataCommunication;
import com.tambo.Model.Question;
import com.tambo.Model.User;
import com.tambo.R;
import com.tambo.Utils.Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass. Represents the professor activity
 */
public class ProfessorFragment extends Fragment{
    private ArrayList<Question> questions;
    private AdapterQuestionProfessor adapter;
    private DataCommunication mCallBack;
    private RecyclerView recyclerView;

    private User mainUser;

    private RequestQueue queue;

    private Timer timer;
    private TimerTask timerTask;
    private String tag;
    private boolean initialDisp = true;

    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;
    public ProfessorFragment() {
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
        setHasOptionsMenu(true);
        questions=new ArrayList<>();

        View view = inflater.inflate(R.layout.fragment_professor,container,false);
        //FloatingActionButton buttonReload = view.findViewById(R.id.fab);
        mainUser = mCallBack.getUser();

        queue = Volley.newRequestQueue(getContext());

        reloadQuestionsByUser();

        /*buttonReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadQuestionsByUser();
            }
        });*/
        //Creating the recyclerView
        recyclerView = view.findViewById(R.id.recyclerViewProfessor);


        //Specify an adapter to recycler view
        adapter = new AdapterQuestionProfessor(getContext(), questions, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, final int position) {
                //Launch NoticeDialog Fragment to see description and
                mCallBack.setQuestionProfessor(questions.get(position));
                final SelectedDialogFragment dialogFragment = SelectedDialogFragment.newInstance(new DataCommunication.DialogCallback() {
                    @Override
                    public void updateRecyclerView(Question question) {
                        //Nothing to do, this case is used in StudentFragment
                    }

                    @Override
                    public void updateRecyclerView(boolean state) {
                        if(!state) {
                            AdapterQuestionProfessor.QuestionViewHolder questionViewHolder = adapter.getHolder(position);
                            questionViewHolder.imageView.setImageResource(R.drawable.questiona);
                        }
                    }
                });
                dialogFragment.show(getFragmentManager(),"infoSelect");
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //Start reload in background
        scheduleReload(60000);

        return view;
    }
    public void onViewCreated(View view, Bundle savedInstanceState){
        materialDesignFAM = (FloatingActionMenu) getView().findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = getView().findViewById(R.id.material_design_floating_action_menu_item1);
       /* floatingActionButton2 = getView().findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton3 = getView().findViewById(R.id.material_design_floating_action_menu_item3);*/
        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tag!=null)
                reloadQuestionsByTopic(tag);
            }
        });
       /* floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu second item clicked

            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu third item clicked

            }
        });*/
    }


    public void reloadQuestionsByUser(){
        //Problema con el servidor obteniendo las preguntas diferentes a las que el usuario ha hecho
        StringRequest myReq = new StringRequest(Request.Method.GET, Connect_Server.url_server + "ServletQuestion?option=except&user="+ Utils.toJson(mainUser)+"&authorization="+mCallBack.getToken(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Type QuestionsType = new TypeToken<ArrayList<Question>>(){}.getType();
                Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
                questions = gson.fromJson(response, QuestionsType);
                adapter = new AdapterQuestionProfessor(getContext(), questions, new CustomItemClickListener() {
                    @Override
                    public void onItemClick(View v, final int position) {
                        //Launch NoticeDialog Fragment to see description and
                        mCallBack.setQuestionProfessor(questions.get(position));
                        final SelectedDialogFragment dialogFragment = SelectedDialogFragment.newInstance(new DataCommunication.DialogCallback() {
                            @Override
                            public void updateRecyclerView(Question question) {
                                //Nothing to do, this case is used in StudentFragment
                            }

                            @Override
                            public void updateRecyclerView(boolean state) {
                                if(!state) {
                                    AdapterQuestionProfessor.QuestionViewHolder questionViewHolder = adapter.getHolder(position);
                                    questionViewHolder.imageView.setImageResource(R.drawable.questiona);
                                }
                            }
                        });
                        dialogFragment.show(getFragmentManager(),"infoSelect");
                    }
                });
                recyclerView.setAdapter(adapter);
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
    public void reloadQuestionsByTopic(String topic){
        //Problema con el servidor obteniendo las preguntas diferentes a las que el usuario ha hecho
        StringRequest myReq = new StringRequest(Request.Method.GET, Connect_Server.url_server + "ServletQuestion?option=byTopic&user="+topic+"&authorization="+mCallBack.getToken(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Type QuestionsType = new TypeToken<ArrayList<Question>>(){}.getType();
                Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
                questions = gson.fromJson(response, QuestionsType);
                adapter = new AdapterQuestionProfessor(getContext(), questions, new CustomItemClickListener() {
                    @Override
                    public void onItemClick(View v, final int position) {
                        //Launch NoticeDialog Fragment to see description and
                        mCallBack.setQuestionProfessor(questions.get(position));
                        final SelectedDialogFragment dialogFragment = SelectedDialogFragment.newInstance(new DataCommunication.DialogCallback() {
                            @Override
                            public void updateRecyclerView(Question question) {
                                //Nothing to do, this case is used in StudentFragment
                            }

                            @Override
                            public void updateRecyclerView(boolean state) {
                                if(!state) {
                                    AdapterQuestionProfessor.QuestionViewHolder questionViewHolder = adapter.getHolder(position);
                                    questionViewHolder.imageView.setImageResource(R.drawable.questiona);
                                }
                            }
                        });
                        dialogFragment.show(getFragmentManager(),"infoSelect");
                    }
                });
                recyclerView.setAdapter(adapter);
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

    public void scheduleReload(final int periodCalled){
        final Handler handler = new Handler();
        Runnable runnabler = new Runnable() {
            @Override
            public void run() {
                reloadQuestionsByUser();
                handler.postDelayed(this, periodCalled);
            }
        };
        handler.postDelayed(runnabler,periodCalled);

        getActivity().runOnUiThread(runnabler);

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_tag, menu);
        super.onCreateOptionsMenu(menu,inflater);
        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) item.getActionView();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.tags_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(initialDisp==true){
                    initialDisp = false;
                }else if (initialDisp==false){
                tag = String.valueOf(parent.getItemAtPosition(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Do nothing
            }

        });
       // return true;
    }

}
