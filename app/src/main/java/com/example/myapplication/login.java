package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {
private TextView textView3 ;
private TextView textView2;
private EditText email ;
private EditText password ;
private Button button2;
private FirebaseAuth firebaseAuth ;
private ProgressDialog progressDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        button2=findViewById(R.id.button2);
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);



        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().isEmpty() || !email.getText().toString().contains("@") || !email.getText().toString().contains(".") ){
                    email.setError("email is invalid");
                }else if(password.getText().toString().isEmpty() || password.getText().toString().length()<8){
                    password.setError("password is invalid");
                }else {
                    validate(email.getText().toString(),password.getText().toString());
                }
            }
        });

        textView2=findViewById(R.id.textView2);
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this,forget_password.class));
            }
        });
        textView3=findViewById(R.id.textView3);
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this,signup.class));
            }
        });
    }

    private void validate(String emailS, String passwordS) {
        ProgressDialog.setMessage("please wait ..... !!!!");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(emailS,passwordS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    chekemailverification();
                }else{
                    Toast.makeText(login.this, "please virify that your data is correct!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });

    }

    private void chekemailverification() {
        FirebaseUser user =firebaseAuth.getCurrentUser();
        boolean emailFlag = user.isEmailVerified();
        if(emailFlag){
                  startActivity(new Intent(login.this,principale.class));
        }else{
            Toast.makeText(this, "please check your email!", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}