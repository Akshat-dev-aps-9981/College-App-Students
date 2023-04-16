package com.aksapps.nehrucollagestudents.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.aksapps.nehrucollagestudents.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText loginEmail, loginPassword;
    private MaterialButton loginBtn;
    private TextView openRegistration, openForgetPassword;

    private String email = "", password = "";
    private FirebaseAuth auth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        loginBtn = findViewById(R.id.login_btn);
        openRegistration = findViewById(R.id.open_register);
        openForgetPassword = findViewById(R.id.open_forget_password);
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setIcon(R.drawable.launcher_icon);
        progressDialog.setCancelable(false);

        openForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
                finish();
            }
        });

        openRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    private void validateData() {
        email = Objects.requireNonNull(loginEmail.getText()).toString();
        password = Objects.requireNonNull(loginPassword.getText()).toString();

        if (email.isEmpty()) {
            loginEmail.setError("Email is Required.");
            loginEmail.requestFocus();
        } else if (password.isEmpty()) {
            loginPassword.setError("Password is Required.");
            loginPassword.requestFocus();
        } else {
            loginUser();
        }
    }

    private void loginUser() {
        progressDialog.setTitle("Login User...");
        progressDialog.setMessage("Loging In the student, please wait...");
        progressDialog.show();
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Login Successful.", Toast.LENGTH_SHORT).show();
                    openMain();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Login user unsuccessful. Error: " + Objects.requireNonNull(Objects.requireNonNull(task.getException()).getMessage()).toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Login user Failed. Error: " + Objects.requireNonNull(e.getMessage()).toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}