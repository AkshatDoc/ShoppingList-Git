package com.example.shoppinglistv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    public void deleteItem(FirebaseFirestore db, String itemName) {
        db.collection("ItemList").document(itemName.toUpperCase()).delete()
        .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(MainActivity.this, "DELETE SUCCESSFUL", Toast.LENGTH_SHORT).show();
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "FAILED DELETE", Toast.LENGTH_SHORT).show();
            }
        });

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent thisIntent = getIntent();
        String name = thisIntent.getStringExtra("NAME");
        Integer qty = thisIntent.getIntExtra("QTY",0);
        double price =  thisIntent.getDoubleExtra("PRICE",0.0);
        boolean isBought = thisIntent.getBooleanExtra("PURCHASED",false);

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        final EditText nameItemBox = (EditText) findViewById(R.id.itemName);
        final EditText qtyBox = findViewById(R.id.itemQty);
        final EditText priceBox = findViewById(R.id.itemCost);
        final CheckBox beenBought = findViewById(R.id.purchased);
        Button submitBox = findViewById(R.id.submit);
        Button deleteButton = findViewById(R.id.delete);
        Button switchButton  =  findViewById(R.id.viewSwitch);

        if(name != null) {
            nameItemBox.setText(name);
        } else{
            nameItemBox.setText("");
        }

        qtyBox.setText(qty.toString());
        priceBox.setText(Double.toString(price));
        beenBought.setChecked(isBought);



        submitBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> item = new HashMap<>();
                String itemName = nameItemBox.getText().toString().toUpperCase();
                boolean canBePurchased = beenBought.isChecked();
                String strOfItems = null;
                String strPriceOfItem = null;
                try{

                    strOfItems = (qtyBox.getText().toString());
                    strPriceOfItem = (priceBox.getText().toString());
                }catch (NumberFormatException e)
                {
                    System.out.println(e);
                }

                int nOfItems = Integer.parseInt(strOfItems.trim());
                double priceOfItems = Double.parseDouble(strPriceOfItem.trim());
                if(!itemName.isEmpty()) {
                } else {
                }
                item.put("Purchased",canBePurchased);
                item.put("Quantity", nOfItems);
                item.put("Cost",priceOfItems);
                db.collection("ItemList").document(itemName).set(item);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = nameItemBox.getText().toString().toUpperCase();
                deleteItem(db,itemName);
            }
        });

        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ListActivity.class);
                startActivity(intent);
            }
        });



        }


    public void signInWindow(View view) {
        Intent intent = new Intent(MainActivity.this,signIn.class);
        startActivity(intent);
    }
}
