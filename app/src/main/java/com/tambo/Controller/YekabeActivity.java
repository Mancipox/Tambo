package com.tambo.Controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.tambo.LocalCommunication.DataCommunication;
import com.tambo.Model.Question;
import com.tambo.Model.User;
import com.tambo.R;

import java.util.ArrayList;

public class YekabeActivity extends AppCompatActivity implements NoticeDialogFragment.NoticeDialogListener, DataCommunication {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private TabItem tabItemStudent;
    private TabItem tabItemProfessor;
    private ViewPager viewPager;

    private String questionText;
    private EditText questionEditText;
    private boolean sucefullPost;

    private AdapterQuestion adapterQuestion;

    private User user;

    private ArrayList<Question> questions;

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

        if(getUser()==null){
            User usertemp = new User();
            usertemp.setEmail("Pablo@mail.com");
            usertemp.setKarma(20);
            usertemp.setUsername("Pablox");
            setUser(usertemp);
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

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
    public AdapterQuestion getAdapterQuestion() {
        return adapterQuestion;
    }

    @Override
    public void setAdapterQuestion(AdapterQuestion adapterQuestion) {
        this.adapterQuestion=new AdapterQuestion(questions);
        this.adapterQuestion = adapterQuestion;
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




}
