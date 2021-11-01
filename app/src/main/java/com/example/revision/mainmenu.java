package com.example.revision;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class mainmenu extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);

        Button Room = findViewById((R.id.Room));
        Room.setOnClickListener(v -> {
             startActivity(new Intent(this, room.class));
        Button Group = findViewById((R.id.Group));
        Group.setOnClickListener(v1 -> {
            startActivity(new Intent(this, group.class));
        Button Create = findViewById((R.id.Create));
        Create.setOnClickListener(v2 -> startActivity(new Intent(this,create.class)));

        });

        });
    }



}





