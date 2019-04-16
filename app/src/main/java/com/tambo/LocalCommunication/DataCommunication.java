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

    User getUser();
    void setUser(User user);

    Question getQuestionProfessor();
    void setQuestionProfessor(Question questionProfessor);


    Question getQuestionStudent();
    void setQuestionStudet(Question questionStudet);

    interface DialogCallback{
        void updateRecyclerView(Question question);
        void updateRecyclerView(boolean state);
    }

    String getToken();
    void setToken(String token);
}
