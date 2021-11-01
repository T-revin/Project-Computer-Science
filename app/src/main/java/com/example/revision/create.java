package com.example.revision;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class create extends AppCompatActivity {
    private EditText EnterQuestion, answer1,answer2, answer3,answer4;
    private FirebaseFirestore db;
    private Button btnSave, btnAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        db = FirebaseFirestore.getInstance();
        EnterQuestion = findViewById(R.id.EnterQuestion);
        answer1 = findViewById((R.id.answer1));
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener((View.OnClickListener) this);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Pushing the Inputted question and options from the user into the database
                String Question = EnterQuestion.getText().toString();
                String option1 = answer1.getText().toString();
                String option2= answer2.getText().toString();
                String option3= answer3.getText().toString();
                String option4= answer4.getText().toString();
                Map<String,Object> QuestionSet = new HashMap<>();
                QuestionSet.put("Question", Question);
                QuestionSet.put("option 1",option1);
                QuestionSet.put("option 2",option2);
                QuestionSet.put("option 3",option3);
                QuestionSet.put("option 4",option4);

                db.collection("QuestionSet")
                        .add(QuestionSet)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(create.this,"Question Saved!",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(create.this,"Unsuccessfull, Try Again!",Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }
}

