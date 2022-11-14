package com.example.javabucksim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.designtoast.DesignToast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.pdmodel.PDPage;
import com.tom_roush.pdfbox.pdmodel.PDPageContentStream;
import com.tom_roush.pdfbox.pdmodel.font.PDFont;
import com.tom_roush.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
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

        Button saveReportButton = (Button) findViewById(R.id.buttonSaveReport);
        saveReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "Saving report", Toast.LENGTH_SHORT).show();
                DesignToast.makeText(getApplicationContext(), "Saving report", DesignToast.LENGTH_SHORT, DesignToast.TYPE_INFO).show();
                saveReport();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
                    DesignToast.makeText(reportActivity.this, "Error getting reports", DesignToast.LENGTH_SHORT, DesignToast.TYPE_ERROR).show();
                    //Toast.makeText(reportActivity.this, "Error getting reports", Toast.LENGTH_LONG).show();
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

    private void saveReport() {
        PDFBoxResourceLoader.init(getApplicationContext());
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDFont font = PDType1Font.HELVETICA;
        PDPageContentStream contentStream;

        try{
            contentStream = new PDPageContentStream(document, page);
            contentStream.beginText();

            contentStream.setLeading(36.0f);
            contentStream.setFont(font, 24);
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText("javabucksIM Sales and Operations Report");
            contentStream.newLine();
            contentStream.setNonStrokingColor(0, 106, 78);
            contentStream.setFont(font, 24);
            contentStream.showText("Number of drinks sold: " + statReport.numberOfDrinksSold);
            contentStream.newLine();
            contentStream.showText("Rate of orders per hour: " + statReport.rateOfOrdersPerHour + "/hr");
            contentStream.newLine();
            contentStream.showText("Total sales revenue: $" + statReport.totalSalesRevenue);
            contentStream.newLine();
            contentStream.showText("Net Profit: $" + statReport.netProfit);
            contentStream.newLine();
            contentStream.showText("Number of customers: " + statReport.numberOfCustomers);
            contentStream.newLine();
            contentStream.showText("Number of employees: " + statReport.numberOfEmployees);
            contentStream.newLine();
            contentStream.showText("Employee wage per hour: " + statReport.employeeWagePerHour + "/hr");
            contentStream.newLine();
            contentStream.showText("Employee work hours: " + statReport.employeeWorkHours + " hours");
            contentStream.newLine();
            contentStream.showText("Total employee costs: $" + statReport.totalEmployeeCosts);
            contentStream.endText();

            contentStream.close();

            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString() + "/javabucksIM_report.pdf";
            document.save(path);
            document.close();
            DesignToast.makeText(getApplicationContext(), "Saved to " + path, DesignToast.LENGTH_SHORT, DesignToast.TYPE_SUCCESS).show();
            //Toast.makeText(getApplicationContext(), "Saved to " + path, Toast.LENGTH_SHORT).show();
        }
        catch (IOException e) {
            DesignToast.makeText(getApplicationContext(),"Error saving report",DesignToast.LENGTH_SHORT, DesignToast.TYPE_ERROR).show();
            //Toast.makeText(getApplicationContext(),"Error saving report",Toast.LENGTH_SHORT).show();
        }
    }
}