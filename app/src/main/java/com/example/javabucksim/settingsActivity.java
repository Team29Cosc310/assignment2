package com.example.javabucksim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class settingsActivity extends AppCompatActivity {

    private Bundle bundle;
    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        showCurUserInfo();
    }

    private void showCurUserInfo(){

        Intent intent = getIntent();
        bundle = intent.getExtras();


        String firstName = bundle.getString("firstName");
        String lastName = bundle.getString("lastName");
        String email = bundle.getString("email");
        String pw = bundle.getString("password");
        role = bundle.getString("role");

        String res = String.format("Firstname: %s \n" +
                "Lastname: %s \n" +
                "Email: %s \n" +
                "Password: %s \n" +
                "Role: %s \n", firstName,lastName,email,pw,role);

        TextView result = findViewById(R.id.curUserInfo);
        result.setText(res);


    }

}