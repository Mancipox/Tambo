package com.tambo.Controller;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.tambo.Connection.Connect_Server;
import com.tambo.LocalCommunication.DataCommunication;
import com.tambo.Model.Question;
import com.tambo.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass. Represents the professor activity @BD
 */
public class ProfessorFragment extends Fragment implements View.OnClickListener{
    private ArrayList<Question> questions;
    private AdapterQuestionProfessor adapter;
    private DataCommunication mCallBack;

    private Connect_Server connect_server;

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
        questions=new ArrayList<>();
        //connect_server.startConnection();
        View view = inflater.inflate(R.layout.fragment_professor,container,false);
        FloatingActionButton buttonReload = view.findViewById(R.id.fab);
        buttonReload.setOnClickListener(this);
        //Creating the recyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewProfessor);
        /*try {
            questions = connect_server.getQuestionsProfessor(mCallBack.getUser());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        //Data for test purpose
        questions.add(new Question("2",null, false, "Pregunta de prueba 1", 5));
        questions.add(new Question("3",null, false, "Pregunta de prueba 2", 5));
        questions.add(new Question("4",null, false, "Pregunta de prueba 3", 5));
        questions.add(new Question("5",null, false, "Pregunta de prueba 4", 5));

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
                            questionViewHolder.linearLayout.setBackgroundColor(Color.parseColor("#f4f2bf"));
                        }
                    }
                });
                dialogFragment.show(getFragmentManager(),"infoSelect");
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    /**
     * Reload the questions in recyclerview
     * @param v
     */
    public void onClick(View v) {
        /*try {
            questions = connect_server.getQuestionsProfessor(mCallBack.getUser());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        //Data for test purpose
        questions.add(new Question("6",null, false, "Pregunta de prueba 5 Button", 5));
        adapter.notifyDataSetChanged();
    }
}
