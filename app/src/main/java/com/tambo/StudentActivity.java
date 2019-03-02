package com.tambo;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.support.design.widget.Snackbar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * This class is the manager of the student main view
 * @author mancipox
 */
public class StudentActivity extends AppCompatActivity implements NoticeDialogFragment.NoticeDialogListener{
    /**
     * An array from questions to load
     */
    private ArrayList<Question> questions;
    /**
     * An adapter to manage recycler view
     */
    private AdapterQuestion adapter;
    /**
     *
     */
     private EditText editTextQuestionTitle;


    /**
     * Create the view and initialice basic components
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_student);

        editTextQuestionTitle = findViewById(R.id.editTextQuestion);

        //Creating the recyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewStudent);

        //Get the DB dataset of questions from this user
        //questions=BD.getAllQuestionsFromId(this.user);
        questions=Question.createQuestionList(20);

        //Specify an adapter to recycler view
        adapter = new AdapterQuestion(questions);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Method from interface {@link com.tambo.NoticeDialogFragment.NoticeDialogListener} that listen the positive click from dialog fragment
     * Execute the snakcbar and reload the recycler view of questions
     * @param dialog
     */
    public void onDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button
        Snackbar.make(findViewById(android.R.id.content),"Pregunta enviada",Snackbar.LENGTH_LONG).show();
        //Add the questions from DB to array list of questions !!
        //question = BD.getAllQuestionsFromId(this.user);
        adapter.notifyDataSetChanged(); //Reload the recycler view !!
        editTextQuestionTitle.setText("");//Set blank the text field from question
    }


    /**
     * Method from interface {@link com.tambo.NoticeDialogFragment.NoticeDialogListener} that listen the negative click from dialog frament
     * Execute the snackbar
     * @param dialog
     */
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button
        Snackbar.make(findViewById(android.R.id.content),"Pregunta cancelada",Snackbar.LENGTH_LONG).show();
    }

    /**
     * Post the question, creates a dialog view to select the date and write a description
     * @param view
     */
    public void onClickPost(View view){
        NoticeDialogFragment dialogFragment = new NoticeDialogFragment(); //Create a dialog fragment
        EditText editText = findViewById(R.id.editTextQuestion); //Get the text question
        Bundle bundle = new Bundle(); //To pass data from activity to dialog
        bundle.putString("textQuestionDialog",editText.getText().toString()); //Adding the data to pass to bundle
        dialogFragment.setArguments(bundle); //Passing the data
        dialogFragment.show(getSupportFragmentManager(), "infoQuestion"); //Showing the dialog
    }

}
/**
 * Biblio from recycler view
 * https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter.html?hl=es-419#notifyItemChanged(int)
 * Biblio from DialogFragment
 * https://developer.android.com/guide/topics/ui/dialogs?hl=es-419
 */