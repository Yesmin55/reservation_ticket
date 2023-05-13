package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forget_password extends AppCompatActivity {
private Button button4 ;
private Button button5 ;
private EditText forget ;
private FirebaseAuth firebaseAuth ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        button5=findViewById(R.id.button5);
        forget=findViewById(R.id.forget);
        firebaseAuth=FirebaseAuth.getInstance();
        button4=findViewById(R.id.button4);

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String useremail = forget.getText().toString().trim();
                if (useremail.isEmpty() || useremail.contains("@") || useremail.contains(".")){
                    forget.setError("email is invalid");
                }else {
                    firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(forget_password.this, "password reset emeil sent! ", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(forget_password.this,login.class));
                            }else{
                                Toast.makeText(forget_password.this, "Error!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });



        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(forget_password.this, login.class));
            }
        });
    }
}