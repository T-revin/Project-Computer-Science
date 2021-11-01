package com.example.revision;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private Button registerUser;
    private TextView Banner;
    private EditText Email, Username, Password, ConfirmPasswordReg;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();

        Banner = (TextView) findViewById((R.id.Banner));
        Banner.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        Email = (EditText) findViewById(R.id.Email);
        Username = (EditText) findViewById(R.id.Username);
        Password = (EditText) findViewById(R.id.Password);
        ConfirmPasswordReg = (EditText) findViewById(R.id.ConfirmPasswordReg);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Banner:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.registerUser:
                registerUser();
                break;

        }
    }

    private void registerUser() {
        String email = Email.getText().toString().trim();
        String username = Username.getText().toString().trim();
        String password = Password.getText().toString().trim();
        String confirmpassword = ConfirmPasswordReg.getText().toString().trim();

        if (email.isEmpty()) {//checking if email has an input from the user
            Email.setError("Email is required");
            Email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {// validating if email actually exists
            Email.setError("Please provide vaild email");
            Email.requestFocus();
            return;
        }
        if (username.isEmpty()) {//checking username has an input
            Username.setError("Username is required");
            Username.requestFocus();
            return;
        }
        if (password.isEmpty()) {//checking if password has an input
            Password.setError("Password is required");
            Password.requestFocus();
            return;
        }
        if (password.length() < 6) { // making sure password is at least 6 characters
            Password.setError("Password must be at least 6 characters");
        }
        if (confirmpassword.isEmpty()) {//checking if confirm password has an input
            ConfirmPasswordReg.setError("Please confirm Password");
            ConfirmPasswordReg.requestFocus();
            return;
        }
        if (!confirmpassword.equals(password)){//checking if confirm password input is the same as password
            ConfirmPasswordReg.setError("Password must match");
            ConfirmPasswordReg.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            User user = new User(email,username,password);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()){
                                        Toast.makeText(SignUp.this,"Registration Successfull!",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                        startActivity(new Intent(SignUp.this,MainActivity.class));
                                        //Redirect to login
                                    }else{
                                        Toast.makeText(SignUp.this,"Failed Registration, Try Again!",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }


                                }
                            });

                        } else{
                            Toast.makeText(SignUp.this,"Failed Registration, Try Again!",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}