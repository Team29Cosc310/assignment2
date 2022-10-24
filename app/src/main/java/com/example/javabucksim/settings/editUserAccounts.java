package com.example.javabucksim.settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.javabucksim.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class editUserAccounts extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    Bundle bundle;
    Intent intent;
    String userId;

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

    }

    private void getData(){

        String email = intent.getStringExtra("email");

        Task<QuerySnapshot> usersRef = db.collection("users").whereEqualTo("email", email).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> updatedData = document.getData();
                                userId = document.getId();

                                String newFirstName = updatedData.get("firstName").toString();
                                String newLastName = updatedData.get("lastName").toString();
                                String newEmail = updatedData.get("email").toString();
                                String newPassword = updatedData.get("password").toString();

                                bundle.putString("firstName", newFirstName);
                                bundle.putString("lastName", newLastName);
                                bundle.putString("email", newEmail);
                                bundle.putString("password", newPassword);

                                populateFields();

                            }

                        } else {
                            Toast.makeText(editUserAccounts.this, "Something went wrong getting data", Toast.LENGTH_SHORT).show();
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

    // submit form, go back to settings activity
    /* to be implemented

    public void submit(View view){

        String oldFirstName = bundle.getString("firstName");
        String oldLastName = bundle.getString("lastName");
        String oldPW = bundle.getString("password");

        String newFirstName = ((EditText)findViewById(R.id.et_editAcc_fn)).getText().toString();
        String newLastName = ((EditText)findViewById(R.id.et_editAcc_ln)).getText().toString();
        String newPW = ((EditText)findViewById(R.id.et_editAcc_pw)).getText().toString();


        if (newPW.length() < 6){
            Toast.makeText(editUserAccounts.this,"Password must be at least 6 characters",Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(editUserAccounts.this, "Fields successfully updated", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(editUserAccounts.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });

            finish();
        } else {
            Toast.makeText(editUserAccounts.this, "No changes made", Toast.LENGTH_SHORT).show();
            finish();
        }


    }

     */

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}