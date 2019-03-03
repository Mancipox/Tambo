package com.tambo.Model;

import java.util.ArrayList;

public class Question {

    private static int id;
    //private User userDo;
    //private User userAnsw;
    private boolean state;
    private String decription;
    private int karma;
    //private Meeting meet;

    /*public Question(int id, User userDo, User userAnsw, boolean state, String decription, int karma, Meeting meet) {
        this.id = id;
        this.userDo = userDo;
        this.userAnsw = userAnsw;
        this.state = state;
        this.decription = decription;
        this.karma = karma;
        this.meet = meet;
    }*/

    public Question(int id, boolean state, String description, int karma){
        this.state=state;
        Question.id =id;
        this.decription=description;
        this.karma=karma;
    }

    public void setId(int id){
        Question.id =id;
    }

    public int getId(){
        return id;
    }

    /*public User getUserDo() {
        return userDo;
    }*/

    /*public void setUserDo(User userDo) {
        this.userDo = userDo;
    }*/

    /*public User getUserAnsw() {
        return userAnsw;
    }*/

    /*public void setUserAnsw(User userAnsw) {
        this.userAnsw = userAnsw;
    }*/


    public boolean getState() {
        return state;
    }

    /**
     * Change the state of the question to complete
     * This method too evaluate the meeting state, assing the karma to the professor and add the question
     * to the arraylist of answered questions
     * @param
     */
    /*public void setState(boolean state) {
        this.state = state;
        if(state && this.meet.getState()){
            userAnsw.setKarma(userAnsw.getKarma()+this.karma);
            userAnsw.addQuestionAnswered(this);
        }

    }*/

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public int getKarma() {
        return karma;
    }

    public void setKarma(int karma) {
        this.karma = karma;
    }

    /*public Meeting getMeet() {
        return meet;
    }*/

    /*public void setMeet(Meeting meet) {
        this.meet = meet;
    }*/

    private static int lastContactId=0;

    public static ArrayList<Question> createQuestionList(int numQuestions){
        ArrayList<Question> questions= new ArrayList<Question>();
        for (int i = 1; i <=numQuestions ; i++) questions.add(new Question(++lastContactId,false,id+" - "+lastContactId, lastContactId+i));
        return questions;
    }

    @Override
    public String toString() {
        return "Question: "+id+" - description: "+decription;
    }

}
