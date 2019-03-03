package com.tambo.LocalCommunication;


import com.tambo.Controller.AdapterQuestion;
import com.tambo.Model.Question;
import com.tambo.Model.User;

import java.util.ArrayList;

public interface DataCommunication {
    String getQuestionText();
    void setQuestionText(String text);

    AdapterQuestion getAdapterQuestion();
    void setAdapterQuestion(AdapterQuestion adapterQuestion);

    User getUser();
    void setUser(User user);

    ArrayList<Question> getQuestionsStudent();
    void setQuestionsStudent(ArrayList<Question> questions);
    void addQuestionStudent(Question question);

}
