package com.example.javabucksim;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.chad.designtoast.DesignToast;
import com.example.javabucksim.listItems.Categories;
import com.example.javabucksim.login.loginActivity;
import com.example.javabucksim.orders.autoOrder;
import com.example.javabucksim.settings.settingsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Map;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mFirebaseAuth;
    private String role;

    //menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    //menuName & email
    TextView menuName;
    TextView menuEmail;
    String menuFirstNameString;
    String menuLastNameString;
    String menuEmailString;
    FirebaseUser user;


    Bundle bundle = new Bundle();
    Button items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //menu
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navView);
        toolbar = findViewById(R.id.toolbar);

        //toolbar
        setSupportActionBar(toolbar);
        //nav_drawer
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        drawerLayout.setScrimColor(Color.parseColor("#32000000"));
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        //headerInfo
        setMenuNameAndEmail();



        items = findViewById(R.id.items);

        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();

        ProgressBar progBar = findViewById(R.id.indeterminateBar);
        progBar.setVisibility(View.GONE);

        if (mFirebaseUser != null) {
            // user is logged in
            try {
                // get user info from firebase
                getInfo();
            } catch (Exception e) {
                //if it fails show error
                TextView result = findViewById(R.id.textViewResult);
                result.setText(e.toString());
            }
        }

        setUpSettings();
        setUpReports();
        setUpLogout();
        setUpItemDetails();

    }

    // check if user is logged in
    // if not logged in, go to login screen
    @Override
    protected void onStart(){
        super.onStart();

        showLoading();

        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser != null){
            // user is logged in

        } else {
            startActivity(new Intent(this, loginActivity.class));
            finish();
        }
    }

    // does nothing extra currently
    @Override
    protected void onResume() {
        super.onResume();

        endLoading();

    }

    // show loading circle, hide layout
    private void showLoading(){

        ProgressBar progBar = findViewById(R.id.indeterminateBar);
        ConstraintLayout layout = findViewById(R.id.main_layout);

        layout.setVisibility(View.GONE);
        progBar.setVisibility(View.VISIBLE);

    }

    // end loading circle, show layout
    private void endLoading(){

        ProgressBar progBar = findViewById(R.id.indeterminateBar);
        ConstraintLayout layout = findViewById(R.id.main_layout);

        progBar.setVisibility(View.GONE);
        layout.setVisibility(View.VISIBLE);

    }

    // settings on click button
    public void setUpSettings(){

        Button logoutBut = findViewById(R.id.settingsButton);
        logoutBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, settingsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    // reports on click button
    public void setUpReports() {
        Button reportButton = findViewById(R.id.reportButton);
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, reportActivity.class);
                startActivity(intent);
            }
        });
    }

    // logout user and end activity
    private void setUpLogout(){

        Button logoutBut = findViewById(R.id.logoutButton);
        logoutBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFirebaseAuth.signOut();
                DesignToast.makeText(MainActivity.this, "Successfully logged out", DesignToast.LENGTH_SHORT, DesignToast.TYPE_SUCCESS).show();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    // method that gets user info from database based on their login user id
    public void getInfo(){

        String doc = mFirebaseAuth.getUid();

        DocumentReference docRef = db.collection("users").document(doc);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Object> userInfo = document.getData();
                        setInfo(userInfo);
                        setUpView();
                    } else {
                        // do stuff
                    }
                } else {

                    DesignToast.makeText(MainActivity.this, "Error", DesignToast.LENGTH_SHORT, DesignToast.TYPE_SUCCESS).show();
                }

            }
        });

    }

    //changes settings text based on role
    public void setUpView(){

        Button settingsBut = findViewById(R.id.settingsButton);
        settingsBut.setText(role + " Settings");
    }

    public void placeOrder(View view)
    {
        Button order = findViewById(R.id.place_order);
        Intent intent = new Intent(this, autoOrder.class);
        startActivity(intent);
    }



    // puts user data in bundle
    private void setInfo(Map<String, Object> fields){


        String firstName = fields.get("firstName").toString();
        String lastName = fields.get("lastName").toString();
        String email = fields.get("email").toString();
        String pw = fields.get("password").toString();
        role = fields.get("role").toString();

        bundle.putString("firstName", firstName);
        bundle.putString("lastName", lastName);
        bundle.putString("email", email);
        bundle.putString("password", pw);
        bundle.putString("role", role);

    }

    private void setUpItemDetails(){
        items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Categories.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //menuMethods
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_home:
                break;
            case R.id.nav_place_order:
                Intent intent = new Intent(MainActivity.this,autoOrder.class);
                startActivity(intent);
                break;
            case R.id.nav_veiw_items:
                Intent intent1 = new Intent(MainActivity.this,Categories.class);
                startActivity(intent1);
                break;
            case R.id.nav_profile:
                Intent intent2 = new Intent(MainActivity.this,settingsActivity.class);
                startActivity(intent2);
                break;
            case R.id.nav_logout:
                mFirebaseAuth.signOut();
                Intent intent3 = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent3);
                finish();
                break;
            case R.id.nav_veiw_report:
                Intent intent4 = new Intent(MainActivity.this, reportActivity.class);
                startActivity(intent4);
                break;
        }
        return true;
    }
    void setMenuNameAndEmail() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            DesignToast.makeText(MainActivity.this, "Successfully logged out", DesignToast.LENGTH_SHORT, DesignToast.TYPE_SUCCESS).show();
        } else {
            String uid = user.getUid();


            DocumentReference documentReference = db.collection("users").document(uid);
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    menuFirstNameString = value.getString("firstName");
                    menuLastNameString = value.getString("lastName");
                    menuEmailString = value.getString("email");
                    View headView = navigationView.getHeaderView(0);
                    TextView navUserName = (TextView) headView.findViewById(R.id.menuName);
                    TextView navUserEmail = (TextView) headView.findViewById(R.id.menuEmail);
                    navUserName.setText(menuFirstNameString + " " + menuLastNameString);
                    navUserEmail.setText(menuEmailString);
                }
            });
        }
    }
}