package com.example.storyteller;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Locale;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.languageid.FirebaseLanguageIdentification;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;








public class MainActivity extends AppCompatActivity {

    EditText text;
    TextView englishText;
    Button speak;
    StringBuilder hindiString;
    private TextToSpeech textToSpeech;
    String readFile="";
    String language="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readFile=getIntent().getStringExtra("readFile");
        language=getIntent().getStringExtra("language");
        //Toast.makeText(this, readFile, Toast.LENGTH_SHORT).show();
        Log.i("extraaaaaa",readFile);
       // text = findViewById(R.id.enter_text);
        englishText = findViewById(R.id.translatedText);
        speak = findViewById(R.id.speaker);
        hindiString = new StringBuilder();
        translate();
        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this, hindiString.toString(), Toast.LENGTH_SHORT).show();
               loadSpeakingLanguages(hindiString.toString());
                textToSpeech.setLanguage(Locale.forLanguageTag("hin"));

            }
        });











    }

    private void loadSpeakingLanguages(String textToTranslate) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ttsGreater21(textToTranslate);
        } else {
            ttsUnder20(textToTranslate);
        }
    }

    @SuppressWarnings("deprecation")
    private void ttsUnder20(String text) {
        HashMap<String, String> map = new HashMap<>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "MessageId");

        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, map);


    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void ttsGreater21(String text) {
        String utteranceId = this.hashCode() + "";





        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);

       // textToSpeech.synthesizeToFile(wakuUpText, myHashRender, destFileName);
    }


    @Override
    protected void onResume() {
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });
        super.onResume();
    }

    public void onPause() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
            textToSpeech = null;
        }
        super.onPause();
    }


    public void translate() {
        translateTextToEnglish("hindi");
    }

    public void translateText(FirebaseTranslator langTranslator) {

        if(readFile.length()>0){
            int len = readFile.length();
            final StringBuilder str = new StringBuilder();

            for(int i=0;i<len;i++){

                str.append(readFile.charAt(i));


                if (readFile.charAt(i)=='.'){
                    Log.i("seee",str.toString());





                    //translate source text to english
                    String st = str.toString().replace("\u0000", "");;
                    langTranslator.translate(st)
                            .addOnSuccessListener(
                                    new OnSuccessListener<String>() {
                                        @Override
                                        public void onSuccess(@NonNull String translatedText) {
                                            Log.d("fail",translatedText);
                                            englishText.append(translatedText+"| ");
                                            hindiString.append(translatedText);
                                        }
                                    })
                            .addOnFailureListener(
                                    new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            Toast.makeText(MainActivity.this,
                                                    "Problem in translating the text entered",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    });

                    /*
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                        }
                    }, 2000);*/
                    str.setLength(0);
                }



            }

        }else{


            InputStream fr= null;
            OutputStream fw=null;
            try {
                fr = getResources().openRawResource(
                        getResources().getIdentifier("test",
                                "raw", getPackageName()));

                Reader reader = new InputStreamReader(fr);
                //fr = new FileReader("C:\\Users\\saurabh\\Desktop\\test.txt");
            } catch (Exception e) {
                Log.d("msg","file not found");
                e.printStackTrace();
            }
            int i;
            try{
                final StringBuilder str = new StringBuilder();
                while((i=fr.read())!=-1)
                {
                    str.append((char)i);
                    if ((char)i=='.'){
                        Log.i("seee",str.toString());





                        //translate source text to english
                        String st = str.toString().replace("\u0000", "");;
                        langTranslator.translate(st)
                                .addOnSuccessListener(
                                        new OnSuccessListener<String>() {
                                            @Override
                                            public void onSuccess(@NonNull String translatedText) {
                                                Log.d("fail",translatedText);
                                                englishText.append(translatedText+"| ");
                                                hindiString.append(translatedText);
                                            }
                                        })
                                .addOnFailureListener(
                                        new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                                Toast.makeText(MainActivity.this,
                                                        "Problem in translating the text entered",
                                                        Toast.LENGTH_LONG).show();
                                            }
                                        });

                    /*
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                        }
                    }, 2000);*/
                        str.setLength(0);
                    }


                }

                fr.close();
            }catch (Exception e){
                Log.d("msg","file not found2");
            }


        }

    }


    public void downloadTranslatorAndTranslate(String langCode) {
        //get source language id from bcp code
        //int sourceLanguage = FirebaseTranslateLanguage.languageForLanguageCode(langCode);

        //create translator for source and target languages
        FirebaseTranslatorOptions options;

        if(language.equals("hindi")){

            options =
                    new FirebaseTranslatorOptions.Builder()
                            .setSourceLanguage(FirebaseTranslateLanguage.EN)
                            .setTargetLanguage(FirebaseTranslateLanguage.HI)
                            .build();

        }
        else{

            options =
                    new FirebaseTranslatorOptions.Builder()
                            .setSourceLanguage(FirebaseTranslateLanguage.EN)
                            .setTargetLanguage(FirebaseTranslateLanguage.MR)
                            .build();


        }






        final FirebaseTranslator langTranslator =
                FirebaseNaturalLanguage.getInstance().getTranslator(options);

        //download language models if needed
        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
                .requireWifi()
                .build();
        langTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void v) {
                                Log.d("translator", "downloaded lang  model");
                                //after making sure language models are available
                                //perform translation
                                translateText(langTranslator);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {

                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(MainActivity.this,
                                        "Problem in translating the text entered",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
    }

    public void translateTextToEnglish(String text) {

        Log.i("pp",text);
        //First identify the language of the entered text
        FirebaseLanguageIdentification languageIdentifier =
                FirebaseNaturalLanguage.getInstance().getLanguageIdentification();
        languageIdentifier.identifyLanguage(text)
                .addOnSuccessListener(
                        new OnSuccessListener<String>() {
                            @Override
                            public void onSuccess(@Nullable String languageCode) {
                                if (languageCode != "und") {
                                    Log.d("translator", "lang "+languageCode);
                                    //download translator for the identified language
                                    // and translate the entered text into english
                                    downloadTranslatorAndTranslate(languageCode);
                                } else {
                                    Toast.makeText(MainActivity.this,
                                            "Could not identify language of the text entered",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this,
                                        "Problem in identifying language of the text entered",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
    }
}
