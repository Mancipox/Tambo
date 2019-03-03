package com.tambo.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tambo.Model.Question;
import com.tambo.R;

import java.util.ArrayList;

public class Profesor extends AppCompatActivity {
    ArrayList<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesor);
        RecyclerView mRecyclerView = findViewById(R.id.my_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        questions=Question.createQuestionList(20);
        AdapterQuestionProfesor mAdapter = new AdapterQuestionProfesor(questions,this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.setOnItemClickListener(new AdapterQuestionProfesor.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                System.out.println("gottem");
            }
        });
    }
}
