package com.example.shant.roomspacesaver;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.database.sqlite.*;
import android.widget.Toast;
import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {
    DBHelper myDb;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("this",this.toString());
    }

    public void logUserIn(View view){
        final EditText UserName = (EditText) findViewById(R.id.userNameText);
        final EditText UserPass = (EditText) findViewById(R.id.userPasswordText);
        loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Login inside"," Clicked");
                String username = UserName.getText().toString();
                String password = UserPass.getText().toString();
                Log.d(username,password);
                boolean result = myDb.checkCredentials(username, password);
                if (result){
                    Log.d("Logged in:", "successfully");
                }else{
                    Log.d("Invalid username ","and password");
                }
            }
        });
        Log.d("Login outside"," Clicked");

//        Intent intent = new Intent(this, RoomsActivity.class);
//        EditText editText = (EditText) findViewById(R.id.loggedIn_message);
//        String message = editText.getText().toString();
//        intent.putExtra("ROOMS", "User logged in");
//        startActivity(intent);
    }
    public void showSignUpView(View view){
        final LinearLayout loginlayout= (LinearLayout) findViewById(R.id.loginView);
        final LinearLayout signUpLayout = (LinearLayout) findViewById(R.id.signUpView);
        final EditText newUserName = (EditText) findViewById(R.id.newUserNameText);
        final EditText newUserPass = (EditText) findViewById(R.id.newUserPasswordText);
        Log.d("New user!","Signup");
    }
}
