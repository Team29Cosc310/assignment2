package com.example.javabucksim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class settingsActivity extends AppCompatActivity {

    private Bundle bundle;
    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        Intent intent = getIntent();
        bundle = intent.getExtras();

        if (bundle != null){
            showCurUserInfo();
            showAdminSettings();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private void showCurUserInfo(){


        String firstName = bundle.getString("firstName");
        String lastName = bundle.getString("lastName");
        String email = bundle.getString("email");
        String pw = bundle.getString("password");
        role = bundle.getString("role");

        String res = String.format("Account Info: \n" +
                "Firstname: %s \n" +
                "Lastname: %s \n" +
                "Email: %s \n" +
                "Password: %s \n" +
                "Role: %s \n", firstName,lastName,email,pw,role);

        TextView result = findViewById(R.id.curUserInfo);
        result.setText(res);

    }

    private void showAdminSettings(){

        LinearLayout admLay = findViewById(R.id.adminLayout);

        if (!role.equals("admin")){
            admLay.setVisibility(View.GONE);
        } else {
            admLay.setVisibility((View.VISIBLE));
        }

    }
    public void editAccount(View view){

        Intent intent = new Intent(settingsActivity.this, editAccount.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    public void createAccount(View view){

        Intent intent = new Intent(settingsActivity.this, createAccount.class);
        startActivity(intent);

    }

    public void editUsers(View view){

        Intent intent = new Intent(settingsActivity.this, editUsers.class);
        startActivity(intent);

    }

    public void deleteAccount(View view){

        Intent intent = new Intent(settingsActivity.this, deleteAccount.class);
        startActivity(intent);

    }

}