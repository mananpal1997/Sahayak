package com.example.android.itday.UselessPages;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.android.itday.MainActivity;
import com.example.android.itday.R;

import im.delight.android.location.SimpleLocation;

public class Register extends AppCompatActivity {
    private SimpleLocation location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        location = new SimpleLocation(this);
        if (!location.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(this);
        }

    }

    public void open_contractor_register_page(View view) {
        Intent myIntent = new Intent(Register.this, Register_Contractor.class);
        startActivity(myIntent);
    }

    public void open_worker_register_page(View view) {
        Intent myIntent = new Intent(Register.this, Register_Worker.class);
        startActivity(myIntent);
    }

    public void open_client_register_page(View view) {
        Intent myIntent = new Intent(Register.this, Register_Client.class);
        startActivity(myIntent);
    }
}
