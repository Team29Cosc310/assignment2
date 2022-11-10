package com.example.javabucksim.listItems;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.javabucksim.listItems.Item;
import com.example.javabucksim.R;

public class Choices extends AppCompatActivity {

    Button choice1, choice2, choice3, choice4, choice5, back;
    TextView category;
    String cat, c1, c2, c3, c4, c5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choices);

        back = findViewById(R.id.backButton);
        category = findViewById(R.id.category);
        choice1 = findViewById(R.id.choice1);
        choice2 = findViewById(R.id.choice2);
        choice3 = findViewById(R.id.choice3);
        choice4 = findViewById(R.id.choice4);
        choice5 = findViewById(R.id.choice5);

        Bundle bundle = getIntent().getExtras();
        cat = bundle.getString("category");
        c1 = bundle.getString("choice1");
        c2 = bundle.getString("choice2");
        c3 = bundle.getString("choice3");
        c4 = bundle.getString("choice4");
        c5 = bundle.getString("choice5");

        if(bundle != null) {
            category.setText(cat);
            choice1.setText(c1);
            choice2.setText(c2);
            choice3.setText(c3);
            choice4.setText(c4);
            choice5.setText(c5);
        } else {
            Toast.makeText(this, "Null", Toast.LENGTH_SHORT).show();
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productName = c1;
                Intent intent = new Intent(Choices.this, Item.class);
                Bundle bundle = new Bundle();
                bundle.putString("productName", productName);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        choice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productName = c2;
                Intent intent = new Intent(Choices.this, Item.class);
                Bundle bundle = new Bundle();
                bundle.putString("productName", productName);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        choice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productName = c3;
                Intent intent = new Intent(Choices.this, Item.class);
                Bundle bundle = new Bundle();
                bundle.putString("productName", productName);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        choice4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productName = c4;
                Intent intent = new Intent(Choices.this, Item.class);
                Bundle bundle = new Bundle();
                bundle.putString("productName", productName);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        choice5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productName = c5;
                Intent intent = new Intent(Choices.this, Item.class);
                Bundle bundle = new Bundle();
                bundle.putString("productName", productName);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
}