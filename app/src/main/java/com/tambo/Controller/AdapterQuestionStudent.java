package com.tambo.Controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tambo.Model.Question;
import com.tambo.R;

import java.util.ArrayList;

/**
 * This class is used to represent a Recycler view and methods in student
 * @author mancipox
 */
public class AdapterQuestionStudent extends RecyclerView.Adapter<AdapterQuestionStudent.QuestionViewHolder> {
    /**
     * A list of questions, the dataset to load in recycler view
     */
    private ArrayList<Question> questionsStudents;
    private Context mContext;
    CustomItemClickListener listener;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
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
     * @param questionsStudents
     * @param listener
     */
    public AdapterQuestionStudent(Context mContext, ArrayList<Question> questionsStudents,CustomItemClickListener listener){
        this.questionsStudents=questionsStudents;
        this.mContext= mContext;
        this.listener = listener;
    }



    /**
     * Create a view and initialice in new holder
     * @param parent
     * @param viewType
     * @return
     */
    public AdapterQuestionStudent.QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        //Create a new view
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View questionView = inflater.inflate(R.layout.item_question,parent,false);

        final AdapterQuestionStudent.QuestionViewHolder mViewHolder = new QuestionViewHolder(questionView);
        //Listen a click in an item
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
     * @param position
     */
    @Override
    public void onBindViewHolder(AdapterQuestionStudent.QuestionViewHolder questionViewHolder, int position) {
        Question question = questionsStudents.get(position);
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        TextView textView = questionViewHolder.textView;
        textView.setText(question.toString());
    }

    @Override
    public int getItemCount() {
        return questionsStudents.size();
    }

}

/**
 * Biblio from recycler view
 * https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter.html?hl=es-419#notifyItemChanged(int)
 * https://guides.codepath.com/android/using-the-recyclerview
 */