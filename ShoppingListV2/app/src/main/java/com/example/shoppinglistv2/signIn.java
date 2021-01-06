package com.example.shoppinglistv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signIn extends AppCompatActivity {


    private FirebaseAuth mAuth;
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        final EditText email = findViewById(R.id.email);
        final EditText password = findViewById(R.id.password);
        Button registerButton = findViewById(R.id.register);
        Button signInButton = findViewById(R.id.signIn);







        mAuth = FirebaseAuth.getInstance();


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final HashMap<String,String> userInfo = new HashMap<>();
                final String emailID = email.getText().toString().trim();
                String passID = password.getText().toString().trim();
                mAuth.createUserWithEmailAndPassword(emailID, passID)
                        .addOnCompleteListener(signIn.this,new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(signIn.this, "Authentication success.",
                                            Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if(user != null)
                                    {
                                        String userUID = user.getUid();
                                        String email = emailID;
                                        Toast.makeText(signIn.this, user.getEmail() + " " + user.getUid(),
                                                Toast.LENGTH_SHORT).show();
                                        userInfo.put(email, "DOCTOR");
                                        db.collection("USERS").document("IDs").set(userInfo);
                                        viewList();
                                    }
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(signIn.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });





        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final HashMap<String,String> userInfo = new HashMap<>();
                final String emailID = email.getText().toString().trim();
                String passID = password.getText().toString().trim();

                mAuth.signInWithEmailAndPassword(emailID,passID)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    viewList();
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(signIn.this, "Login success. Welcome " + emailID,
                                            Toast.LENGTH_SHORT).show();


                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(signIn.this, "Login failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
            });
    }


    public void viewList(){
        Intent intent =  new Intent(this,MasterList.class);
        startActivity(intent);
    }


    public void viewList(Bundle bundle){
        Intent intent =  new Intent(this,MasterList.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }



}



