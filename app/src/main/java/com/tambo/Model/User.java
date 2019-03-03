package com.tambo.Model;


import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author mancipox
 */
public class User {

    private String username;
    private String firstName;
    private String secondName;
    private String password;
    private String email;
    private String phone;
    private String gender;
    private final ArrayList<Question> questionsDone;
    private final ArrayList<Question> questionsAnswered;
    private int karma;

    public User() {
        this.questionsDone = new ArrayList<>();
        this.questionsAnswered = new ArrayList<>();
    }

    public User(String username, String firstName, String secondName, String password, String email, String phone, String gender, int karma) {
        this.username = username;
        this.firstName = firstName;
        this.secondName = secondName;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.questionsDone = new ArrayList<>();
        this.questionsAnswered = new ArrayList<>();
        this.karma = karma;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getKarma() {
        return karma;
    }

    public void setKarma(int karma) {
        this.karma = karma;
    }

    /**
     * Get the questions done by this user
     *
     * @return An arraylist with the questions done
     */
    public ArrayList<Question> getQuestionsDone() {
        return questionsDone;
    }

    /**
     * Get the question with the specific id done by this user
     *
     * @param id The id of the question
     * @return A question done
     */
    public Question getQuestionsDone(int id) {
        for (int i = 0; i < questionsDone.size(); i++) {
            if (questionsDone.get(i).getId() == id) {
                return questionsDone.get(i);
            }
        }
        return null;
    }

    /**
     * Get the questions answered by the user
     *
     * @return A arraylist of questions answered
     */
    public ArrayList<Question> getQuestionsAnswered() {
        return questionsAnswered;
    }

    /**
     * Get the question with the specific id answered by this user
     *
     * @param id The id of the question
     * @return A question answered
     */
    public Question getQuestionsAnswered(int id) {
        for (int i = 0; i < questionsAnswered.size(); i++) {
            if (questionsAnswered.get(i).getId() == id) {
                return questionsAnswered.get(i);
            }
        }
        return null;
    }

    /**
     * Add a question in array of questionsDone
     *
     * @param question Question to add in done
     */
    private void addQuestionDone(Question question) {
        questionsDone.add(question);
    }

    /**
     * Add a question in array of questionAnswered
     *
     * @param question Question to add in answered
     */
    public void addQuestionAnswered(Question question) {
        questionsAnswered.add(question);
    }

    /**
     * Create a question with id_quest, description_quest, karma_quest and
     * assing a meet to it with id_meet, date and place_meet. This method record the
     * created question in arraylist of questions done.
     * The method can be done just if the user has enought karma points according to karma_quest value
     * @param id_quest integer with the id of the question
     * @param description_quest String with the description of the question
     * @param karma_quest integer with the weight in karma point of the question
     * @param id_meet integer with the id of the meeting
     * @param date Date of the meeting
     * @param place_meet String with the place of the meeting
     * @return True or false if could or not post the Question
     */
    public boolean createQuestion(int id_quest, String description_quest, int karma_quest, int id_meet, String date, String place_meet) {
        if (this.karma > karma_quest) {
            Meeting meet = new Meeting(id_meet, date, place_meet, false);
            Question quest = new Question(id_quest, this, null, false, description_quest, karma_quest, meet);
            this.karma-=karma_quest;
            addQuestionDone(quest);
            return true;
        }else return false;

    }

    /**
     * Accept a question (professor)
     * @param quest The question to accept
     */
    public void acceptQuestion(Question quest) {
        quest.setUserAnsw(this);
    }

    /**
     * Set to complete a question with id id done by the user. This method too
     * set the status of the meeting to true and the status of the question to
     * true/complete
     *
     * @param id integer with the id of the question
     */
    public void setCompleteQuestion(int id) {
        Question quest = getQuestionsDone(id);
        quest.getMeet().setState(true);
        quest.setState(true);
    }

    @Override
    public String toString() {
        return "User: " + username + " - email: " + email + " - karma: " + karma;
    }

}