package com.tambo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AdapterQuestion extends RecyclerView.Adapter<AdapterQuestion.QuestionViewHolder> {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class QuestionViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public QuestionViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.question_name);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View textView) {
                    // item clicked
                }
            });
        }
    }

    private List<Question> mQuestions;
    //Create a constructor based in dataset
    public AdapterQuestion(List<Question> myDataset){
        mQuestions=myDataset;
    }


    //Create a new view when starts a ViewHolder
    public AdapterQuestion.QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        //Create a new view
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View questionView = inflater.inflate(R.layout.item_question,parent,false);

        QuestionViewHolder vh = new QuestionViewHolder(questionView);
        return vh;
    }


    //Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(AdapterQuestion.QuestionViewHolder questionViewHolder, int position) {
        Question question = mQuestions.get(position);
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        TextView textView = questionViewHolder.textView;
        textView.setText(question.toString());
    }

    @Override
    public int getItemCount() {
        return mQuestions.size();
    }



}
