package com.example.javabucksim;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class deleteAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void submit(View view){
        finish();
    }

}