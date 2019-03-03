package com.tambo.Controller;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tambo.Model.Question;
import com.tambo.R;

import java.util.List;

public class AdapterQuestionProfesor extends RecyclerView.Adapter<AdapterQuestionProfesor.QuestionViewHolder> {

    private OnItemClickListener mListener;
    public Activity act;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class QuestionViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;

        public QuestionViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.question_name);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View textView) {
                    if (listener!= null){
                        int position = getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    private List<Question> mQuestions;
    //Create a constructor based in dataset
    public AdapterQuestionProfesor(List<Question> myDataset, Activity act){
        this.act=act;
        mQuestions=myDataset;
    }


    //Create a new view when starts a ViewHolder
    public AdapterQuestionProfesor.QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        //Create a new view
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View questionView = inflater.inflate(R.layout.item_question,parent,false);

        QuestionViewHolder vh = new QuestionViewHolder(questionView,mListener);
        return vh;
    }


    //Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(AdapterQuestionProfesor.QuestionViewHolder questionViewHolder, int position) {
        Question question = mQuestions.get(position);
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        TextView textView = questionViewHolder.textView;
        textView.setText(question.toString());
        questionViewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return mQuestions.size();
    }



}