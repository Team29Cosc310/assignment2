package com.example.javabucksim.listItems;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.BuddhistCalendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.javabucksim.listItems.coldCoffee.ColdCof;
import com.example.javabucksim.listItems.coldDrink.ColdD;
import com.example.javabucksim.listItems.hotCoffee.HotCof;
import com.example.javabucksim.listItems.hotDrink.HotD;
import com.example.javabucksim.MainActivity;
import com.example.javabucksim.listItems.otherDrinks.Other;
import com.example.javabucksim.R;
import com.example.javabucksim.orders.autoOrder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

public class Categories extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mFirebaseAuth;
    int[] productNum = new int[18];
    String[] productName = {"chai", "blondeRoast", "capp", "coffee", "coldbrew", "cups", "darkRoast", "flavour", "juice", "latte", "lids",
            "matcha", "mediumRoast", "milk", "sleeves", "stoppers", "sugar", "tea"};
    ArrayList<String> items = new ArrayList<String>();

    Intent intent;
    Button hotCof, coldCof, hotD, coldD, other, back, check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        intent = new Intent(this, autoOrder.class);
        hotCof = findViewById(R.id.hotCof);
        coldCof = findViewById(R.id.coldCof);
        hotD = findViewById(R.id.hotD);
        coldD = findViewById(R.id.coldD);
        other = findViewById(R.id.other);

        back = findViewById(R.id.backButton);

        check = findViewById(R.id.check);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        hotCof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent hotCof_intent = new Intent(Categories.this, HotCof.class);
                startActivity(hotCof_intent);
            }
        });

        coldCof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent coldCof_intent = new Intent(Categories.this, ColdCof.class);
                startActivity(coldCof_intent);
            }
        });

        hotD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent hotD_intent = new Intent(Categories.this, HotD.class);
                startActivity(hotD_intent);
            }
        });

        coldD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent coldD_intent = new Intent(Categories.this, ColdD.class);
                startActivity(coldD_intent);
            }
        });

        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent other_intent = new Intent(Categories.this, Other.class);
                startActivity(other_intent);
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lowStackWarning();
            }
        });
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

                alert();
            }
        });
    }

    public void alert() {
        Button check = (Button) findViewById(R.id.check);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Categories.this);

                for (int i = 0; i < productNum.length; i++) {
                    if (productNum[i] < 50)
                    {
                        builder.setCancelable(true);
                        builder.setTitle("Low stock warning");
                        builder.setMessage("Please resupply " + productName[i] + ":");
                        //fix
                        items.add(productName[i]);
                        //
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        builder.setPositiveButton("Order now", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                openOrderActivity();
                            }
                        });
                        builder.show();

                        break;
                    }
                }
            }
        });
    }
    public void openOrderActivity()
    {

        //pass to activty
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("items",items);
        intent.putExtras(bundle);
        //
        startActivity(intent);
    }
}