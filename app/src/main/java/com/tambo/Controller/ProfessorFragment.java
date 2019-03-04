package com.tambo.Controller;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tambo.Connection.Connect_Server;
import com.tambo.LocalCommunication.DataCommunication;
import com.tambo.Model.Question;
import com.tambo.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass. Represents the professor activity @BD
 */
public class ProfessorFragment extends Fragment {
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
        connect_server = new Connect_Server();
        questions=new ArrayList<>();
        connect_server.startConnection();
        View view = inflater.inflate(R.layout.fragment_professor,container,false);
        //Creating the recyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewProfessor);

        //Get the DB dataset of questions from this user
        //questions=BD.getAllQuestionsFromId(this.user);
        try {
            questions = connect_server.getQuestionsProfessor(mCallBack.getUser());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Specify an adapter to recycler view
        adapter = new AdapterQuestionProfessor(getContext(), questions, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //Launch NoticeDialog Fragment to see description and
                mCallBack.setQuestionProfessor(questions.get(position));
                SelectedDialogFragment selectedDialogFragment = new SelectedDialogFragment();
                selectedDialogFragment.show(getFragmentManager(),"infoSelect");
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

}
