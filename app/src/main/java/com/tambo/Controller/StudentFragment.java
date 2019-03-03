package com.tambo.Controller;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tambo.Model.Question;
import com.tambo.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentFragment extends Fragment implements View.OnClickListener {


    private ArrayList<Question> questions;

    private AdapterQuestion adapter;

    private EditText editTextQuestionTitle;

    private Button buttonPostQuestion;



    public StudentFragment() {
        // Required empty public constructor
    }


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

        //Specify an adapter to recycler view
        adapter = new AdapterQuestion(questions);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        buttonPostQuestion = (Button) view.findViewById(R.id.buttonPostQuestion);
        buttonPostQuestion.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        NoticeDialogFragment dialogFragment = new NoticeDialogFragment(); //Create a dialog fragment
        //EditText editText = getView().findViewById(R.id.editTextQuestion); //Get the text question
        //Bundle bundle = new Bundle(); //To pass data from activity to dialog
        //bundle.putString("textQuestionDialog",editText.getText().toString()); //Adding the data to pass to bundle
        //dialogFragment.setArguments(bundle); //Passing the data
        dialogFragment.show(getFragmentManager(), "infoQuestion"); //Showing the dialog
    }
}
