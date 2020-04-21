package com.example.storyteller;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button login;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        auth = FirebaseAuth.getInstance();

        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        login = findViewById(R.id.login_logBtn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();

                if(!TextUtils.isEmpty(txt_email) ||
                !TextUtils.isEmpty(txt_password)){

                    loginUser(txt_email,txt_password);

                }else{
                    Toast.makeText(LoginActivity.this, "All Fields are manadatory!", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void loginUser(String email, String password) {

        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this,ExploreActivity.class));
                finish();
            }
        });
    }

}
