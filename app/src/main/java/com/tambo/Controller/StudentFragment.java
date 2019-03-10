package com.tambo.Controller;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tambo.Connection.Connect_Server;
import com.tambo.LocalCommunication.DataCommunication;
import com.tambo.Model.Question;
import com.tambo.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass. Represents the student activity @BD
 */
public class StudentFragment extends Fragment implements View.OnClickListener{


    private ArrayList<Question> questions;

    private AdapterQuestionStudent adapter;

    private EditText editTextQuestionTitle;

    private Button buttonPostQuestion;


    private DataCommunication mCallBack; //To pass information between fragments
    private Connect_Server connect_server;



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
        //connect_server = new Connect_Server();
        questions = new ArrayList<Question>();
        View view = inflater.inflate(R.layout.fragment_student,container,false);

        editTextQuestionTitle = view.findViewById(R.id.editTextQuestion);

        //Creating the recyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewStudent);


        /*try {
            connect_server.startConnection();
            questions= Connect_Server.getQuestionsStudent(mCallBack.getUser());
            if(questions==null){Toast.makeText(getContext(), "Vacío", Toast.LENGTH_SHORT).show();}
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        //Data for test purpouse
        questions.add(new Question("4",null, false, "Pregunta de prueba 5", 5));
        if(questions==null) questions= new ArrayList<Question>();

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
