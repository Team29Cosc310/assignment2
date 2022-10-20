package com.example.javabucksim;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class reportActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Intent intent = getIntent();

        mFirebaseAuth = FirebaseAuth.getInstance();


        TextView numOfDrinksSold = (TextView) findViewById(R.id.textViewNumberOfDrinksSold);
        TextView rateOfOrders = (TextView) findViewById(R.id.textViewRateOfOrders);
        TextView totalSalesRevenue = (TextView) findViewById(R.id.textViewTotalSalesRevenue);
        TextView netProfit = (TextView) findViewById(R.id.textViewNetProfit);
        TextView numOfCustomers = (TextView) findViewById(R.id.textViewNumberOfCustomers);
        TextView numOfEmployees = (TextView) findViewById(R.id.textViewNumberOfEmployees);
        TextView employeeWagePerHour = (TextView) findViewById(R.id.textViewEmployeeWagePerHour);
        TextView employeeWorkHours = (TextView) findViewById(R.id.textViewEmployeeWorkHours);
        TextView totalEmployeeCosts = (TextView) findViewById(R.id.textViewTotalEmployeeCosts);

        getReportFromDB();
    }

    private void getReportFromDB() {

        TextView numOfDrinksSold = (TextView) findViewById(R.id.textViewNumberOfDrinksSold);
        TextView rateOfOrders = (TextView) findViewById(R.id.textViewRateOfOrders);
        TextView totalSalesRevenue = (TextView) findViewById(R.id.textViewTotalSalesRevenue);
        TextView netProfit = (TextView) findViewById(R.id.textViewNetProfit);
        TextView numOfCustomers = (TextView) findViewById(R.id.textViewNumberOfCustomers);
        TextView numOfEmployees = (TextView) findViewById(R.id.textViewNumberOfEmployees);
        TextView employeeWagePerHour = (TextView) findViewById(R.id.textViewEmployeeWagePerHour);
        TextView employeeWorkHours = (TextView) findViewById(R.id.textViewEmployeeWorkHours);
        TextView totalEmployeeCosts = (TextView) findViewById(R.id.textViewTotalEmployeeCosts);

        String doc = "AYtNyBDk7j01DYrlZFIV";
        DocumentReference docRef = db.collection("statistics").document(doc);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Object> stats = document.getData();
                        numOfDrinksSold.setText("Number of Drinks sold: " + stats.get("numberOfDrinksSold").toString());
                        rateOfOrders.setText("Rate of Orders (per hour): " + stats.get("rateOfOrders").toString());
                        totalSalesRevenue.setText("Total Sales Revenue: " + stats.get("totalSalesRevenue").toString());
                        netProfit.setText("Net Profit: " + stats.get("netProfit").toString());
                        numOfCustomers.setText("Number of Customers: " + stats.get("numberOfCustomers").toString());
                        numOfEmployees.setText("Number of Employees: " + stats.get("numberOfEmployees").toString());
                        employeeWagePerHour.setText("Employee wage per hour: " + stats.get("employeeWagesPerHour").toString());
                        employeeWorkHours.setText("Employee work hours: " + stats.get("employeeHours").toString());
                        totalEmployeeCosts.setText("Total employee costs: " + stats.get("totalEmployeeCosts").toString());
                    }
                    else {

                    }
                }
                else {
                    Toast.makeText(reportActivity.this, "Error getting reports", Toast.LENGTH_LONG).show();
                    numOfDrinksSold.setText("Error:" + task.getException());
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
}