package com.example.asus.mnmgrecipejournal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {

    private EditText email,pass,phone,cpassword,name;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    public ProgressBar progressBar;
    public String emailAddress,password,Name,Phone,ConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name = (EditText) findViewById(R.id.editText3);
        email = (EditText) findViewById(R.id.editText4);
        pass = (EditText) findViewById(R.id.editText6);
        cpassword = (EditText) findViewById(R.id.editText7);
        phone = (EditText) findViewById(R.id.editText5);

        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public boolean InputVerify(String emailAddress,String password,String Name,String Phone,String ConfirmPassword){

        if (Name.equals("")) {
            name.setError("Name Field Can't left Blank ");
            name.requestFocus();
            return false;
        }

        if (Phone.equals("")) {
            phone.setError("Phone Number Field Can't left Blank ");
            phone.requestFocus();
            return false;
        }

        if (emailAddress.equals("")) {
            email.setError("Email Address Field Can't left Blank ");
            email.requestFocus();
            return false;
        }

        if (password.equals("")) {
            pass.setError("Password Field Can't left Blank ");
            pass.requestFocus();
            return false;
        }

        if (ConfirmPassword.equals("")) {
            pass.setError("Confirm Password Field Can't left Blank ");
            pass.requestFocus();
            return false;
        }

        if(!android.util.Patterns.PHONE.matcher(Phone).matches()){
            phone.setError("Phone Number Invalid");
            phone.requestFocus();
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            email.setError("Please Enter Valid Email Address");
            email.requestFocus();
            return false;
        }

        if (password.length() < 8) {
            pass.setError("Password must contain Minimum 8 character");
            pass.requestFocus();
            return false;
        }

        if (!password.matches("^(?=.*[@$%&#_()=+?»«<>£§€{}\\[\\]-])(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).*")) {
            pass.setError("Password Must have at least 1 upper case letter \n " +
                    "Password Must have at least 1 lower case letter \n " +
                    "Password Must have at least 1 digit \n ");
            pass.requestFocus();
            return false;
        }

        if(!password.equals(ConfirmPassword)){
            cpassword.setError("Both Passwords Must be Same");
            cpassword.requestFocus();
            return false;
        }

        return true;
    }

    public String EncodeEmail(String emailAddress){
        emailAddress = emailAddress.replace(".", ",");
        return emailAddress;
    }

    public String DecodeEmail(String emailAddress){
        emailAddress = emailAddress.replace(",", ".");
        return emailAddress;
    }

    public void uploadData(String emailAddress, String password, String Name, String Phone){
        try {
            emailAddress = EncodeEmail(emailAddress);
            mDatabase.child("Users").child(emailAddress).child("Email Address").setValue(emailAddress);
            mDatabase.child("Users").child(emailAddress).child("Password").setValue(password);
            mDatabase.child("Users").child(emailAddress).child("Full Name").setValue(Name);
            mDatabase.child("Users").child(emailAddress).child("Phone Number").setValue(Phone);
            emailAddress = DecodeEmail(emailAddress);
        }catch (Exception e){
            Toast.makeText(Registration.this, "Failed Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void RegisterButton(View view){

        emailAddress = email.getText().toString().trim();
        password = pass.getText().toString().trim();
        Name = name.getText().toString().trim();
        ConfirmPassword = cpassword.getText().toString().trim();
        Phone = phone.getText().toString().trim();

        if(InputVerify(emailAddress,password,Name,Phone,ConfirmPassword)) {

            progressBar.setVisibility(View.VISIBLE);

            firebaseAuth.createUserWithEmailAndPassword(emailAddress, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Registration.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        Toast.makeText(Registration.this, "Please Wait Uploading to Database... ", Toast.LENGTH_SHORT).show();
                        uploadData(emailAddress,password,Name,Phone);
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(Registration.this, "Successfully Uploaded to Database", Toast.LENGTH_SHORT).show();
                        Toast.makeText(Registration.this, "Please Login with ur registered Credentials to Continue ", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Registration.this, Login.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(Registration.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
