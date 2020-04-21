package com.example.storyteller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DetailsActivity extends AppCompatActivity {

    private Button translate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        translate = findViewById(R.id.translate);

        translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] language = {"hindi", "marathi"};

                AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
                builder.setTitle("Pick a language");
                builder.setItems(language, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // the user clicked on language[which]
                        startActivity(new Intent(DetailsActivity.this,MainActivity.class).putExtra("readFile","").putExtra("language",language[which]));

                    }
                });
                builder.show();

            }
        });
    }
}
