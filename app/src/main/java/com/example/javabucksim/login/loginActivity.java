package com.example.javabucksim.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chad.designtoast.DesignToast;
import com.example.javabucksim.MainActivity;
import com.example.javabucksim.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;

    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;

    SignInButton signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        // Set the dimensions of the sign-in button.
        signInButton = findViewById(R.id.sign_in_button);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });


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
                                DesignToast.makeText(loginActivity.this, "Successfully logged in", DesignToast.LENGTH_SHORT, DesignToast.TYPE_SUCCESS).show();
                                startActivity(new Intent(loginActivity.this, MainActivity.class));
                            } else {
                                // there was an error signing in
                                DesignToast.makeText(loginActivity.this, "Something went wrong.", DesignToast.LENGTH_SHORT, DesignToast.TYPE_ERROR).show();
                            }
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    DesignToast.makeText(loginActivity.this, "Error: " + e.getLocalizedMessage(), DesignToast.LENGTH_SHORT, DesignToast.TYPE_ERROR).show();
                                }
                            })

                            .addOnCanceledListener(new OnCanceledListener() {
                                @Override
                                public void onCanceled() {
                                    DesignToast.makeText(loginActivity.this, "Canceled!.", DesignToast.LENGTH_SHORT, DesignToast.TYPE_ERROR).show();
                                }
                            });

                } else {
                    DesignToast.makeText(loginActivity.this, "Please enter a valid email and password.", DesignToast.LENGTH_SHORT, DesignToast.TYPE_ERROR).show();
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

    void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,1000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                navigateToMain();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong",Toast.LENGTH_SHORT).show();
            }
        }
    }
    void navigateToMain() {
        finish();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}