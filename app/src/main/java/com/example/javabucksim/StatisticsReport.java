package com.example.javabucksim;

import java.io.IOException;

// This class will contain all the statistics values retrieved from Firebase
public class StatisticsReport {
    int numberOfDrinksSold;
    float rateOfOrdersPerHour;
    float totalSalesRevenue;
    float netProfit;
    int numberOfCustomers;
    int numberOfEmployees;
    float employeeWagePerHour;
    float employeeWorkHours;
    float totalEmployeeCosts;

    // TODO: Will add implementation in next stage
    public void saveReport(String fileName) throws IOException {

    }

    public String toString() {
        String str = "Sales and Operations reports\n";
        str += "Number of drinks sold: " + Integer.toString(this.numberOfDrinksSold) + "\n";
        str += "Rate of orders (per hour): " + Float.toString(this.rateOfOrdersPerHour) + "/hr\n";
        str += "Total sales revenue: $" + Float.toString(this.totalSalesRevenue) + "\n";
        str += "Net Profit: $" + Float.toString(this.netProfit) + "\n";
        str += "Number of Customers: " + Integer.toString(this.numberOfCustomers) + "\n";
        str += "Number of Employees: " + Integer.toString(this.numberOfEmployees) + "\n";
        str += "Employee wage per hour: $" + Float.toString(this.employeeWagePerHour) + "/hr\n";
        str += "Employee work hours: " + Float.toString(this.employeeWorkHours) + " hours\n";
        str += "Number of drinks sold: $" + Float.toString(this.totalEmployeeCosts) + "\n";

        return str;
    }
}
