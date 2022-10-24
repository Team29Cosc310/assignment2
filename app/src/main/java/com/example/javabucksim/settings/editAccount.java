package com.example.javabucksim.settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.javabucksim.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class editAccount extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        bundle = intent.getExtras();

        populateFields();

    }

    private void populateFields(){

        String firstName = bundle.getString("firstName");
        String lastName = bundle.getString("lastName");
        String email = bundle.getString("email");
        String pw = bundle.getString("password");

        EditText fn = findViewById(R.id.et_editAcc_fn);
        EditText ln = findViewById(R.id.et_editAcc_ln);
        EditText emailField = findViewById(R.id.et_editAcc_email);
        EditText password = findViewById(R.id.et_editAcc_pw);

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

    // submit form, go back to settings activity
    public void submit(View view){

        String oldFirstName = bundle.getString("firstName");
        String oldLastName = bundle.getString("lastName");
        String oldPW = bundle.getString("password");

        String newFirstName = ((EditText)findViewById(R.id.et_editAcc_fn)).getText().toString();
        String newLastName = ((EditText)findViewById(R.id.et_editAcc_ln)).getText().toString();
        String newPW = ((EditText)findViewById(R.id.et_editAcc_pw)).getText().toString();

        FirebaseUser user = mAuth.getCurrentUser();
        String userId = user.getUid();


        if (newPW.length() < 6){
            Toast.makeText(editAccount.this,"Password must be at least 6 characters",Toast.LENGTH_SHORT).show();
            return;
        }

        if (newPW != oldPW){
            // may need re-authentication, to be implemented later
            user.updatePassword(newPW);
        }

        if ((!newFirstName.equals(oldFirstName)) || (!newLastName.equals(oldLastName)) || (!newPW.equals(oldPW))){

            HashMap<String, Object> updateFields = new HashMap<>();

            if (newFirstName != oldFirstName){
                updateFields.put("firstName", newFirstName);
            }
            if (newLastName != oldLastName){
                updateFields.put("firstName", newFirstName);
            }
            if (newPW != oldPW){
                updateFields.put("password", newPW);
            }

            db.collection("users").document(userId).update(updateFields).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(editAccount.this, "Fields successfully updated", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(editAccount.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });

            finish();
        } else {
            Toast.makeText(editAccount.this, "No changes made", Toast.LENGTH_SHORT).show();
            finish();
        }


    }

}