package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MenuActivity extends AppCompatActivity {

    private CardView cardPizza, cardBurger, cardShawarma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        cardPizza = findViewById(R.id.cardPizza);
        cardBurger = findViewById(R.id.cardBurger);
        cardShawarma = findViewById(R.id.cardShawarma);

        cardPizza.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, ItemDetailsActivity.class);
            intent.putExtra("itemName", "Pizza");
            startActivity(intent);
        });

        cardBurger.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, ItemDetailsActivity.class);
            intent.putExtra("itemName", "Burger");
            startActivity(intent);
        });

        cardShawarma.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, ItemDetailsActivity.class);
            intent.putExtra("itemName", "Shawarma");
            startActivity(intent);
        });
    }
}
