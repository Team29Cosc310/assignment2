package com.example.javabucksim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();

        // logout and end activity
        Button logoutBut = findViewById(R.id.logoutButton);
        logoutBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFirebaseAuth.signOut();
                finish();
            }
        });

    }

    // check if user is logged in
    // if not logged in, go to login screen
    @Override
    protected void onStart(){
        super.onStart();

        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser != null){
            //user is logged in
        } else {
            startActivity(new Intent(this, loginActivity.class));
            finish();
        }

    }

    public void getInfo(View view){

        String doc = mFirebaseAuth.getUid();

        DocumentReference docRef = db.collection("users").document(doc);


        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map <String, Object> fields = document.getData();
                        setInfo((fields));
                    } else {
                        // do stuff
                    }
                } else {
                    //Toast.makeText(activity_home.this, "Error", Toast.LENGTH_LONG).show();
                }


            }
        });

    }

    private void setInfo(Map<String, Object> fields){

        TextView result = findViewById(R.id.textViewResult);

        String firstName = fields.get("firstName").toString();
        String lastName = fields.get("lastName").toString();
        String email = fields.get("email").toString();
        String pw = fields.get("password").toString();
        String role = fields.get("role").toString();

        String res = String.format("Firstname: %s \n" +
                "Lastname: %s \n" +
                "Email: %s \n" +
                "Password: %s \n" +
                "Role: %s \n", firstName,lastName,email,pw,role);


        result.setText(res);

    }

}