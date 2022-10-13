package com.example.javabucksim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mFirebaseAuth;
    private String role;
    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();


        if (mFirebaseUser != null) {
            // user is logged in
            try {
                // get user info from firebase
                getInfo();
            } catch (Exception e) {
                //if it fails show error
                TextView result = findViewById(R.id.textViewResult);
                result.setText(e.toString());
            }
        }

        setUpSettings();
        setUpLogout();

        // logout user and end activity


    }

    // check if user is logged in
    // if not logged in, go to login screen
    @Override
    protected void onStart(){
        super.onStart();

        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser != null){
            // user is logged in

        } else {
            startActivity(new Intent(this, loginActivity.class));
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    public void setUpSettings(){

        Button logoutBut = findViewById(R.id.settingsButton);
        logoutBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, settingsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private void setUpLogout(){

        Button logoutBut = findViewById(R.id.logoutButton);
        logoutBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFirebaseAuth.signOut();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    // method that gets user info from database based on their login user id
    public void getInfo(){

        String doc = mFirebaseAuth.getUid();

        DocumentReference docRef = db.collection("users").document(doc);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Object> userInfo = document.getData();
                        setInfo(userInfo);
                        setUpView();
                    } else {
                        // do stuff
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public void setUpView(){

        Button settingsBut = findViewById(R.id.settingsButton);
        settingsBut.setText(role + " Settings");


    }

    // This was used for testing the database
    // Saved for temporary reference

    private void setInfo(Map<String, Object> fields){


        String firstName = fields.get("firstName").toString();
        String lastName = fields.get("lastName").toString();
        String email = fields.get("email").toString();
        String pw = fields.get("password").toString();
        role = fields.get("role").toString();

        bundle.putString("firstName", firstName);
        bundle.putString("lastName", lastName);
        bundle.putString("email", email);
        bundle.putString("password", pw);
        bundle.putString("role", role);

        /*
        String res = String.format("Firstname: %s \n" +
                "Lastname: %s \n" +
                "Email: %s \n" +
                "Password: %s \n" +
                "Role: %s \n", firstName,lastName,email,pw,role);

        TextView result = findViewById(R.id.textViewResult);
        result.setText(res);

         */

    }

     //

}