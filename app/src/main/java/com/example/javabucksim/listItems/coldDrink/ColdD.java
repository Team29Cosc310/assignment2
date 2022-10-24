package com.example.javabucksim.listItems.coldDrink;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.javabucksim.R;
import com.example.javabucksim.listItems.Categories;

public class ColdD extends AppCompatActivity {

    Button back, seasonal, tea, chai, matcha, juice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cold_d);

        seasonal = findViewById(R.id.iced_seasonalD);
        tea = findViewById(R.id.iced_tea);
        chai = findViewById(R.id.icedChai);
        matcha = findViewById(R.id.icedMatcha);
        juice = findViewById(R.id.juice);


        back = findViewById(R.id.backButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(ColdD.this, Categories.class);
                startActivity(back);
            }
        });

        seasonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent seasonal_intent = new Intent(ColdD.this, ColdD1.class);
                startActivity(seasonal_intent);

            }
        });

        tea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tea_intent = new Intent(ColdD.this, ColdD2.class);
                startActivity(tea_intent);

            }
        });

        chai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chai_intent = new Intent(ColdD.this, ColdD3.class);
                startActivity(chai_intent);

            }
        });

        matcha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent matcha_intent = new Intent(ColdD.this, ColdD4.class);
                startActivity(matcha_intent);

            }
        });

        juice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent juice_intent = new Intent(ColdD.this, ColdD5.class);
                startActivity(juice_intent);

            }
        });
    }
}