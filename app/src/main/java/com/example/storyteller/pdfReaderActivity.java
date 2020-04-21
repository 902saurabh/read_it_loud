package com.example.storyteller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class pdfReaderActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private Button btn;
    private Button read_it;
    private TextView readText;
    private ImageView logo;

    private DrawerLayout drawerLayout;

    private static final int READ_REQUEST_CODE = 42;
    private static final String PRIMARY = "primary";
    private static final String LOCAL_STORAGE = "/storage/self/primary/";
    private static final String EXT_STORAGE = "/storage/7764-A034/";
    private static final String COLON = ":";

    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_reader);


        Toolbar toolbar = findViewById(R.id.toolbar);
        //
        setSupportActionBar(toolbar);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();





        //==========


        btn = findViewById(R.id.reader_btn);
        read_it = findViewById(R.id.read_it_loud);
        logo = findViewById(R.id.logo);
       // readText = findViewById(R.id.pdfText);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("*/*");
                startActivityForResult(intent, READ_REQUEST_CODE);
            }
        });

        read_it.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(pdfReaderActivity.this, "Please Choose the File first!", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if(requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if(resultData != null) {
                Uri uri = resultData.getData();

                Toast.makeText(this, uri.getPath(), Toast.LENGTH_SHORT).show();
                Log.v("URI", uri.getPath());
                readPdfFile(uri);
            }
        }
    }

    /**
     * open pdf file and convert to text and start text 2 speech
     * @param uri
     */
    public void readPdfFile(Uri uri) {
        String fullPath;
        //convert from uri to full path
        if(uri.getPath().contains(PRIMARY)) {

            fullPath = uri.getPath().split(COLON)[1];
        }
        else {
            fullPath = uri.getPath().split(COLON)[1];

        }
        Log.v("URI", uri.getPath()+" "+fullPath);
        final String stringParser;
        try {

            InputStream in = getContentResolver().openInputStream(uri);

            PdfReader pdfReader = new PdfReader(getContentResolver().openInputStream(uri));
            stringParser = PdfTextExtractor.getTextFromPage(pdfReader, 1).trim();
            pdfReader.close();


            final String[] language = {"hindi", "marathi"};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Pick a language");
            builder.setItems(language, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // the user clicked on colors[which]
                    startActivity(new Intent(pdfReaderActivity.this,MainActivity.class).putExtra("readFile",stringParser).putExtra("language",language[which]));
                }
            });
            builder.show();
            // readText.setText(stringParser);
           // textToSpeech.speak(stringParser, TextToSpeech.QUEUE_FLUSH,null, null);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return true;
    }
}
