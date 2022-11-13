package com.example.javabucksim;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class autoOrder extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    RecyclerView recyclerView;
    ArrayList<orderTile> orderItems= new ArrayList<>();
    order_RVAdapter myAdapter;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autoord);
        //need to get the data from firebase
        mFirebaseAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.orderView);
        setUpOrder();
        myAdapter = new order_RVAdapter(this,orderItems);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();



    }
    public void setUpOrder(){
        //figure out which items to grab


        //grab names and quant from db?
        String[] itemNames ={"tea","coffee", "ice tea"};
        String[] itemquantities = {"300","200","100"};// when an item is low calculate the diference between max and current for all items and if it is less than 25% order the approprate ammount of that item
        //create order tiles
        orderTile tile ;
        for(int i =0; i< itemNames.length;i++)
        {

            orderItems.add(new orderTile(itemNames[i], itemquantities[i]));
        }
    }
    public void conf(View view)
    {
        Button order = findViewById(R.id.conf);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(this,"Order Placed", Toast.LENGTH_LONG).show();
    }

    public void back(View view) {
        Button back = findViewById(R.id.backButton);
        Intent intent = new Intent(this, Categories.class);
        startActivity(intent);
    }

}
