package com.rtd.javafx;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connect {

    public static Connection connectDB(){
        try {
            Class.forName("com.mysql.jdbc.Driver");


            Connection connect= DriverManager.getConnection("jdbc:mysql://localhost:3307/multiuser","root","");

            return connect;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
