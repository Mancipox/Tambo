package com.tambo.Controller;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tambo.Model.Class;
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
    private ArrayList<Class> questionsStudents;
    private Context mContext;
    CustomItemClickListener listener;
    private ArrayList<AdapterQuestionStudent.QuestionViewHolder> questionsViewHolder;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
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
     * @param questionsStudents
     * @param listener
     */
    public AdapterQuestionStudent(Context mContext, ArrayList<Class> questionsStudents, CustomItemClickListener listener){
        this.questionsStudents=questionsStudents;
        this.mContext= mContext;
        this.listener = listener;
        questionsViewHolder = new ArrayList<>();
    }

    public AdapterQuestionStudent(ArrayList<Class> questionsStudents){
        this.questionsStudents=questionsStudents;
    }

    public void setItem(Class aClass){
        this.questionsStudents.add(aClass);
        this.notifyDataSetChanged();
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
        questionsViewHolder.add(mViewHolder);
        return mViewHolder;
    }


    /**
     * Replace content of the recycler view based in dataset
     * @param questionViewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(AdapterQuestionStudent.QuestionViewHolder questionViewHolder, int i) {
        Class aClass = questionsStudents.get(i);
        if(questionsStudents.get(i).getTeacherEmail()!=null && !questionsStudents.get(i).isState()) questionViewHolder.imageView.setImageResource(R.drawable.questiona);
        else if(questionsStudents.get(i).getTeacherEmail()==null) questionViewHolder.imageView.setImageResource(R.drawable.questions);
        else questionViewHolder.imageView.setImageResource(R.drawable.correct2);
        TextView textView = questionViewHolder.textView;
        textView.setText(aClass.getDescription());

    }

    @Override
    public int getItemCount() {
        return questionsStudents.size();
    }
    public AdapterQuestionStudent.QuestionViewHolder getHolder(int i){
        Log.d("AdapterQuestion request","Index "+i+" Mod "+i%questionsViewHolder.size());
        return questionsViewHolder.get(i%questionsViewHolder.size());
    }

}

/**
 * Biblio from recycler view
 * https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter.html?hl=es-419#notifyItemChanged(int)
 * https://guides.codepath.com/android/using-the-recyclerview
 */