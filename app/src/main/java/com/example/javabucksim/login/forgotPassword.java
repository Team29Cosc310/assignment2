package com.example.javabucksim.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.javabucksim.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPassword extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mAuth = FirebaseAuth.getInstance();

        back = findViewById(R.id.backButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //back button
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    // to be implemented
    public void forgotPassword(View view){


        EditText etEmail = findViewById(R.id.etForgotPW);
        String emailAddress = etEmail.getText().toString().trim();

        //checking if email is empty
        if(TextUtils.isEmpty(emailAddress)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }


        mAuth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(forgotPassword.this, "Reset password email sent.", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

    }


}