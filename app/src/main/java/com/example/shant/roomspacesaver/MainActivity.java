package com.example.shant.roomspacesaver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.database.sqlite.*;


import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void showSignUpView(View view){
        Log.d("New user!","Signup");
        LinearLayout loginlayout= (LinearLayout) findViewById(R.id.loginView);
        LinearLayout signUpLayout = (LinearLayout) findViewById(R.id.signUpView);
        final EditText newUserName = (EditText) findViewById(R.id.newUserNameText);
        final EditText newUserPass = (EditText) findViewById(R.id.newUserPasswordText);
        Button signUpButton = (Button) findViewById(R.id.signUpButton);
        loginlayout.setVisibility(View.INVISIBLE);
        signUpLayout.setVisibility(View.VISIBLE);
        //User newUser = new User();
        signUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                String userName = newUserName.getText().toString();
                String userPass = newUserPass.getText().toString();
                Log.d("Username", userName);//if both are blank, logs dont work
                Log.d("Password",userPass);//if both are blank, logs dont work
                Log.d("Signup Button","Clicked");
                //create new user
                User newUser = new User();
                newUser.userName = userName;
                newUser.password = userPass;
                newUser.rooms= new Room[0];
            }
        });

    }
}
