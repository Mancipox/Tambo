package com.tambo.Controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tambo.Model.Question;
import com.tambo.R;

import java.util.ArrayList;

/**
 * This class is used to represent a Recycler view and methods in professor
 */
public class AdapterQuestionProfessor extends RecyclerView.Adapter<AdapterQuestionProfessor.QuestionViewHolder> {
    private ArrayList<Question> questionsProfessor;
    private Context mContext;
    CustomItemClickListener listener;


    public class QuestionViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public QuestionViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.question_name);
        }
    }

    /**
     * Constructor to listen a click in items
     * @param mContext
     * @param questionsProfessor
     * @param listener
     */
    public AdapterQuestionProfessor(Context mContext, ArrayList<Question> questionsProfessor,CustomItemClickListener listener){
        this.questionsProfessor=questionsProfessor;
        this.mContext= mContext;
        this.listener = listener;
    }


    /**
     * Create a view and initialice in new holder
     * @param parent
     * @param i
     * @return
     */
    @NonNull
    @Override
    public AdapterQuestionProfessor.QuestionViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        //Create a new view
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View questionView = inflater.inflate(R.layout.item_question,parent,false);

        final QuestionViewHolder mViewHolder = new QuestionViewHolder(questionView);
        questionView.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, mViewHolder.getPosition());
            }
        });
        return mViewHolder;
    }

    /**
     * Replace content of the recycler view based in dataset
     * @param questionViewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(@NonNull AdapterQuestionProfessor.QuestionViewHolder questionViewHolder, int i) {
        Question question = questionsProfessor.get(i);
        TextView textView = questionViewHolder.textView;
        textView.setText(question.toString());
    }

    @Override
    public int getItemCount() {
        return questionsProfessor.size();
    }
}
