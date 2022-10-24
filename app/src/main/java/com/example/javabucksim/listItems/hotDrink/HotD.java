package com.example.javabucksim.listItems.hotDrink;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.javabucksim.R;
import com.example.javabucksim.listItems.Categories;

public class HotD extends AppCompatActivity {

    Button back, seasonal, tea, hotchoc, chai, matcha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_d);

        seasonal = findViewById(R.id.seasonalD);
        tea = findViewById(R.id.hotTea);
        hotchoc = findViewById(R.id.hotChoc);
        chai = findViewById(R.id.chaiLatte);
        matcha = findViewById(R.id.matchaLatte);

        back = findViewById(R.id.backButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(HotD.this, Categories.class);
                startActivity(back);
            }
        });

        seasonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent seasonal_intent = new Intent(HotD.this, HotD1.class);
                startActivity(seasonal_intent);

            }
        });

        tea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tea_intent = new Intent(HotD.this, HotD2.class);
                startActivity(tea_intent);

            }
        });

        hotchoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent HC_intent = new Intent(HotD.this, HotD3.class);
                startActivity(HC_intent);

            }
        });

        chai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chai_intent = new Intent(HotD.this, HotD4.class);
                startActivity(chai_intent);

            }
        });

        matcha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent matcha_intent = new Intent(HotD.this, HotD5.class);
                startActivity(matcha_intent);

            }
        });
    }
}