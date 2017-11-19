package com.example.zakk.givemesomespace;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String userName = "";
    String userPass = "";
    ProgressBar pb = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pb = (ProgressBar)findViewById(R.id.progressBarHidden);


    }

    public void loginButtonClicked(View view) {
        pb.setVisibility(View.VISIBLE);
        boolean loginResult = loginProcess();

//
//        if(loginResult){
//            Intent intent = new Intent(this, MapViewActivity.class);
//            startActivity(intent);
//        }

        if(loginResult){
            Intent intent = new Intent(this, GoogleMapsActivity.class);
            startActivity(intent);
        }
    }



    public boolean loginProcess(){
        boolean result = false;
        userName = ((EditText)findViewById(R.id.loginUsernameTxt)).getText().toString();
        userPass = ((EditText)findViewById(R.id.loginPassTxt)).getText().toString();
        dataBaseControlAgent x = new dataBaseControlAgent();
        x.setParameters(userName, userPass);
        x.execute();


        long connectionStartTime = System.currentTimeMillis();
        long elapsedTime = System.currentTimeMillis();
        //1min timeout
        try{
            while(elapsedTime - connectionStartTime < 30000){
                Thread.sleep(500);
                if(x.isLoginResult()){
                    Toast.makeText(this,"heyyyy! you got in the db!",Toast.LENGTH_SHORT).show();
                    result = true;
                    break;
                }
                elapsedTime = System.currentTimeMillis();
            }

            if(!x.isLoginResult()){
                Toast.makeText(this,"login Failed!!",Toast.LENGTH_SHORT).show();
                x.setLoginResult(false);
                result = false;
            }
            x.setLoginResult(false);

        }catch(Exception ex){
            ex.printStackTrace();
        }

        return result;
    }

    public boolean loginValidation(){
        boolean result = false;

        if( (userName == null) || ("".equals(userName)) ){
            Toast.makeText(this, "messed up with your ID...",Toast.LENGTH_SHORT);
        }else{
            if( (userPass == null) || ("".equals(userPass)) ){
                Toast.makeText(this, "messed up with your PASSWORD...",Toast.LENGTH_SHORT);
            }else{
                result = true;
            }
        }


        return result;
    }
}// end of MainActivity

