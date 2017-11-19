package com.example.zakk.givemesomespace;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.net.URL;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by zakk on 09/11/2017.
 */

public class dataBaseControlAgent extends AsyncTask<String, String, String> {


    //hardcoding for now.
    protected String sid = "SQLD";
    protected String dbUser = "COMP214_F17_118";
    protected String dbURL = "oracle1.centennialcollege.ca";
    protected String dbPass = "password";

    private String appUserName = "";
    private String appUserPass = "";

    private boolean loginResult = false;



    @Override
    protected String doInBackground(String... strings) {
        connect();
        return null;
    }


    public boolean connect(){
        boolean result = false;

        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@" +dbURL+ ":1521:" +sid, dbUser,dbPass);

            String query = "select * from demoLogin where username = ? and password = ?";
            PreparedStatement stmt=con.prepareStatement(query);
            stmt.setString(1, appUserName);
            stmt.setString(2, appUserPass);

            ResultSet rs=stmt.executeQuery();


            while(rs.next()) {
                Log.i("DBInfo", rs.getNString(1) + " " + rs.getNString(2) + " " + rs.getNString(3));
                setLoginResult(true);
            }
            con.close();



        }catch (Exception ex){
            Log.i("Exception", ex.getMessage());
        }


        return result;
    }

    public void setParameters(String userNameInput, String userPassInput){
        appUserName = userNameInput;
        appUserPass = userPassInput;
    }

    public boolean isLoginResult() {
        return loginResult;
    }

    public void setLoginResult(boolean loginResult) {
        this.loginResult = loginResult;
    }

}
