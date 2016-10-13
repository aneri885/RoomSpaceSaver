package com.example.shant.roomspacesaver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void showSignUpView(View view){
        LinearLayout loginlayout= (LinearLayout) findViewById(R.id.loginView);
        LinearLayout signUpLayout = (LinearLayout) findViewById(R.id.signUpView);
        loginlayout.setVisibility(View.INVISIBLE);
        signUpLayout.setVisibility(View.VISIBLE);
    }
}
