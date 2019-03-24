package com.tambo.Connection;
import android.os.StrictMode;

import com.tambo.Model.Question;
import com.tambo.Model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public interface Connect_Server {

    String url_server = "http://192.168.137.1:8080/ServerTambo/";
}