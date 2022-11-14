package com.example.javabucksim.listItems;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.javabucksim.R;

public class Item extends AppCompatActivity {

    TextView productName, smol, mid, chonk, one, two, three, price12, price16, price20;
    Button back;
//    String sCalories, sProtein, sFat, sCarbs, sFiber, sSugar;
//    String mCalories, mProtein, mFat, mCarbs, mFiber, mSugar;
//    String cCalories, cProtein, cFat, cCarbs, cFiber, cSugar;
//    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        back = findViewById(R.id.backButton);
        productName = findViewById(R.id.productName);
        smol = findViewById(R.id.smol);
        mid = findViewById(R.id.mid);
        chonk = findViewById(R.id.chonk);
        one = findViewById(R.id.price_12oz);
        two = findViewById(R.id.price_16oz);
        three = findViewById(R.id.price_20oz);
        price12 = findViewById(R.id.price12oz);
        price16 = findViewById(R.id.price16oz);
        price20 = findViewById(R.id.price20oz);

        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("productName");
        productName.setText(name);

        if (name.equals("Pumpkin Spice Latte")){
            price12.setText("$4.75");
            price16.setText("$5.25");
            price20.setText("$5.75");
        }  else if (name.equals("Dark Roast")) {
            price12.setText("$2.25");
            price16.setText("$2.75");
            price20.setText("$3.25");
        }  else if (name.equals("Medium Roast")){
            price12.setText("$2.25");
            price16.setText("$2.75");
            price20.setText("$3.25");
        }  else if (name.equals("Blonde Roast")){
            price12.setText("$2.25");
            price16.setText("$2.75");
            price20.setText("$3.25");
        }  else if (name.equals("Cappuccino")){
            price12.setText("$3.25");
            price16.setText("$3.75");
            price20.setText("$4.25");
        }  else if (name.equals("Iced Pumpkin Spice Latte")){
            price12.setText("$4.75");
            price16.setText("$5.25");
            price20.setText("$5.75");
        }  else if (name.equals("Iced Coffee")){
            price12.setText("$2.25");
            price16.setText("$2.75");
            price20.setText("$3.25");
        }  else if (name.equals("Cold Brew")){
            price12.setText("$2.25");
            price16.setText("$2.75");
            price20.setText("$3.25");
        }  else if (name.equals("Iced Cappuccino")){
            price12.setText("$3.25");
            price16.setText("$3.75");
            price20.setText("$4.25");
        }  else if (name.equals("Pumpkin Chai Latte")){
            price12.setText("$4.75");
            price16.setText("$5.25");
            price20.setText("$5.75");
        }  else if (name.equals("Tea")){
            price12.setText("$2.25");
            price16.setText("$2.75");
            price20.setText("$3.25");
        }  else if (name.equals("Hot Chocolate")){
            price12.setText("$3.25");
            price16.setText("$3.75");
            price20.setText("$4.25");
        }  else if (name.equals("Chai Latte")){
            price12.setText("$3.25");
            price16.setText("$3.75");
            price20.setText("$4.25");
        }  else if (name.equals("Matcha Latte")){
            price12.setText("$3.25");
            price16.setText("$3.75");
            price20.setText("$4.25");
        }  else if (name.equals("Iced Pumpkin Chai Latte")){
            price12.setText("$4.75");
            price16.setText("$5.25");
            price20.setText("$5.75");
        }  else if (name.equals("Iced Tea")){
            price12.setText("$2.25");
            price16.setText("$2.75");
            price20.setText("$3.25");
        }  else if (name.equals("Iced Chai Latte")){
            price12.setText("$3.25");
            price16.setText("$3.75");
            price20.setText("$4.25");
        }  else if (name.equals("Iced Matcha Latte")){
            price12.setText("$3.25");
            price16.setText("$3.75");
            price20.setText("$4.25");
        } else if (name.equals("Juice")){
            smol.setText("Orange");
            mid.setText("Apple");
            chonk.setText("Cranberry");

            one.setText("Orange:");
            two.setText("Apple:");
            three.setText("Cranberry:");

            price12.setText("$3.25");
            price16.setText("$3.25");
            price20.setText("$3.25");
        } else if (name.equals("Flavour Shots")){
            smol.setText("Vanilla");
            mid.setText("Hazelnut");
            chonk.setText("Caramel");

            one.setText("Vanilla:");
            two.setText("Hazelnut:");
            three.setText("Caramel:");

            price12.setText("$0.25");
            price16.setText("$0.25");
            price20.setText("$0.25");
        } else if (name.equals("Espresso")){
            smol.setText("One");
            mid.setText("Two");
            chonk.setText("Three");

            one.setText("One:");
            two.setText("Two:");
            three.setText("Three:");

            price12.setText("$0.75");
            price16.setText("$0.75");
            price20.setText("$0.75");
        } else if (name.equals("Milk")){
            smol.setText("Splash");
            mid.setText("Normal");
            chonk.setText("Extra");

            one.setText("Splash:");
            two.setText("Normal:");
            three.setText("Extra:");

            price12.setText("$0.25");
            price16.setText("$0.50");
            price20.setText("$0.75");
        } else if (name.equals("Cream")){
            smol.setText("Splash");
            mid.setText("Normal");
            chonk.setText("Extra");

            one.setText("Splash:");
            two.setText("Normal:");
            three.setText("Extra:");

            price12.setText("$0.25");
            price16.setText("$0.50");
            price20.setText("$0.75");
        } else if (name.equals("Sugar")){
            smol.setText("One");
            mid.setText("Two");
            chonk.setText("Three");

            one.setText("One:");
            two.setText("Two:");
            three.setText("Three:");

            price12.setText("$0.25");
            price16.setText("$0.50");
            price20.setText("$0.75");
        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}