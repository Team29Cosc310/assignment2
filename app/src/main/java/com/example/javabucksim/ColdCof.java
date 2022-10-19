package com.example.javabucksim;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ColdCof extends AppCompatActivity {

    Button back, seasonal, coffee, coldBrew, latte, capp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cold_cof);

        seasonal = findViewById(R.id.iced_seasonal);
        coffee = findViewById(R.id.iced_coffee);
        coldBrew = findViewById(R.id.iced_cB);
        latte = findViewById(R.id.iced_latte);
        capp = findViewById(R.id.iced_cap);

        back = findViewById(R.id.backButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(ColdCof.this, Categories.class);
                startActivity(back);
            }
        });

        seasonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent seasonal_intent = new Intent(ColdCof.this, ColdCof1.class);
                startActivity(seasonal_intent);
            }
        });

        coffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent icedCof_intent = new Intent(ColdCof.this, ColdCof2.class);
                startActivity(icedCof_intent);
            }
        });

        latte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent icedLatte_intent = new Intent(ColdCof.this, ColdCof3.class);
                startActivity(icedLatte_intent);
            }
        });

        coldBrew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent coldB_intent = new Intent(ColdCof.this, ColdCof4.class);
                startActivity(coldB_intent);
            }
        });

        capp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iced_capp_intent = new Intent(ColdCof.this, ColdCof5.class);
                startActivity(iced_capp_intent);
            }
        });
    }
}