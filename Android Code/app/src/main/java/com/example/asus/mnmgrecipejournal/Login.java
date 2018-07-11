package com.example.asus.mnmgrecipejournal;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private EditText email,pass;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private DatabaseReference mDatabase;
    public String password,emailAddress,Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.editText);
        pass = (EditText) findViewById(R.id.editText2);
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public boolean InputVerify(String emailAddress, String password){

        if (password.equals("")) {
            pass.setError("Password Field Can't left Blank ");
            pass.requestFocus();
        }

        if (emailAddress.equals("")) {
            email.setError("Email Address Field Can't left Blank ");
            email.requestFocus();
        }

        if (password.length() < 8) {
            pass.setError("Password must contain Minimum 8 character");
            pass.requestFocus();
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            email.setError("Please Enter Valid Email Address");
            email.requestFocus();
        }
        if (!emailAddress.equals("") && password.length() >= 8 && !password.equals("")
                && android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            return true;
        }
        return false;
    }

    public String EncodeEmail(String emailAddress){
        emailAddress = emailAddress.replace(".", ",");
        return emailAddress;
    }

    public String DecodeEmail(String emailAddress) {
        emailAddress = emailAddress.replace(",", ".");
        return emailAddress;
    }

    public void SubmitButton(View view){

        emailAddress = email.getText().toString().trim();
        password = pass.getText().toString().trim();

        if(InputVerify(emailAddress,password)) {

            progressBar.setVisibility(View.VISIBLE);

            firebaseAuth.signInWithEmailAndPassword(emailAddress, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        mDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                emailAddress = EncodeEmail(emailAddress);
                                Name = dataSnapshot.child("Users").child(emailAddress).child("Full Name").getValue().toString();
                                Toast.makeText(Login.this, Name, Toast.LENGTH_SHORT).show();
                                emailAddress = DecodeEmail(emailAddress);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(Login.this, "Failed To Retrieve Data", Toast.LENGTH_SHORT).show();
                            }
                        });

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                Toast.makeText(Login.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                Intent i = new Intent(Login.this, HomePage.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.putExtra("Name",Name);
                                i.putExtra("Email",emailAddress);
                                startActivity(i);
                                finish();
                            }
                        }, 5000);

                    } else {
                        progressBar.setVisibility(View.GONE);
                        Log.e("ERROR", task.getException().getMessage());
                        Toast.makeText(Login.this, "UnSuccessfully", Toast.LENGTH_SHORT).show();
                        Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void SignUp(View view){
        Intent i = new Intent(Login.this,Registration.class);
        startActivity(i);
    }
}
