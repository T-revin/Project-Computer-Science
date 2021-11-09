package com.example.revision;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class mainmenu extends AppCompatActivity  {
    RecyclerView categoryList;
    ArrayList<Categories> categoryModels;
    CategoryAdapter CategoryAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
        categoryList = findViewById(R.id.categoryList);

        db = FirebaseFirestore.getInstance();
        categoryModels = new ArrayList<Categories>();
        CategoryAdapter = new CategoryAdapter(mainmenu.this,categoryModels);
        EventChangeListener();

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
    //Recycler View Categories
    private  void EventChangeListener(){
        db.collection("Catergory")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if(error !=null){

                            Log.e("Error",error.getMessage());
                            return;//ERROR message
                        }
                        for (DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType()== DocumentChange.Type.ADDED){
                                categoryModels.add(dc.getDocument().toObject(Categories.class));
                            }

                            CategoryAdapter.notifyDataSetChanged();
                        }
                    }
                });


}}





