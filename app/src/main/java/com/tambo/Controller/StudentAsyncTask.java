package com.tambo.Controller;

import android.os.AsyncTask;

import com.tambo.Model.Question;

public class StudentAsyncTask extends AsyncTask<Question, Integer, Boolean> {

    @Override
    protected Boolean doInBackground(Question... questions) {
        return null;
    }
    protected void  onPostExecute(Boolean response){

    }
}