package com.example.storyteller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText name;
    private EditText email;
    private EditText password;

    private EditText confirmPassword;
    private Button reg_btn;
    private Button log_btn;

    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.sign_name);
        email = findViewById(R.id.sign_email);
        password = findViewById(R.id.sign_password);
        confirmPassword = findViewById(R.id.confirm_sign_password);
        reg_btn = findViewById(R.id.btn_register);
        log_btn = findViewById(R.id.btn_login);

        auth = FirebaseAuth.getInstance();

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String txt_name = name.getText().toString();
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                String txt_confirm_password = confirmPassword.getText().toString();

                if(!TextUtils.isEmpty(txt_name) ||
                        !TextUtils.isEmpty(txt_email) ||
                        !TextUtils.isEmpty(txt_password) ||
                        !TextUtils.isEmpty(txt_confirm_password)
                ){

                    if (txt_email.length()<6){
                        Toast.makeText(RegisterActivity.this, "Password should be at least 6 character!", Toast.LENGTH_SHORT).show();
                    }else if(!txt_password.equals(txt_confirm_password)){
                        Toast.makeText(RegisterActivity.this, "Password miss-match", Toast.LENGTH_SHORT).show();
                    }else{
                        registerUser(txt_email,txt_password);
                    }
                }else{
                    Toast.makeText(RegisterActivity.this, "All Fields are manadatory!", Toast.LENGTH_SHORT).show();
                }



            }
        });


        log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });



    }

    private void registerUser(String email, String password) {

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegisterActivity.this, "Registration Failed!", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}
