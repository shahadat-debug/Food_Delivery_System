package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    private ListView lvCartItems;
    private TextView tvTotalPrice;
    private Button btnPlaceOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        lvCartItems = findViewById(R.id.lvCartItems);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);

        List<String> itemStrings = new ArrayList<>();
        for (OrderItem item : Cart.getInstance().getItems()) {
            itemStrings.add(item.toString());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemStrings);
        lvCartItems.setAdapter(adapter);

        tvTotalPrice.setText("Total: PKR " + Cart.getInstance().getTotalPrice());

        btnPlaceOrder.setOnClickListener(v -> {
            Intent intent = new Intent(OrderActivity.this, RecieptActivity.class);
            startActivity(intent);
        });
    }
}
