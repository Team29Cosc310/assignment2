package com.example.javabucksim;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mFirebaseAuth;
    private String role;



    String chai;
    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();

        ProgressBar progBar = findViewById(R.id.indeterminateBar);
        progBar.setVisibility(View.GONE);

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
        setUpReports();
        setUpLogout();
        lowStackWarning();



    }

    // check if user is logged in
    // if not logged in, go to login screen
    @Override
    protected void onStart(){
        super.onStart();

        showLoading();

        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser != null){
            // user is logged in

        } else {
            startActivity(new Intent(this, loginActivity.class));
            finish();
        }
    }

    // does nothing extra currently
    @Override
    protected void onResume() {
        super.onResume();

        endLoading();

    }

    // show loading circle, hide layout
    private void showLoading(){

        ProgressBar progBar = findViewById(R.id.indeterminateBar);
        ConstraintLayout layout = findViewById(R.id.main_layout);

        layout.setVisibility(View.GONE);
        progBar.setVisibility(View.VISIBLE);

    }

    // end loading circle, show layout
    private void endLoading(){

        ProgressBar progBar = findViewById(R.id.indeterminateBar);
        ConstraintLayout layout = findViewById(R.id.main_layout);

        progBar.setVisibility(View.GONE);
        layout.setVisibility(View.VISIBLE);

    }

    // settings on click button
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

    // reports on click button
    public void setUpReports() {
        Button reportButton = findViewById(R.id.reportButton);
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, reportActivity.class);
                startActivity(intent);
            }
        });
    }

    // logout user and end activity
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

    //changes settings text based on role
    public void setUpView(){

        Button settingsBut = findViewById(R.id.settingsButton);
        settingsBut.setText(role + " Settings");





    }
    public void placeOrder(View view)
    {
        Button order = findViewById(R.id.place_order);
        Intent intent = new Intent(this, autoOrder.class);
        startActivity(intent);
    }



    // puts user data in bundle
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

    }

     //
    void lowStackWarning() {
        String doc = "j9BQe3OtLP6XnUK66MWK";


        DocumentReference documentReference = db.collection("Inventory").document(doc);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                chai = value.getString("chai");
                alert(chai);
            }
        });
    }

    public void alert(String chai) {
        Button test = (Button) findViewById(R.id.test);

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setCancelable(true);
                builder.setTitle("Low stock warning");
                builder.setMessage("Please resupply your stock!");

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.setPositiveButton("Order now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });
    }
}