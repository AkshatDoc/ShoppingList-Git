package com.example.shoppinglistv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.*;

import java.util.ArrayList;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now;
    int i = 0;
    Intent intent;

    FirebaseAuth mAuth;

    public ArrayList<Item> FirebaseContent = new ArrayList<>();
    Button addInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_list);
        Intent thisIntent = getIntent();

        addInfo = findViewById(R.id.addItems);

        intent = new Intent(this,MainActivity.class);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        String listName = thisIntent.getStringExtra("List Name");

        Toast.makeText(this, listName + " KSJDKSJDK", Toast.LENGTH_SHORT).show();
        TextView nameOfList = findViewById(R.id.curListName);

        nameOfList.setText(listName);


        addInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Item newItem = new Item(null,0,0,false);
                bundle = newItem.toBundle();
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });




        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference= db.collection("ItemList");

        final ItemAdapter adapter = new ItemAdapter(this, FirebaseContent);

        collectionReference.limit(25).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            for(QueryDocumentSnapshot journals: queryDocumentSnapshots) {
                                FirebaseContent.add(new Item(journals.getId(),(int)(long)journals.get("Quantity"),(double)journals.get("Cost"),(boolean)journals.get("Purchased")));
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
        ListView items = (ListView)findViewById(R.id.list);
        items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListActivity.this, Long.toString(id), Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                Item newItem = FirebaseContent.get((int)(id));
                bundle = newItem.toBundle();
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        items.setAdapter(adapter);


    }




}
