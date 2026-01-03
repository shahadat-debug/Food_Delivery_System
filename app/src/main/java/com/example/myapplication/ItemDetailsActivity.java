package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ItemDetailsActivity extends AppCompatActivity {

    private TextView tvItemName;
    private RadioGroup rgSize;
    private EditText etQuantity;
    private Button btnAddToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        tvItemName = findViewById(R.id.tvItemName);
        rgSize = findViewById(R.id.rgSize);
        etQuantity = findViewById(R.id.etQuantity);
        btnAddToCart = findViewById(R.id.btnAddToCart);

        String itemName = getIntent().getStringExtra("itemName");
        tvItemName.setText(itemName);

        btnAddToCart.setOnClickListener(v -> {
            int selectedId = rgSize.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this, "Please select a size", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selectedRadioButton = findViewById(selectedId);
            String sizeWithPrice = selectedRadioButton.getText().toString();
            String[] parts = sizeWithPrice.split(" - PKR ");
            String size = parts[0];
            int price = Integer.parseInt(parts[1]);

            int quantity = Integer.parseInt(etQuantity.getText().toString());

            Cart.getInstance().addItem(new OrderItem(itemName, size, quantity, price));

            new AlertDialog.Builder(this)
                    .setTitle("Item Added")
                    .setMessage("Do you want to add another item?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Intent intent = new Intent(ItemDetailsActivity.this, MenuActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    })
                    .setNegativeButton("No, go to cart", (dialog, which) -> {
                        Intent intent = new Intent(ItemDetailsActivity.this, OrderActivity.class);
                        startActivity(intent);
                    })
                    .show();
        });
    }
}
