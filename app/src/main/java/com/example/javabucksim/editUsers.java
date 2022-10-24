package com.example.javabucksim;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class editUsers extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<User> userArrayList;
    UserRVAdapter myAdapter;
    FirebaseFirestore db;
    UserRVAdapter.OnItemClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_users);

        // can start progress bar here
        ProgressBar progBar = findViewById(R.id.idProgressBar);
        progBar.setVisibility(View.GONE);

        // 13:28 video
        recyclerView = findViewById(R.id.idRVUsers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        userArrayList = new ArrayList<User>();
        setOnClickListener();
        myAdapter = new UserRVAdapter(editUsers.this, userArrayList, listener);

        recyclerView.setAdapter(myAdapter);
        

    }

    private void setOnClickListener() {

        listener = new UserRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(getApplicationContext(), editUserAccounts.class);
                intent.putExtra("email", userArrayList.get(position).getEmail());
                startActivity(intent);

            }
        };

    }

    public void getAllUsers(View view){

        ProgressBar progBar = findViewById(R.id.idProgressBar);
        progBar.setVisibility(View.VISIBLE);

        db.collection("users").orderBy("firstName", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null){

                    // end progress bar here
                    ProgressBar progBar = findViewById(R.id.idProgressBar);
                    progBar.setVisibility(View.GONE);

                    Log.e("Firestore error", error.getMessage());
                    return;
                }

                for (DocumentChange dc : value.getDocumentChanges() ){

                    // get document if data is added may need to change
                    if (dc.getType() == DocumentChange.Type.ADDED){

                        userArrayList.add(dc.getDocument().toObject(User.class));

                    }

                    myAdapter.notifyDataSetChanged();
                    // end progress bar here
                    ProgressBar progBar = findViewById(R.id.idProgressBar);
                    progBar.setVisibility(View.GONE);

                }

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void submit(View view){
        finish();
    }

}