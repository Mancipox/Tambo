package com.tambo.LocalCommunication;


import com.tambo.Model.Class;
import com.tambo.Model.User;

/**
 * Interface to communicate between fragments
 */
public interface DataCommunication {
    String getQuestionText();
    void setQuestionText(String text);

    User getUser();
    void setUser(User user);

    Class getClassProfessor();
    void setClassProfessor(Class classProfessor);


    Class getClassStudent();
    void setQuestionStudet(Class classStudet);

    interface DialogCallback{
        void updateRecyclerView(Class aClass);
        void updateRecyclerView(boolean state);
    }

    String getToken();
    void setToken(String token);
}
