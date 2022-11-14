package com.example.javabucksim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        EditText etEmail = findViewById(R.id.editTextEmail);
        EditText etPW = findViewById(R.id.editTextPW);
        Button loginBut = findViewById(R.id.loginButton);

        mFirebaseAuth = FirebaseAuth.getInstance();

        loginBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString().trim();
                String pw = etPW.getText().toString().trim();

                if (!email.isEmpty() && !pw.isEmpty()){

                    mFirebaseAuth.signInWithEmailAndPassword(email, pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                // sign in complete, go to main activity
                                startActivity(new Intent(loginActivity.this, MainActivity.class));
                            } else {
                                // there was an error signing in
                                Toast.makeText(loginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(loginActivity.this, "Error: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT);
                                }
                            })

                            .addOnCanceledListener(new OnCanceledListener() {
                                @Override
                                public void onCanceled() {
                                    Toast.makeText(loginActivity.this, "Canceled!", Toast.LENGTH_SHORT);
                                }
                            });

                } else {
                    Toast.makeText(loginActivity.this,"Please enter valid email and password.",Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        EditText etEmail = findViewById(R.id.editTextEmail);
        EditText etPW = findViewById(R.id.editTextPW);

        etEmail.setText("");
        etPW.setText("");
    }

    // to be implemented
    public void forgotPassword(View view){

        Intent intent = new Intent(loginActivity.this, forgotPassword.class);
        startActivity(intent);

    }

}