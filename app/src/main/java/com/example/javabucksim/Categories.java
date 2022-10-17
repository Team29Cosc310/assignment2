package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Categories extends AppCompatActivity {

    Button hotCof, coldCof, hotD, coldD, other, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        hotCof = findViewById(R.id.hotCof);
        coldCof = findViewById(R.id.coldCof);
        hotD = findViewById(R.id.hotD);
        coldD = findViewById(R.id.coldD);
        other = findViewById(R.id.other);

        back = findViewById(R.id.backButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(Categories.this, MainActivity.class);
                startActivity(back);
            }
        });

        hotCof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent hotCof_intent = new Intent(Categories.this, HotCof.class);
                startActivity(hotCof_intent);
            }
        });

        coldCof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent coldCof_intent = new Intent(Categories.this, ColdCof.class);
                startActivity(coldCof_intent);
            }
        });

        hotD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent hotD_intent = new Intent(Categories.this, HotD.class);
                startActivity(hotD_intent);
            }
        });

        coldD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent coldD_intent = new Intent(Categories.this, ColdD.class);
                startActivity(coldD_intent);
            }
        });

        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent other_intent = new Intent(Categories.this, Other.class);
                startActivity(other_intent);
            }
        });
    }
}