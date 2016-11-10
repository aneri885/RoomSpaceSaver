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
        myDb = new DBHelper(this); //required for reference
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
                    Toast.makeText(MainActivity.this, "Success !", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, RoomsActivity.class);
                    intent.putExtra("ROOMS", "User logged in");
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, "Invalid Details, try again !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Log.d("Login outside"," Clicked");
    }
    public void showSignUpView(View view){
        final LinearLayout loginlayout= (LinearLayout) findViewById(R.id.loginView);
        final LinearLayout signUpLayout = (LinearLayout) findViewById(R.id.signUpView);
        final EditText newUserName = (EditText) findViewById(R.id.newUserNameText);
        final EditText newUserPass = (EditText) findViewById(R.id.newUserPasswordText);
        Log.d("New user!","Signup");
//        Button signUpButton = (Button) findViewById(R.id.signUpButton);
//        loginlayout.setVisibility(View.INVISIBLE);
//        signUpLayout.setVisibility(View.VISIBLE);
        //User newUser = new User();
//        signUpButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // Perform action on click
//                String userName = newUserName.getText().toString();
//                String userPass = newUserPass.getText().toString();
//                Log.d("Username", userName);//if both are blank, logs dont work
//                Log.d("Password",userPass);//if both are blank, logs dont work
//                Log.d("Signup Button","Clicked");
//                //create new user
//                User newUser = new User();
//                newUser.userName = userName;
//                newUser.password = userPass;
//                newUser.rooms= new Room[0];
//                boolean inserted = myDb.insertUser(userName, userPass, "[]");
//                if (inserted){
//                    Toast.makeText(MainActivity.this, "User created, Please login", Toast.LENGTH_SHORT).show();
//                    loginlayout.setVisibility(View.VISIBLE);
//                    signUpLayout.setVisibility(View.INVISIBLE);
//                }
//                else{
//                    Toast.makeText(MainActivity.this, "User creation failed, retry again", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }
}
