package com.example.javabucksim.listItems.otherDrinks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.javabucksim.R;
import com.example.javabucksim.listItems.Categories;

public class Other extends AppCompatActivity {

    Button back, flavour, espresso, milk, cream, sugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        flavour = findViewById(R.id.flavour);
        espresso = findViewById(R.id.espr);
        milk = findViewById(R.id.milk);
        cream = findViewById(R.id.cream);
        sugar = findViewById(R.id.sugar_1);

        back = findViewById(R.id.backButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(Other.this, Categories.class);
                startActivity(back);
            }
        });

        flavour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent flavour_intent = new Intent(Other.this, Other1.class);
                startActivity(flavour_intent);
            }
        });

        espresso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent espresso_intent = new Intent(Other.this, Other2.class);
                startActivity(espresso_intent);
            }
        });

        milk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent milk_intent = new Intent(Other.this, Other3.class);
                startActivity(milk_intent);
            }
        });

        cream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cream_intent = new Intent(Other.this, Other4.class);
                startActivity(cream_intent);
            }
        });

        sugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sugar_intent = new Intent(Other.this, Other5.class);
                startActivity(sugar_intent);
            }
        });
    }
}