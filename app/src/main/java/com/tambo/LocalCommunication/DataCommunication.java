package com.tambo.LocalCommunication;

import android.widget.EditText;

public interface DataCommunication {
    String getQuestionText();
    void setQuestionText(String text);

    EditText getEditTextQuestion();
    void setEditTextQuestion(EditText editTextQuestionAsked);
}
