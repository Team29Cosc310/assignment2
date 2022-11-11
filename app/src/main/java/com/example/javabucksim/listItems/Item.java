package com.example.javabucksim.listItems;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.javabucksim.R;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Item extends AppCompatActivity {

    TextView productName;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        back = findViewById(R.id.backButton);
        productName = findViewById(R.id.productName);

        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("productName");
        productName.setText(name);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}