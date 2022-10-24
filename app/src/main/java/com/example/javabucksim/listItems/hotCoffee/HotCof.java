package com.example.javabucksim.listItems.hotCoffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.javabucksim.R;
import com.example.javabucksim.listItems.Categories;

public class HotCof extends AppCompatActivity {

    Button back, seasonal, darkRoast, mediumRoast, blondeRoast, latte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_cof);

        seasonal = findViewById(R.id.seasonal);
        darkRoast = findViewById(R.id.darkRoast);
        mediumRoast = findViewById(R.id.mediumRoast);
        blondeRoast = findViewById(R.id.blondeRoast);
        latte = findViewById(R.id.latte);

        back = findViewById(R.id.backButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        seasonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent seasonal_intent = new Intent(HotCof.this, HotCof1.class);
                startActivity(seasonal_intent);
            }
        });

        darkRoast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent darkR_intent = new Intent(HotCof.this, HotCof2.class);
                startActivity(darkR_intent);
            }
        });

        mediumRoast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mediumR_intent = new Intent(HotCof.this, HotCof3.class);
                startActivity(mediumR_intent);
            }
        });

        blondeRoast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent blondeR_intent = new Intent(HotCof.this, HotCof4.class);
                startActivity(blondeR_intent);
            }
        });

        latte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent latte_intent = new Intent(HotCof.this, HotCof5.class);
                startActivity(latte_intent);
            }
        });
    }
}