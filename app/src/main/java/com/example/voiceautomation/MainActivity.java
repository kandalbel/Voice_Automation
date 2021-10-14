package com.example.voiceautomation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Locale;

import static android.Manifest.permission.RECORD_AUDIO;

public class MainActivity extends AppCompatActivity {

    private SpeechRecognizer speechRecognizer;
    private Intent intentRecogniser;
    private TextView textview;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        voiceautomation();

        button=(Button)findViewById(R.id.buton);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void voiceautomation() {
        Intent voice=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        voice.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        voice.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        voice.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to open");
        startActivityForResult(voice, 1);

    }

    private void openWhatsApp(String number) {
        try {
            number = number.replace(" ", "").replace("+", "");

            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setComponent(new ComponentName("com.whatsapp","com.whatsapp.Conversation"));
            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(number)+"@s.whatsapp.net");

            startActivity(Intent.createChooser(sendIntent, "Compartir en")
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

        } catch(Exception e) {
            Log.e("WS", "ERROR_OPEN_MESSANGER"+e.toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode== RESULT_OK && data !=null){
            ArrayList<String> arrayList=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (arrayList.get(0).toString().equals("open WhatsApp")){
              openWhatsApp("+90 5345524812");
            }

            else if (arrayList.get(0).toString().equals("open camera")){
                Intent camera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(camera);
            }

            else if (arrayList.get(0).toString().equals("open YouTube")){
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/")));
            }
            else if (arrayList.get(0).toString().equals("open Facebook")){
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/")));
            }
            else if (arrayList.get(0).toString().equals("open Twitter")){
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/login")));
            }

        }
    }
    }

