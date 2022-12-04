package com.example.javabucksim.listItems;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.javabucksim.DrawerBaseActivity;
import com.example.javabucksim.databinding.ActivityCategoriesBinding;
import com.google.android.material.navigation.NavigationView;

import com.example.javabucksim.R;
import com.example.javabucksim.orders.autoOrder;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Categories extends DrawerBaseActivity {
//        implements NavigationView.OnNavigationItemSelectedListener {

    ActivityCategoriesBinding activityCategoriesBinding;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mFirebaseAuth;
    int[] productNum = new int[18];
    String[] productName = {"chai", "blondeRoast", "capp", "coffee", "coldbrew", "cups", "darkRoast", "flavour", "juice", "latte", "lids",
            "matcha", "mediumRoast", "milk", "sleeves", "stoppers", "sugar", "tea"};





    Button hotCof, coldCof, hotD, coldD, other, back, check;
    String choice1, choice2, choice3, choice4, choice5, category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCategoriesBinding = ActivityCategoriesBinding.inflate(getLayoutInflater());
        setContentView(activityCategoriesBinding.getRoot());



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
                category = "Hot Coffees";
                choice1 = "Pumpkin Spice Latte";
                choice2 = "Dark Roast";
                choice3 = "Medium Roast";
                choice4 = "Blonde Roast";
                choice5 = "Cappuccino";
                Intent intent = new Intent(Categories.this, Choices.class);
                intent.putExtra("category", category);
                intent.putExtra("choice1", choice1);
                intent.putExtra("choice2", choice2);
                intent.putExtra("choice3", choice3);
                intent.putExtra("choice4", choice4);
                intent.putExtra("choice5", choice5);
                startActivity(intent);
            }
        });

        coldCof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "Cold Coffees";
                choice1 = "Iced Pumpkin Spice Latte";
                choice2 = "Iced Coffee";
                choice3 = "Iced Latte";
                choice4 = "Cold Brew";
                choice5 = "Iced Cappuccino";
                Intent intent = new Intent(Categories.this, Choices.class);
                Bundle bundle = new Bundle();
                bundle.putString("category", category);
                bundle.putString("choice1", choice1);
                bundle.putString("choice2", choice2);
                bundle.putString("choice3", choice3);
                bundle.putString("choice4", choice4);
                bundle.putString("choice5", choice5);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        hotD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "Hot Drinks";
                choice1 = "Pumpkin Chai Latte";
                choice2 = "Tea";
                choice3 = "Hot Chocolate";
                choice4 = "Chai Latte";
                choice5 = "Matcha Latte";
                Intent intent = new Intent(Categories.this, Choices.class);
                Bundle bundle = new Bundle();
                bundle.putString("category", category);
                bundle.putString("choice1", choice1);
                bundle.putString("choice2", choice2);
                bundle.putString("choice3", choice3);
                bundle.putString("choice4", choice4);
                bundle.putString("choice5", choice5);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        coldD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "Cold Drinks";
                choice1 = "Iced Pumpkin Chai Latte";
                choice2 = "Iced Tea";
                choice3 = "Iced Chai Latte";
                choice4 = "Iced Matcha Latte";
                choice5 = "Juice";
                Intent intent = new Intent(Categories.this, Choices.class);
                Bundle bundle = new Bundle();
                bundle.putString("category", category);
                bundle.putString("choice1", choice1);
                bundle.putString("choice2", choice2);
                bundle.putString("choice3", choice3);
                bundle.putString("choice4", choice4);
                bundle.putString("choice5", choice5);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "Other";
                choice1 = "Flavour Shots";
                choice2 = "Espresso";
                choice3 = "Milk";
                choice4 = "Cream";
                choice5 = "Sugar";
                Intent intent = new Intent(Categories.this, Choices.class);
                Bundle bundle = new Bundle();
                bundle.putString("category", category);
                bundle.putString("choice1", choice1);
                bundle.putString("choice2", choice2);
                bundle.putString("choice3", choice3);
                bundle.putString("choice4", choice4);
                bundle.putString("choice5", choice5);
                intent.putExtras(bundle);
                startActivity(intent);
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
        Intent intent = new Intent(this, autoOrder.class);
        startActivity(intent);
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}