package com.example.storyteller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    private Button login;
    private Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        login = findViewById(R.id.start_login);
        register = findViewById(R.id.start_register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(StartActivity.this, "login page", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(StartActivity.this,LoginActivity.class));
                //finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(StartActivity.this, "register page", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(StartActivity.this,RegisterActivity.class));
               // finish();
            }
        });

    }
}
