package com.example.javabucksim;


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

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Categories extends AppCompatActivity {
//        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mFirebaseAuth;
    int[] productNum = new int[18];
    String[] productName = {"chai", "blondeRoast", "capp", "coffee", "coldbrew", "cups", "darkRoast", "flavour", "juice", "latte", "lids",
            "matcha", "mediumRoast", "milk", "sleeves", "stoppers", "sugar", "tea"};

//    //menu
//    DrawerLayout drawerLayout;
//    NavigationView navigationView;
//    Toolbar toolbar;
//    //menuName & email
//    TextView menuName;
//    TextView menuEmail;
//    String menuFirstNameString;
//    String menuLastNameString;
//    String menuEmailString;
//    FirebaseUser user;


    Button hotCof, coldCof, hotD, coldD, other, back, check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

//        //menu
//        drawerLayout = findViewById(R.id.drawer_layout);
//        navigationView = findViewById(R.id.nav_view);
//        toolbar = findViewById(R.id.toolbar);
//
//        //toolbar
//        setSupportActionBar(toolbar);
//        //nav_drawer
//        navigationView.bringToFront();
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
//        drawerLayout.addDrawerListener(toggle);
//        drawerLayout.setScrimColor(Color.parseColor("#32000000"));
//        toggle.syncState();
//        navigationView.setNavigationItemSelectedListener(this);
//        //headerInfo
//        setMenuNameAndEmail();


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
                Intent back = new Intent(Categories.this, MainActivity.class);
                startActivity(back);
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
        Intent intent = new Intent(this,autoOrder.class);
        startActivity(intent);
    }

//    @Override
//    public void onBackPressed() {
//
//        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            drawerLayout.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
//
//    //menuMethods
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.nav_home:
//                break;
//            case R.id.nav_place_order:
//                Intent intent = new Intent(Categories.this,autoOrder.class);
//                startActivity(intent);
//                break;
//            case R.id.nav_veiw_items:
//                Intent intent1 = new Intent(Categories.this,Categories.class);
//                startActivity(intent1);
//                break;
//            case R.id.nav_profile:
//                Intent intent2 = new Intent(Categories.this,settingsActivity.class);
//                startActivity(intent2);
//                break;
//            case R.id.nav_logout:
//                mFirebaseAuth.signOut();
//                Intent intent3 = new Intent(Categories.this, MainActivity.class);
//                startActivity(intent3);
//                finish();
//                break;
//            case R.id.nav_veiw_report:
//                Intent intent4 = new Intent(Categories.this, reportActivity.class);
//                startActivity(intent4);
//                break;
//        }
//        return true;
//    }
//    void setMenuNameAndEmail() {
//        user = FirebaseAuth.getInstance().getCurrentUser();
//        String uid = user.getUid();
//
//
//
//        DocumentReference documentReference = db.collection("users").document(uid);
//        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                menuFirstNameString = value.getString("firstName");
//                menuLastNameString = value.getString("lastName");
//                menuEmailString = value.getString("email");
//                View headView = navigationView.getHeaderView(0);
//                TextView navUserName = (TextView) headView.findViewById(R.id.menuName);
//                TextView navUserEmail = (TextView) headView.findViewById(R.id.menuEmail);
//                navUserName.setText(menuFirstNameString + " " + menuLastNameString);
//                navUserEmail.setText(menuEmailString);
//            }
//        });
//    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}