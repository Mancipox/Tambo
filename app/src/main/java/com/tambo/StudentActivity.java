package com.tambo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

//From https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter.html?hl=es-419#notifyItemChanged(int)
public class StudentActivity extends AppCompatActivity {
    ArrayList<Question> questions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        //Creating the recyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewStudent);

        //Createa a dataset
        questions=Question.createQuestionList(20);

        // specify an adapter
        AdapterQuestion adapter = new AdapterQuestion(questions);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
