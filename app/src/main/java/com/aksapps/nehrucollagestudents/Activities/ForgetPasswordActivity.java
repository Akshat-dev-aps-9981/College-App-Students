package com.aksapps.nehrucollagestudents.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.aksapps.nehrucollagestudents.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ForgetPasswordActivity extends AppCompatActivity {
    private TextInputEditText forgetEmail;
    private MaterialButton forgetBtn;
    private FirebaseAuth auth;
    private String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        forgetEmail = findViewById(R.id.forget_email);
        forgetBtn = findViewById(R.id.forget_password_btn);
        auth = FirebaseAuth.getInstance();

        forgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });
    }

    private void validateData() {
        email = forgetEmail.getText().toString();
        if (email.isEmpty()) {
            forgetEmail.setError("Email is Required.");
            forgetEmail.requestFocus();
        } else {
            forgetPassword();
        }
    }

    private void forgetPassword() {
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ForgetPasswordActivity.this, "Please Check Your Email for reset link.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(ForgetPasswordActivity.this, "Something went wrong while sending email. Error: " + Objects.requireNonNull(Objects.requireNonNull(task.getException()).getMessage()).toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}