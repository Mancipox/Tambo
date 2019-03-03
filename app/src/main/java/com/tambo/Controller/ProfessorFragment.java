package com.tambo.Controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.tambo.Model.Question;
import com.tambo.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfessorFragment extends Fragment {
    private ArrayList<Question> questions;
    private AdapterQuestion adapter;

    public ProfessorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_professor,container,false);
        //Creating the recyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewProfessor);

        //Get the DB dataset of questions from this user
        //questions=BD.getAllQuestionsFromId(this.user);
        questions= Question.createQuestionList(1);


        //Specify an adapter to recycler view
        adapter = new AdapterQuestion(questions);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

}
