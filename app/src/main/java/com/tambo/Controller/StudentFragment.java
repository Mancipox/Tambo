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
        View view = inflater.inflate(R.layout.fragment_student,container,false);

        editTextQuestionTitle = view.findViewById(R.id.editTextQuestion);

        //Creating the recyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewStudent);

        //Get the DB dataset of questions from this user
        //questions=BD.getAllQuestionsFromId(this.user);
        questions=Question.createQuestionList(20);
        mCallBack.setQuestionsStudent(questions);

        //Specify an adapter to recycler view
        adapter = new AdapterQuestionStudent(getContext(), questions, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                mCallBack.setActualPosition(position);
                CompletedDialogFragment dialogFragment = new CompletedDialogFragment();
                dialogFragment.show(getFragmentManager(),"infoComplete");
            }
        });
        mCallBack.setAdapterQuestionStudent(adapter);
        recyclerView.setAdapter(adapter);
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
        DescribeDialogFragment dialogFragment = new DescribeDialogFragment(); //Create a dialog fragment
        EditText editText = getView().findViewById(R.id.editTextQuestion); //Get the text question
        mCallBack.setQuestionText(editText.getText().toString());
        dialogFragment.show(getFragmentManager(), "infoQuestion"); //Showing the dialog
    }

}
