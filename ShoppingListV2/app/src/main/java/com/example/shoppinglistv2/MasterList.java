package com.example.shoppinglistv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class MasterList extends AppCompatActivity {

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now;
    int i = 0;
    Intent intent;

    FirebaseAuth mAuth;

    public ArrayList<String> ListContent = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_list);
        intent = new Intent(this,ListActivity.class);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        ListContent.add("Doctor");
        ListContent.add("Special");
        ListContent.add("Aryaman Birthday");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference= db.collection("ItemList");

        final ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.temp_list_view,R.id.name,ListContent);


        ListView items = (ListView)findViewById(R.id.listView);
        items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MasterList.this, Long.toString(id), Toast.LENGTH_SHORT).show();
                //Item newItem = FirebaseContent.get((int)(id));
                intent.putExtra("List Name",ListContent.get((int)(id)));
                startActivity(intent);
            }
        });
        items.setAdapter(adapter);


    }




}
