package com.example.javabucksim.settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.chad.designtoast.DesignToast;
import com.example.javabucksim.R;
import com.example.javabucksim.login.forgotPassword;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class editUserAccounts extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    Bundle bundle;
    Intent intent;
    String userId;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_accounts);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        intent = getIntent();
        bundle = new Bundle();

        back = findViewById(R.id.backButton);

        getData();
        setupSpinner();
        populateFields();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void setupSpinner(){

        String role = bundle.getString("role");

        Spinner spinner = (Spinner) findViewById(R.id.edit_user_role_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.role_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        if (role != null){
            int spinnerPos = adapter.getPosition(role);
            spinner.setSelection(spinnerPos);
        }

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

                                String oldFirstName = updatedData.get("firstName").toString();
                                String oldLastName = updatedData.get("lastName").toString();
                                String oldEmail = updatedData.get("email").toString();
                                String oldPassword = updatedData.get("password").toString();
                                String oldRole = updatedData.get("role").toString();

                                bundle.putString("firstName", oldFirstName);
                                bundle.putString("lastName", oldLastName);
                                bundle.putString("email", oldEmail);
                                bundle.putString("password", oldPassword);
                                bundle.putString("role", oldRole);

                                populateFields();
                                setupSpinner();

                            }

                        } else {
                            DesignToast.makeText(editUserAccounts.this, "Something went wrong getting data", DesignToast.LENGTH_SHORT, DesignToast.TYPE_ERROR).show();
                        }



            }
        });

    }

    private void populateFields(){

        String firstName = bundle.getString("firstName");
        String lastName = bundle.getString("lastName");
        String email = bundle.getString("email");
        //String pw = bundle.getString("password");

        EditText fn = findViewById(R.id.et_editUsr_fn);
        EditText ln = findViewById(R.id.et_editUsr_ln);
        EditText emailField = findViewById(R.id.et_editUsr_email);
        //EditText password = findViewById(R.id.et_editUsr_pw);

        fn.setText(firstName);
        ln.setText(lastName);
        emailField.setText(email);
        //password.setText(pw);

    }

    // submit form, go back to settings activity

    public void submit(View view){

        String oldFirstName = bundle.getString("firstName");
        String oldLastName = bundle.getString("lastName");
        String oldRole = bundle.getString("role");
        String emailAddress = bundle.getString("email");

        String newFirstName = ((EditText)findViewById(R.id.et_editUsr_fn)).getText().toString();
        String newLastName = ((EditText)findViewById(R.id.et_editUsr_ln)).getText().toString();
        String newRole = ((Spinner)findViewById(R.id.edit_user_role_spinner)).getSelectedItem().toString();

        CheckBox resetEmailCB = findViewById(R.id.edit_user_checkBox);

        if (resetEmailCB.isChecked()){

            mAuth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        DesignToast.makeText(editUserAccounts.this, "Reset password email sent", DesignToast.LENGTH_SHORT, DesignToast.TYPE_SUCCESS).show();
                        finish();
                    }
                }
            });

        }


        if ((!newFirstName.equals(oldFirstName)) || (!newLastName.equals(oldLastName)) || (!newRole.equals(oldRole)) ){

            HashMap<String, Object> updateFields = new HashMap<>();

            if (newFirstName != oldFirstName){
                updateFields.put("firstName", newFirstName);
            }
            if (newLastName != oldLastName){
                updateFields.put("firstName", newFirstName);
            }
            if (newRole != oldRole){
                updateFields.put("role", newRole);
            }


            db.collection("users").document(userId).update(updateFields).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    DesignToast.makeText(editUserAccounts.this, "Fields successfully updated", DesignToast.LENGTH_SHORT, DesignToast.TYPE_SUCCESS).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    DesignToast.makeText(editUserAccounts.this, "Something went wrong", DesignToast.LENGTH_SHORT, DesignToast.TYPE_ERROR).show();
                }
            });

            finish();
        } else {
            DesignToast designToast = DesignToast.makeText(this, "No changes made");
            designToast.show();
            finish();
        }


    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}