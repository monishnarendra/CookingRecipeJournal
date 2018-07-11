package com.example.asus.mnmgrecipejournal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class HomePage extends AppCompatActivity {

    private String Name,Email_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarid);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Email_Id = getIntent().getStringExtra("Email");
        Name = getIntent().getStringExtra("Name");

        getSupportActionBar().setTitle("Welcome " + Name);

        Toast.makeText(HomePage.this, "Entered MainPage", Toast.LENGTH_SHORT).show();
        Toast.makeText(HomePage.this, Name, Toast.LENGTH_SHORT).show();
    }

    public void AllRecipesCV(View view){
        Intent i = new Intent(HomePage.this,ViewRecipes.class);
        i.putExtra("Email",Email_Id);
        startActivity(i);
    }

    public void SearchRecipesCV(View view){
        Toast.makeText(HomePage.this, "Search", Toast.LENGTH_SHORT).show();
    }

    public void ProfileCV(View view){
        Intent i = new Intent(HomePage.this,Profile.class);
        i.putExtra("Email",Email_Id);
        startActivity(i);
    }

    public void AddRecipesCV(View view){
        Intent i = new Intent(HomePage.this,AddRecipes.class);
        i.putExtra("Email",Email_Id);
        startActivity(i);
    }
}
