package com.example.javabucksim.settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.chad.designtoast.DesignToast;
import com.example.javabucksim.R;
import com.example.javabucksim.login.loginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class createAccount extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    Map<String, Object> newUser;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mAuth = FirebaseAuth.getInstance();

        back = findViewById(R.id.backButton);

        setupSpinner();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void setupSpinner(){

        Spinner spinner = (Spinner) findViewById(R.id.role_spinner);
            // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.role_array, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

    }

    //back button
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    // submit form, create new user, create new document
    public void submit(View view){

        String email = ((EditText) findViewById(R.id.createAcc_getEmail)).getText().toString();
        String password = ((EditText) findViewById(R.id.createAcc_getPW)).getText().toString();
        String fName = ((EditText) findViewById(R.id.createAcc_getFirstName)).getText().toString();
        String lName = ((EditText) findViewById(R.id.createAcc_getLastName)).getText().toString();
        String role = ((Spinner) findViewById(R.id.role_spinner)).getSelectedItem().toString();

        if (email.isEmpty() || password.isEmpty()){
            DesignToast.makeText(createAccount.this, "Please enter valid email and password", DesignToast.LENGTH_SHORT, DesignToast.TYPE_WARNING).show();
            return;
        }

        if (password.length() < 6){
            DesignToast.makeText(createAccount.this, "Password must be at least 6 characters", DesignToast.LENGTH_SHORT, DesignToast.TYPE_WARNING).show();
            return;
        }

        newUser = new HashMap<>();
        newUser.put("email", email);
        newUser.put("password", password);
        newUser.put("firstName", fName);
        newUser.put("lastName", lName);
        newUser.put("role", role);

        //to be worked on
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (!task.isSuccessful()){
                    //creation failed
                } else {
                    String newUid = task.getResult().getUser().getUid();

                    // Create document with user info

                    db.collection("users").document(newUid).set(newUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            DesignToast.makeText(createAccount.this, "Account successfully created", DesignToast.LENGTH_SHORT, DesignToast.TYPE_SUCCESS).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            DesignToast.makeText(createAccount.this, "Something went wrong", DesignToast.LENGTH_SHORT, DesignToast.TYPE_ERROR).show();
                        }
                    });



                }


            }
        });


    }

}