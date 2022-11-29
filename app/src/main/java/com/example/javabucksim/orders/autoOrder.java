package com.example.javabucksim.orders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.javabucksim.MainActivity;
import com.example.javabucksim.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class autoOrder extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mFirebaseAuth;
    RecyclerView recyclerView;
    ArrayList<orderTile> orderItems= new ArrayList<>();
    order_RVAdapter myAdapter;
    Map<String,Object> map = new HashMap<>();
//    Intent intent = getIntent();
//    Bundle bundle = intent.getExtras();
    ArrayList<String> lowitems;
    int[] productNum = new int[18];
    String[] productName = {"chai", "blondeRoast", "capp", "coffee", "coldbrew", "cups", "darkRoast", "flavour", "juice", "latte", "lids",
            "matcha", "mediumRoast", "milk", "sleeves", "stoppers", "sugar", "tea"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autoord);
        //need to get the data from firebase
        mFirebaseAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.orderView);
        lowitems = new ArrayList<>();
        lowStackWarning();
        //setUpOrder();
        myAdapter = new order_RVAdapter(this,orderItems);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();




    }
    public void setUpOrder(){
        //figure out which items to grab
        //lowitems = bundle.getStringArrayList("items");
        ArrayList<Integer> quant = new ArrayList<>();
        ArrayList<String> quantString = new ArrayList<>();
        //lowStackWarning();
        for (int i = 0; i < productNum.length; i++) {
            if (productNum[i] < 50)
            {
                //lowitems.add("test");
                lowitems.add(productName[i]);
                quant.add(productNum[i]+100);
            }}
        //lowitems.add("test");
        //String[] itemquantities = {"300"};// when an item is low calculate the diference between max and current for all items and if it is less than 25% order the approprate ammount of that item
        for(int i=0;i<quant.size();i++)
        {
            quantString.add(quant.get(i).toString());
        }
        //for each low item create a tile for how much needs to be ordered.
        for(int i =0; i< lowitems.size();i++)
        {
            orderItems.add(new orderTile(lowitems.get(i),"100"));
            map.put(lowitems.get(i),"100");
        }
    }
    public void conf(View view)
    {
        String doc = "j9BQe3OtLP6XnUK66MWK";

        //in theory just get from array list
        //add db update functionality, update the prod name with quanitity
        db.collection("Inventory").document(doc).update(map);


        //
        Button order = findViewById(R.id.conf);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(this,"Order Placed", Toast.LENGTH_LONG).show();

    }

    public void back(View view) {
        Button back = findViewById(R.id.backButton);
        finish();
    }

    void lowStackWarning() {
        String doc = "j9BQe3OtLP6XnUK66MWK";

        DocumentReference documentReference = db.collection("Inventory").document(doc);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                productNum[0] = Integer.parseInt(value.getString(productName[0]));
                productNum[1] = Integer.parseInt(value.getString(productName[1]));
                productNum[2] = Integer.parseInt(value.getString(productName[2]));
                productNum[3] = Integer.parseInt(value.getString(productName[3]));
                productNum[4] = Integer.parseInt(value.getString(productName[4]));
                productNum[5] = Integer.parseInt(value.getString(productName[5]));
                productNum[6] = Integer.parseInt(value.getString(productName[6]));
                productNum[7] = Integer.parseInt(value.getString(productName[7]));
                productNum[8] = Integer.parseInt(value.getString(productName[8]));
                productNum[9] = Integer.parseInt(value.getString(productName[9]));
                productNum[10] = Integer.parseInt(value.getString(productName[10]));
                productNum[11] = Integer.parseInt(value.getString(productName[11]));
                productNum[12] = Integer.parseInt(value.getString(productName[12]));
                productNum[13] = Integer.parseInt(value.getString(productName[13]));
                productNum[14] = Integer.parseInt(value.getString(productName[14]));
                productNum[15] = Integer.parseInt(value.getString(productName[15]));
                productNum[16] = Integer.parseInt(value.getString(productName[16]));
                productNum[17] = Integer.parseInt(value.getString(productName[17]));

                setUpOrder();
            }
        });
    }

}
