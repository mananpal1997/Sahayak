package com.example.android.itday;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.android.itday.UselessPages.Login;
import com.example.android.itday.UselessPages.Register;
import com.example.android.itday.UselessPages.TaskAssign;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Start NewActivity.class


    }

    public void login_page_forward(View view) {
        Intent myIntent = new Intent(MainActivity.this, Login.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        startActivity(myIntent);
    }

    public void register_page_forward(View view) {
        Intent myIntent = new Intent(MainActivity.this,
            Register.class);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        startActivity(myIntent);
    }
}
