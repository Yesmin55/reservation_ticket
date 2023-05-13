package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signup extends AppCompatActivity {
    private Button button3 ;

    private EditText editTextTextPersonName4 ;
    private EditText editTextTextPersonName5 ;
    private EditText editTextTextPersonName6 ;
    private EditText editTextTextPersonName7 ;
    private String name,CIN,email,password;
    private FirebaseAuth   firebaseAuth;
    private FirebaseDatabese FirebaseDatabese;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        editTextTextPersonName4=findViewById(R.id.editTextTextPersonName4);
        editTextTextPersonName5=findViewById(R.id.editTextTextPersonName5);
        editTextTextPersonName6=findViewById(R.id.editTextTextPersonName6);
        editTextTextPersonName7=findViewById(R.id.editTextTextPersonName7);

        firebaseAuth= FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        button3=findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Please wait .......!");
                progressDialog.show();
                if(validate()) {
                    String user_email=editTextTextPersonName6.getText().toString().trim();
                    String user_password=editTextTextPersonName7.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                sendemailverification();
                            } else {
                                Toast.makeText(signup.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    Toast.makeText(signup.this, "Done!", Toast.LENGTH_SHORT).show();

                }
            }

            private void sendemailverification() {
                FirebaseUser user =firebaseAuth.getCurrentUser();
                if(user != null){
                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                senduserdata();
                                Toast.makeText(signup.this, "Registration done, Please check your email!", Toast.LENGTH_SHORT).show();
                            firebaseAuth.signOut();
                            finish();
                            startActivity(new Intent(signup.this, login.class));
                            progressDialog.dismiss();
                            }else{
                                Toast.makeText(signup.this, "Registration Failed!", Toast.LENGTH_SHORT).show();
                           progressDialog.dismiss();
                            }

                        }
                    });
                }
            }

            private void senduserdata() {
                 FirebaseDatabese firebaseDatabese=FirebaseDatabese.getInstance();
                 DatabaseReference myRef =firebaseDatabese.getReference("Users");
                 user user =new user(email,name,CIN);
               myRef.child(""+firebaseAuth.getUid()).setValue(user);
            }
        });
    }

    private boolean validate() {
    boolean result = false ;
    name=editTextTextPersonName4.getText().toString();
    CIN=editTextTextPersonName5.getText().toString();
    email=editTextTextPersonName6.getText().toString();
    password=editTextTextPersonName7.getText().toString();
    if(name.isEmpty() || name.length()<7){
        editTextTextPersonName4.setError("Username is invalid");
    }else if (CIN.isEmpty() || CIN.length() != 8){
        editTextTextPersonName5.setError("CIN is invalid");
    }else if (email.isEmpty() || !email.contains("@") || !email.contains(".")){
        editTextTextPersonName6.setError("Email is invalid");
    }else if (password.isEmpty() || password.length()<8){
        editTextTextPersonName7.setError("Password is invalid");
    }else {
        result=true;
    }
return result;
    }
}