package com.example.javabucksim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    StatisticsReport statReport = new StatisticsReport();
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        back = findViewById(R.id.backButton);

        Intent intent = getIntent();

        mFirebaseAuth = FirebaseAuth.getInstance();

        getReportFromDB();

        // Button will be re-enabled in next assignment
        Button saveReportButton = (Button) findViewById(R.id.buttonSaveReport);
        saveReportButton.setVisibility(View.GONE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(reportActivity.this, MainActivity.class);
                startActivity(back);
            }
        });
    }

    // Read from Firebase, get statistics values, and update the StatisticsReport Object
    private void getReportFromDB() {

        String doc = "AYtNyBDk7j01DYrlZFIV";
        DocumentReference docRef = db.collection("statistics").document(doc);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Object> stats = document.getData();
                        statReport.numberOfDrinksSold = Integer.parseInt(stats.get("numberOfDrinksSold").toString());
                        statReport.rateOfOrdersPerHour = Float.parseFloat(stats.get("rateOfOrders").toString());
                        statReport.totalSalesRevenue = Float.parseFloat(stats.get("totalSalesRevenue").toString());
                        statReport.netProfit = Float.parseFloat(stats.get("netProfit").toString());
                        statReport.numberOfCustomers = Integer.parseInt(stats.get("numberOfCustomers").toString());
                        statReport.numberOfEmployees = Integer.parseInt(stats.get("numberOfEmployees").toString());
                        statReport.employeeWagePerHour = Float.parseFloat(stats.get("employeeWagesPerHour").toString());
                        statReport.employeeWorkHours = Float.parseFloat(stats.get("employeeHours").toString());
                        statReport.totalEmployeeCosts = Float.parseFloat(stats.get("totalEmployeeCosts").toString());
                        updateReportTextView();
                    }
                    else {

                    }
                }
                else {
                    Toast.makeText(reportActivity.this, "Error getting reports", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // Update the TextViews to show the statistics
    private void updateReportTextView() {
        TextView numOfDrinksSold = (TextView) findViewById(R.id.textViewNumberOfDrinksSold);
        TextView rateOfOrders = (TextView) findViewById(R.id.textViewRateOfOrders);
        TextView totalSalesRevenue = (TextView) findViewById(R.id.textViewTotalSalesRevenue);
        TextView netProfit = (TextView) findViewById(R.id.textViewNetProfit);
        TextView numOfCustomers = (TextView) findViewById(R.id.textViewNumberOfCustomers);
        TextView numOfEmployees = (TextView) findViewById(R.id.textViewNumberOfEmployees);
        TextView employeeWagePerHour = (TextView) findViewById(R.id.textViewEmployeeWagePerHour);
        TextView employeeWorkHours = (TextView) findViewById(R.id.textViewEmployeeWorkHours);
        TextView totalEmployeeCosts = (TextView) findViewById(R.id.textViewTotalEmployeeCosts);


        numOfDrinksSold.setText("Number of Drinks sold: " + statReport.numberOfDrinksSold);
        rateOfOrders.setText("Rate of Orders (per hour): " + statReport.rateOfOrdersPerHour);
        totalSalesRevenue.setText("Total Sales Revenue: " + statReport.totalSalesRevenue);
        netProfit.setText("Net Profit: " + statReport.netProfit);
        numOfCustomers.setText("Number of Customers: " + statReport.numberOfCustomers);
        numOfEmployees.setText("Number of Employees: " + statReport.numberOfEmployees);
        employeeWagePerHour.setText("Employee wage per hour: " + statReport.employeeWagePerHour);
        employeeWorkHours.setText("Employee work hours: " + statReport.employeeWorkHours);
        totalEmployeeCosts.setText("Total employee costs: " + statReport.totalEmployeeCosts);

    }
}