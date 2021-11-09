package com.example.revision;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button LoginBtn;//Initalising widgets
    private Button RegisterBtn;
    private EditText Email,Password;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private TextView ForgotPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        LoginBtn = (Button) findViewById(R.id.LoginBtn);
        LoginBtn.setOnClickListener(this);
        RegisterBtn = (Button) findViewById(R.id.RegisterBtn);
        RegisterBtn.setOnClickListener(this);

        Email = (EditText) findViewById(R.id.Username);
        Password = (EditText) findViewById(R.id.Password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();

        ForgotPassword = (TextView)findViewById(R.id.ResetPassword);
        ForgotPassword.setOnClickListener(this);

    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.RegisterBtn://redirecting to register page
                startActivity(new Intent(this,SignUp.class));
                break;
            case R.id.LoginBtn://login user
                userLogin();
                break;
            case R.id.ResetPassword://redirecting to forget password page
                startActivity(new Intent(this,ForgotPassword.class));
                break;
        }
    }
    private void userLogin(){
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();

        if (email.isEmpty()){//checking if email has an input
            Email.setError("Email is required");
            Email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){//checking email is valid
            Email.setError("Please enter a valid email!");
            Email.requestFocus();
            return;
        }
        if(password.isEmpty()){//checking if password has an input
            Password.setError("Password is required");
            Password.requestFocus();
            return;
        }
        if(password.length() < 6){//checking password is at least 6 characters
            Password.setError("Password must be at least 6 characters");
            Password.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified()){
                        startActivity(new Intent(MainActivity.this, Profile.class));
                }else{
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this, "Check your email for verification", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }

}




