package com.tambo.LocalCommunication;


import com.tambo.Controller.AdapterQuestionStudent;
import com.tambo.Model.Question;
import com.tambo.Model.User;

import java.util.ArrayList;

/**
 * Interface to communicate between fragments
 */
public interface DataCommunication {
    String getQuestionText();
    void setQuestionText(String text);

    AdapterQuestionStudent getAdapterQuestionStudent();
    void setAdapterQuestionStudent(AdapterQuestionStudent adapterQuestionStudent);

    User getUser();
    void setUser(User user);

    ArrayList<Question> getQuestionsStudent();
    void setQuestionsStudent(ArrayList<Question> questions);
    void addQuestionStudent(Question question);


    void setActualPosition(int position);
    int getActualPosition();
}
