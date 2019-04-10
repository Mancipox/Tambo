package com.tambo.Controller;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.tambo.LocalCommunication.DataCommunication;
import com.tambo.R;

public class DescribeActivity extends AppCompatActivity {
    private CalendarView calendarView;
    @NotEmpty(message = "Por favor ingresa una descripción")
    private EditText textDescription;
    private Context context;
    protected Validator validator;
    private static DataCommunication.DialogCallback dialogCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_describe);
    }


}
