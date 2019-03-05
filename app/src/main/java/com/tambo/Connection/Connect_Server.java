package com.tambo.Connection;
import android.os.StrictMode;

import com.tambo.Model.Question;
import com.tambo.Model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Connect_Server {

    private static Socket client;
    private static ObjectInputStream inputs;
    private static ObjectOutputStream outputs;

    synchronized public static void startConnection() {
        try {
            if (client == null || client.isClosed()) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                connectToServer();
                getStreams();

            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    private static void connectToServer() throws IOException {
        client = new Socket("172.25.12.21", 12345);
        client.setKeepAlive(true);
        client.setTcpNoDelay(true);
    }


    private static void getStreams() throws IOException {
        outputs = new ObjectOutputStream(client.getOutputStream());
        outputs.flush();
        inputs = new ObjectInputStream(client.getInputStream());

    }
    public static boolean isUser(User user) throws InterruptedException {
        ArrayList<Object> petition= new ArrayList<Object>();
        petition.add("LogIn");
        petition.add(user);
        send(petition);
        Thread.sleep(500);
        return (boolean) receiveMessage();
    }
    public static  boolean addUser (User user) throws InterruptedException {
        ArrayList<Object> petition = new ArrayList<Object>();
        petition.add("SignUp");
        petition.add(user);
        send(petition);
        Thread.sleep(500);
        return (boolean) receiveMessage();

    }

    public static ArrayList<Question> getQuestionsProfessor(User user) throws InterruptedException {
        ArrayList<Object> petition = new ArrayList<Object>();
        petition.add("QuestionsProfessor");
        petition.add(user);
        send(petition);
        Thread.sleep(500);
        return (ArrayList<Question>) receiveMessage();
    }

    public static boolean setUserAnswerQuestion(Question question) throws InterruptedException{
        ArrayList<Object> petition = new ArrayList<Object>();
        petition.add("SetUserAnswerQuestion");
        petition.add(question);
        send(petition);
        Thread.sleep(500);
        return (boolean) receiveMessage();
    }

    public static boolean setAnsweredQuestion(Question question) throws  InterruptedException{
        ArrayList<Object> petition = new ArrayList<Object>();
        petition.add("SetAnsweredQuestion");
        petition.add(question);
        send(petition);
        Thread.sleep(500);
        return (boolean) receiveMessage();
    }

    public static boolean createQuestion(Question question) throws  InterruptedException{
        ArrayList<Object> petition = new ArrayList<Object>();
        petition.add("CreateQuestion");
        petition.add(question);
        send(petition);
        Thread.sleep(500);
        return (boolean) receiveMessage();
    }

    public static ArrayList<Question> getQuestionsStudent(User user) throws InterruptedException {
        ArrayList<Object> petition = new ArrayList<Object>();
        petition.add("QuestionsStudent");
        petition.add(user);
        send(petition);
        Thread.sleep(500);
        return (ArrayList<Question>) receiveMessage();
    }


    public static void send (ArrayList<Object> objects){
        try{
            outputs.writeObject(objects);
            outputs.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static Object receiveMessage() {

        try {
            Object obj=null;
            do {
                obj = inputs.readObject();
            }while (obj==null);
            System.out.println(obj+" Recibido");
            return obj;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }




}