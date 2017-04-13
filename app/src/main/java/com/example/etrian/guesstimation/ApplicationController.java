package com.example.etrian.guesstimation;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by etrian on 4/2/17.
 */

public class ApplicationController extends Application {

    private String yourTeam;
    private int score;
    private int classCode;
    public Connection con;
    public ResultSet results;
    public String pass;

    public int getclassCode() { return classCode; }

    public void setclassCode(int classCode) {
        this.classCode = classCode;
    }

    public String getYourTeam() {
        return yourTeam;
    }

    public void setYourTeam(String yourTeam) {
        this.yourTeam = yourTeam;
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score){
        this.score = score;
    }
    public void updateScore(int score){
        int currentScore = getScore();
        score += currentScore;
        this.score = score;
    }

        String z = "";
        int num;
        Boolean isSuccess = false;


        public String selectQueryLogin(String q)
        {
            try
            {
                Log.i("Reach:", "It reached here!");
                con = connectionclass();        // Connect to database
                if (con == null) //if connection no successful
                {
                    z = "Check Your Internet Access!";
                }
                else {
                    // Change below query according to your own database.
                        String query = q; //select query
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query); //execute select query
                        if (rs.next()) {
                            String password = rs.getString("Pass");
                            System.out.println(password);
                            pass = rs.getString("Pass");
                            z = pass;
                            isSuccess = true;
                            con.close();

                        } else {
                            z = "Invalid SELECT Query!";
                            isSuccess = false;
                        }
                }
            }
            catch (Exception ex) //an exception was thrown trying to connect to database
            {
                isSuccess = false;
                z = ex.getMessage();

                Log.d ("sql error", z);
            }
            return z;
        }

    public ResultSet getQuestions(String q)
    {
        try
        {
            Log.i("Reach:", "It reached here!");
            con = connectionclass();        // Connect to database
            if (con == null) //if connection no successful
            {
                z = "Check Your Internet Access!";
            }
            else {
                // Change below query according to your own database.
                String query = q; //select query
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query); //execute select query
                if (rs.next()) {
                    results = rs;
                    isSuccess = true;

                }
            }
        }
        catch (Exception ex) //an exception was thrown trying to connect to database
        {
            isSuccess = false;
            z = ex.getMessage();

            Log.d ("sql error", z);
        }
        return results;
    }

    public int getRowCount(String q)
    {
        try
        {
            con = connectionclass();        // Connect to database
            if (con == null) //if connection no successful
            {
                z = "Check Your Internet Access!";
            }
            else {
                // Change below query according to your own database.
                String query = q; //select query
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query); //execute select query
                if (rs.next()) {
                    num = rs.getInt(1);
                    con.close();

                } else {
                    z = "Invalid SELECT Query!";
                    isSuccess = false;
                }
            }
        }
        catch (Exception ex) //an exception was thrown trying to connect to database
        {
            isSuccess = false;
            z = ex.getMessage();

            Log.d ("sql error", z);
        }
        return num;
    }

    public String manipulateQuery(String q)
    {
        try
        {
            Log.i("Reach:", "It reached here!");
            con = connectionclass();        // Connect to database
            if (con == null) //if connection no successful
            {
                z = "Check Your Internet Access!";
            }
            else {
                String query = q; //update query
                Statement stmt = con.createStatement();
                int rs = stmt.executeUpdate(query); //use execute update since it doesnt return a set
                if ((rs == 4) || (rs == 1) || (rs == 2) || (rs == 3) || (rs == 0)) { // these were numbers the executeUpdate were returning
                    z = "query successful";
                    isSuccess = true;
                    con.close(); //close connection

                } else {
                    z = "Invalid UPDATE Query!";
                    isSuccess = false;
                }
            }
        }
        catch (Exception ex) //an exception was thrown trying to connect to database
        {
            isSuccess = false;
            z = ex.getMessage();

            Log.d ("sql error", z);
        }
        return z;
    }

    @SuppressLint("NewApi")
    public Connection connectionclass()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;
        try
        {
            Log.e("Is it reaching this:", "Yes");
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            //your database connection string goes below
            ConnectionURL = "jdbc:jtds:sqlserver://kylecorrina.database.windows.net:1433;DatabaseName=capstone;user=adminkyle@kylecorrina;password=Admin2017!;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;"; //connection string
            connection = DriverManager.getConnection(ConnectionURL);
        }
        catch (SQLException se)
        {
            Log.e("error here 1 : ", se.getMessage());
        }
        catch (ClassNotFoundException e)
        {
            Log.e("error here 2 : ", e.getMessage());
        }
        catch (Exception e)
        {
            Log.e("error here 3 : ", e.getMessage());
        }
        return connection;
    }

}
