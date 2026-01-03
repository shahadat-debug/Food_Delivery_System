package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Random;

public class RecieptActivity extends AppCompatActivity {

    private static final int SMS_PERMISSION_REQUEST_CODE = 1;
    private TextView tvCustomerInfo, tvReceipt, tvTotalPrice, tvDeliveryTime, tvRiderInfo;
    private Button btnDone;
    private String customerPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciept);

        tvCustomerInfo = findViewById(R.id.tvCustomerInfo);
        tvReceipt = findViewById(R.id.tvReceipt);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        tvDeliveryTime = findViewById(R.id.tvDeliveryTime);
        tvRiderInfo = findViewById(R.id.tvRiderInfo);
        btnDone = findViewById(R.id.btnDone);

        // Get customer info from SharedPreferences
        SharedPreferences preferences = getSharedPreferences("user_info", MODE_PRIVATE);
        String name = preferences.getString("name", "");
        String address = preferences.getString("address", "");
        customerPhone = preferences.getString("phone", "");

        tvCustomerInfo.setText("Customer: " + name + "\nAddress: " + address + "\nPhone: " + customerPhone);

        // Get order info from Cart
        StringBuilder orderSummary = new StringBuilder();
        for (OrderItem item : Cart.getInstance().getItems()) {
            orderSummary.append(item.toString()).append("\n");
        }
        tvReceipt.setText(orderSummary.toString());

        tvTotalPrice.setText("Total: PKR " + Cart.getInstance().getTotalPrice());

        // Generate random delivery time and rider info
        int deliveryTime = new Random().nextInt(30) + 15; // 15-45 minutes
        String riderName = "Ali";
        String riderPhone = "0300-1234567";
        String riderBike = "ABC-123";

        tvDeliveryTime.setText("Estimated Delivery Time: " + deliveryTime + " minutes");
        tvRiderInfo.setText("Rider: " + riderName + "\nPhone: " + riderPhone + "\nBike: " + riderBike);

        btnDone.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                sendSms();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_REQUEST_CODE);
            }
        });
    }

    private void sendSms() {
        String message = "Thank you for your order! Your total bill is PKR " + Cart.getInstance().getTotalPrice() + ". Your order will be delivered in approx. " + tvDeliveryTime.getText().toString() + ".";
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(customerPhone, null, message, null, null);
            Toast.makeText(this, "Confirmation SMS sent.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "SMS failed to send.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        Cart.getInstance().clearCart();
        Intent intent = new Intent(RecieptActivity.this, ThankYouActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendSms();
            } else {
                Toast.makeText(this, "SMS permission denied.", Toast.LENGTH_SHORT).show();
                // Proceed without sending SMS
                Cart.getInstance().clearCart();
                Intent intent = new Intent(RecieptActivity.this, ThankYouActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
