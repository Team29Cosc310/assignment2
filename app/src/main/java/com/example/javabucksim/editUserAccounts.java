package com.example.javabucksim;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class editUserAccounts extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    Bundle bundle;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_accounts);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        intent = getIntent();
        bundle = new Bundle();

        getData();
        populateFields();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void getData(){

        String email = intent.getStringExtra("email");

        CollectionReference usersRef = db.collection("users");
        usersRef.whereArrayContains("email", email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){

                    for (QueryDocumentSnapshot document : task.getResult()){
                        Map<String, Object> updatedData = document.getData();

                        String newFirstName = updatedData.get("firstName").toString();
                        String newLastName = updatedData.get("lastName").toString();
                        String newEmail = updatedData.get("email").toString();
                        String newPassword = updatedData.get("password").toString();

                        bundle.putString("firstName", newFirstName);
                        bundle.putString("lastName", newLastName);
                        bundle.putString("email", newEmail);
                        bundle.putString("password", newPassword);

                        Toast.makeText(editUserAccounts.this,newFirstName,Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(editUserAccounts.this,"Something went wrong getting data",Toast.LENGTH_SHORT).show();
                }

            }
        });




    }

    private void populateFields(){

        String firstName = bundle.getString("firstName");
        String lastName = bundle.getString("lastName");
        String email = bundle.getString("email");
        String pw = bundle.getString("password");

        EditText fn = findViewById(R.id.et_editUsr_fn);
        EditText ln = findViewById(R.id.et_editUsr_ln);
        EditText emailField = findViewById(R.id.et_editUsr_email);
        EditText password = findViewById(R.id.et_editUsr_pw);

        fn.setText(firstName);
        ln.setText(lastName);
        emailField.setText(email);
        password.setText(pw);

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}