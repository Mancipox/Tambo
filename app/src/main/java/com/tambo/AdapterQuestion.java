package com.tambo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

/**
 * This class is used to represent a Recycler view and methods
 * @author mancipox
 */
public class AdapterQuestion extends RecyclerView.Adapter<AdapterQuestion.QuestionViewHolder> {
    /**
     * A list of questions, the dataset to load in recycler view
     */
    private List<Question> mQuestions;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class QuestionViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public QuestionViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.question_name);
        }
    }

    /**
     * Constructor based in dataset
     */
    public AdapterQuestion(List<Question> myDataset){
        mQuestions=myDataset;
    }


    /**
     * Create a view and initialice in new holder
     * @param parent
     * @param viewType
     * @return
     */
    public AdapterQuestion.QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        //Create a new view
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View questionView = inflater.inflate(R.layout.item_question,parent,false);

        QuestionViewHolder vh = new QuestionViewHolder(questionView);
        return vh;
    }


    /**
     * Replace content of the recycler view based in dataset
     * @param questionViewHolder
     * @param position
     */
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

/**
 * Biblio from recycler view
 * https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter.html?hl=es-419#notifyItemChanged(int)
 * https://guides.codepath.com/android/using-the-recyclerview
 */
