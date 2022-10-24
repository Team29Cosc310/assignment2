package com.example.javabucksim.settings;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.javabucksim.MainActivity;
import com.example.javabucksim.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Map;

public class settingsActivity extends AppCompatActivity {

    private Bundle bundle;
    private String role;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        back = findViewById(R.id.backButton);


        Intent intent = getIntent();
        bundle = intent.getExtras();

        if (bundle != null){
            showCurUserInfo();
            showAdminSettings();
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void onResume() {

        checkChanges();

        super.onResume();
    }


    private void showCurUserInfo(){


        String firstName = bundle.getString("firstName");
        String lastName = bundle.getString("lastName");
        String email = bundle.getString("email");
        String pw = bundle.getString("password");
        role = bundle.getString("role");

        String res = String.format(
                "Firstname: %s \n" +
                "Lastname: %s \n" +
                "Email: %s \n" +
                "Password: %s \n" +
                "Role: %s \n", firstName,lastName,email,pw,role);

        TextView result = findViewById(R.id.curUserInfo);
        result.setText(res);

    }

    private void checkChanges(){

        String userId = mAuth.getUid();

        final DocumentReference docRef = db.collection("users").document(userId);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    //Log.w(TAG, "Listen failed.", e);
                    return;
                }

                String source = snapshot != null && snapshot.getMetadata().hasPendingWrites()
                        ? "Local" : "Server";

                if (snapshot != null && snapshot.exists()) {
                    Map <String, Object> updatedData = snapshot.getData();

                    String newFirstName = updatedData.get("firstName").toString();
                    String newLastName = updatedData.get("lastName").toString();
                    String newEmail = updatedData.get("email").toString();
                    String newPassword = updatedData.get("password").toString();

                    bundle.putString("firstName", newFirstName);
                    bundle.putString("lastName", newLastName);
                    bundle.putString("email", newEmail);
                    bundle.putString("password", newPassword);

                    showCurUserInfo();

                } else {
                    //Log.d(TAG, source + " data: null");
                }
            }
        });


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