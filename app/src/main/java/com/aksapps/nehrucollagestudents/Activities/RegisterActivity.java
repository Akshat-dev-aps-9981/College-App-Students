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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText regName, regEmail, regPassword;
    private MaterialButton registerBtn;
    private TextView openLogin;
    private String name = "", email = "", password = "";
    private FirebaseAuth auth;
    private DatabaseReference reference, dbRef;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regName = findViewById(R.id.reg_name);
        regEmail = findViewById(R.id.reg_email);
        regPassword = findViewById(R.id.reg_password);
        registerBtn = findViewById(R.id.register_btn);
        openLogin = findViewById(R.id.open_login);
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.drawable.launcher_icon);

        openLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
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
            openMain();
        }
    }

    private void openMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void validateData() {
        name = regName.getText().toString();
        email = regEmail.getText().toString();
        password = regPassword.getText().toString();

        if (name.isEmpty()) {
            regName.setError("Name is Required.");
            regName.requestFocus();
        } else if (email.isEmpty()) {
            regEmail.setError("Email is Required.");
            regEmail.requestFocus();
        } else if (password.isEmpty()) {
            regPassword.setError("Password is Required.");
            regPassword.requestFocus();
        } else {
            createUser();
        }
    }

    private void createUser() {
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("Creating Your Account, Please Wait...");
        progressDialog.show();
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Registration Successful.", Toast.LENGTH_SHORT).show();
                    uploadData();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Registration Unsuccessful. Error: " + Objects.requireNonNull(Objects.requireNonNull(task.getException()).getMessage()).toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, "Registration Failed. Error: " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadData() {
        progressDialog.setTitle("Uploading Data...");
        progressDialog.setMessage("Uploading Your Data, Please Wait...");
        progressDialog.show();
        dbRef = reference.child("Users");
        String key = dbRef.push().getKey();
        HashMap<String, String> user = new HashMap<>();
        user.put("key", key);
        user.put("name", name);
        user.put("email", email);
        user.put("password", password);

        dbRef.child(key).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Successfully putted data into servers.", Toast.LENGTH_SHORT).show();
                    openMain();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Error in putting data into servers. Error: " + Objects.requireNonNull(Objects.requireNonNull(task.getException()).getMessage()).toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, "Failed putting data into servers." + Objects.requireNonNull(e.getMessage()).toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}