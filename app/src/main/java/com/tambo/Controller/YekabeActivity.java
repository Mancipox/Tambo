package com.tambo.Controller;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

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

    private ArrayList<Question> questions;

    private int position;

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
            usertemp.setEmail("Pablo@mail.com");
            usertemp.setKarma(20);
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
        //Error implementing this.adapterQuestionStudent = new AdapterQuestionStudent(questions); Doesn't load recyclerview
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
        return questions;
    }

    @Override
    public void setQuestionsStudent(ArrayList<Question> questions) {
        this.questions= new ArrayList<>(questions);
    }

    @Override
    public void addQuestionStudent(Question question) {
        this.questions.add(question);
    }

    @Override
    public void setActualPosition(int position) {
        this.position=position;
    }

    @Override
    public int getActualPosition() {
        return position;
    }


}
