package com.tambo.Controller;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private ArrayList<AdapterQuestionProfessor.QuestionViewHolder> questionsViewHolder;


    public class QuestionViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public ConstraintLayout linearLayout;
        public ImageView imageView;
        public QuestionViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.question_name);
            linearLayout = itemView.findViewById(R.id.layout_question);
            imageView = itemView.findViewById(R.id.imageViewStatus);
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
        questionsViewHolder = new ArrayList<>();
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
        questionsViewHolder.add(mViewHolder);
        return mViewHolder;
    }

    /**
     * Replace content of the recycler view based in dataset, set the color of the layout if is accepted or not
     * @param questionViewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(@NonNull AdapterQuestionProfessor.QuestionViewHolder questionViewHolder, int i) {
        Question question = questionsProfessor.get(i);
        if(questionsProfessor.get(i).isState() && questionsProfessor.get(i).getCredit()!=0) questionViewHolder.imageView.setImageResource(R.drawable.question_accepted);
        else if(questionsProfessor.get(i).getUserAnsw()==null) questionViewHolder.imageView.setImageResource(R.drawable.question);
        else questionViewHolder.imageView.setImageResource(R.drawable.like);

        TextView textView = questionViewHolder.textView;
        textView.setText(question.getDescription());
    }

    @Override
    public int getItemCount() {
        return questionsProfessor.size();
    }

    public void setItem(Question question){
        this.questionsProfessor.add(question);
        this.notifyDataSetChanged();
    }

    public AdapterQuestionProfessor.QuestionViewHolder getHolder(int i){
        return questionsViewHolder.get(i);
    }
}
