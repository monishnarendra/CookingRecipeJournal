package com.example.asus.mnmgrecipejournal;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddRecipes extends AppCompatActivity {

    List<String> lstSource = new ArrayList<>();
    private EditText etName,etDuration,etUrl,etIngredients,etInstructions;
    private String level,name,duration,ingredients,instructions,url,Email;
    private Spinner etSpinner;
    private DatabaseReference mDatabase;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipes);
        etName = findViewById(R.id.editText8);
        etSpinner = findViewById(R.id.spinner);
        etDuration = findViewById(R.id.editText9);
        etUrl = findViewById(R.id.editText10);
        etIngredients = findViewById(R.id.editText12);
        etInstructions = findViewById(R.id.editText11);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Email = getIntent().getStringExtra("Email");
        Toast.makeText(AddRecipes.this, "Email : " + Email, Toast.LENGTH_SHORT).show();

        generateData();
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        SpinnerAdapter adapter = new SpinnerAdapterItem(lstSource,AddRecipes.this);
        spinner.setAdapter(adapter);
    }

    public void generateData(){
        lstSource.add("Ok " + (1));
        lstSource.add("Ok " + (2));
        lstSource.add("Ok " + (3));
        lstSource.add("Good " + (4));
        lstSource.add("Excellent " + (5));
    }

    public String EncodeEmail(String emailAddress){
        emailAddress = emailAddress.replace(".", ",");
        return emailAddress;
    }

    public void AddRecipesButton(View view){
        name = etName.getText().toString().trim();
        level = etSpinner.getSelectedItem().toString();
        duration = etDuration.getText().toString().trim();
        url = etUrl.getText().toString().trim();
        ingredients = etIngredients.getText().toString().trim();
        instructions = etInstructions.getText().toString().trim();

        mDatabase.child("f").setValue(name);

        Toast.makeText(AddRecipes.this, "Name: " + name, Toast.LENGTH_SHORT).show();
        Toast.makeText(AddRecipes.this, "Rating: " + level, Toast.LENGTH_SHORT).show();
        Toast.makeText(AddRecipes.this, "url: " + url, Toast.LENGTH_SHORT).show();
        Toast.makeText(AddRecipes.this, "duration: " + duration, Toast.LENGTH_SHORT).show();
        Toast.makeText(AddRecipes.this, "ingredients: " + ingredients, Toast.LENGTH_SHORT).show();
        Toast.makeText(AddRecipes.this, "url: " + url, Toast.LENGTH_SHORT).show();

        Email = EncodeEmail(Email);

        mDatabase.child("Users").child(Email).child("Recipes").child(name).child("Name").setValue(name);
        mDatabase.child("Users").child(Email).child("Recipes").child(name).child("Rating").setValue(level);
        mDatabase.child("Users").child(Email).child("Recipes").child(name).child("Duration").setValue(duration);
        mDatabase.child("Users").child(Email).child("Recipes").child(name).child("Url").setValue(url);
        mDatabase.child("Users").child(Email).child("Recipes").child(name).child("Ingredients").setValue(ingredients);
        mDatabase.child("Users").child(Email).child("Recipes").child(name).child("Instructions").setValue(instructions);
    }
}
