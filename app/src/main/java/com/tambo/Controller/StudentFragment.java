package com.tambo.Controller;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tambo.LocalCommunication.DataCommunication;
import com.tambo.Model.Question;
import com.tambo.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentFragment extends Fragment implements View.OnClickListener, NoticeDialogFragment.NoticeDialogListener {


    private ArrayList<Question> questions;

    private AdapterQuestion adapter;

    private EditText editTextQuestionTitle;

    private Button buttonPostQuestion;


    private DataCommunication mCallBack;



    public StudentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mCallBack = (DataCommunication) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString()+" must implement DataCommunication");
        }
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
        EditText editText = getView().findViewById(R.id.editTextQuestion); //Get the text question
        mCallBack.setQuestionText(editText.getText().toString());
        dialogFragment.show(getFragmentManager(), "infoQuestion"); //Showing the dialog
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button
        Snackbar.make(getActivity().findViewById(R.id.content),"Pregunta enviada",Snackbar.LENGTH_LONG).show();
        //Add the questions from DB to array list of questions !!
        //question = BD.getAllQuestionsFromId(this.user);
        adapter.notifyDataSetChanged(); //Reload the recycler view !!
        editTextQuestionTitle.setText("");//Set blank the text field from question
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
