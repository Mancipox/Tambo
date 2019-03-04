package com.tambo.Controller;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.tambo.Connection.Connect_Server;
import com.tambo.LocalCommunication.DataCommunication;
import com.tambo.Model.Question;
import com.tambo.Model.User;
import com.tambo.R;

import java.util.ArrayList;


/**
 * Main activity of student-professor. Implements methods of {@link DataCommunication}
 */
public class YekabeActivity extends AppCompatActivity implements DataCommunication {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private TabItem tabItemStudent;
    private TabItem tabItemProfessor;
    private ViewPager viewPager;

    private String questionText;
    private EditText questionEditText;
    private boolean sucefullPost;

    private AdapterQuestionStudent adapterQuestionStudent;

    private User user;

    private ArrayList<Question> questionsStudents;
    private ArrayList<Question> questionsProfessor;

    private int position;

    private Question questionProfessor;
    private Question questionStudent;

    public Connect_Server connect_server;

    /**
     * Set the view @BD
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_yekabe);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        tabLayout = findViewById(R.id.tablayout);
        tabItemStudent = findViewById(R.id.tabStudent);
        tabItemProfessor = findViewById(R.id.tabProfessor);

        ViewPager viewPager = findViewById(R.id.viewPager);
        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        if(getUser()==null){ //Get a random user
            User usertemp = new User();
            usertemp.setEmail("naquerf@gmail.com");
            usertemp.setCredits(2);
            usertemp.setUsername("Pablox");
            setUser(usertemp); //Save to share it
        }
    }



    @Override
    public String getQuestionText() {
        return questionText;
    }

    @Override
    public void setQuestionText(String text) {
        questionText=text;
    }

    @Override
    public AdapterQuestionStudent getAdapterQuestionStudent() {
        return adapterQuestionStudent;
    }

    @Override
    public void setAdapterQuestionStudent(AdapterQuestionStudent adapterQuestionStudent) {
        this.adapterQuestionStudent = new AdapterQuestionStudent(questionsStudents);
        this.adapterQuestionStudent = adapterQuestionStudent;

    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user=user;
    }

    @Override
    public ArrayList<Question> getQuestionsStudent() {
        return questionsStudents;
    }

    @Override
    public void setQuestionsStudent(ArrayList<Question> questions) {
        this.questionsStudents= new ArrayList<>(questions);
    }

    @Override
    public void addQuestionStudent(Question question) {
        this.questionsStudents.add(question);
    }

    @Override
    public ArrayList<Question> getQuestionsProfessor() {
        return questionsProfessor;
    }

    @Override
    public void setQuestionsProfessor(ArrayList<Question> questions) {
        this.questionsProfessor = new ArrayList<>(questions);
    }

    @Override
    public void addQuestionProfessor(Question question) {
        this.questionsProfessor.add(question);
    }

    @Override
    public Question getQuestionProfessor() {
        return questionProfessor;
    }

    @Override
    public void setQuestionProfessor(Question questionProfessor) {
        this.questionProfessor=questionProfessor;
    }

    @Override
    public Question getQuestionStudent() {
        return questionStudent;
    }

    @Override
    public void setQuestionStudet(Question questionStudent) {
        this.questionStudent=questionStudent;
    }


    public interface OnFragmentInteractionListener{
        void updateRecyclerView();
    }


}
