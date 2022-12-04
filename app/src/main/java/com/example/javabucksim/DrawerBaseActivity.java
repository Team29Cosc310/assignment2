package com.example.javabucksim;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.chad.designtoast.DesignToast;
import com.example.javabucksim.listItems.Categories;
import com.example.javabucksim.orders.autoOrder;
import com.example.javabucksim.settings.settingsActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class DrawerBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mFirebaseAuth;

    TextView menuName;
    TextView menuEmail;
    NavigationView navigationView;
    String menuFirstNameString;
    String menuLastNameString;
    String menuEmailString;
    FirebaseUser user;

    @Override
    public void setContentView(View view) {
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer_base, null);
        FrameLayout container = drawerLayout.findViewById(R.id.activityContainer);
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = drawerLayout.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

//        setMenuNameAndEmail();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent intent5 = new Intent(this, MainActivity.class);
                overridePendingTransition(0, 0);
                startActivity(intent5);
                break;
            case R.id.nav_place_order:
                Intent intent = new Intent(this, autoOrder.class);
                overridePendingTransition(0, 0);
                startActivity(intent);
                break;
            case R.id.nav_veiw_items:
                Intent intent1 = new Intent(this, Categories.class);
                overridePendingTransition(0, 0);
                startActivity(intent1);
                break;
            case R.id.nav_profile:
                Intent intent2 = new Intent(this, settingsActivity.class);
                overridePendingTransition(0, 0);
//                intent2.putExtras(bundle);
                startActivity(intent2);
                break;
            case R.id.nav_logout:
//                mFirebaseAuth.signOut();
                Intent intent3 = new Intent(this, MainActivity.class);
                overridePendingTransition(0, 0);
                startActivity(intent3);
//                finish();
                break;
            case R.id.nav_veiw_report:
                Intent intent4 = new Intent(this, reportActivity.class);
                overridePendingTransition(0, 0);
                startActivity(intent4);
                break;
        }
        return true;
    }

    protected void allocateActivityTitle(String titleString) {
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(titleString);
        }
    }

//        void setMenuNameAndEmail() {
//        user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user == null) {
//            DesignToast.makeText(this, "Successfully logged out", DesignToast.LENGTH_SHORT, DesignToast.TYPE_SUCCESS).show();
//        } else {
//            String uid = user.getUid();
//
//
//            DocumentReference documentReference = db.collection("users").document(uid);
//            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
//                @Override
//                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                    menuFirstNameString = value.getString("firstName");
//                    menuLastNameString = value.getString("lastName");
//                    menuEmailString = value.getString("email");
//                    View headView = navigationView.getHeaderView(0);
//                    TextView navUserName = (TextView) headView.findViewById(R.id.menuName);
//                    TextView navUserEmail = (TextView) headView.findViewById(R.id.menuEmail);
//                    navUserName.setText(menuFirstNameString + " " + menuLastNameString);
//                    navUserEmail.setText(menuEmailString);
//                }
//            });
//        }
//    }
}