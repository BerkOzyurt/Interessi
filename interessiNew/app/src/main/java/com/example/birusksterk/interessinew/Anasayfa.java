package com.example.birusksterk.interessinew;

/**
 * Created by BiruskSterk on 10/17/2017.
 */

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by BiruskSterk on 01.08.2017.
 */

public class Anasayfa extends AppCompatActivity {
    Button profil, arkadas, ilgialan, mesaj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anasayfa);
        Profilim();
        Arkadaslarim();
        IlgıAlan();
        Mesaj();
    }

    public void Profilim(){
        profil = (Button)findViewById(R.id.Profilim);

        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Anasayfa.this,Profil.class);
                startActivity(intent);
            }
        });
    }

    public void Arkadaslarim(){
        arkadas = (Button)findViewById(R.id.Arkadaslar);

        arkadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Toast.makeText(Anasayfa.this,"Çıkış işlemi başarılı...", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(Anasayfa.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void IlgıAlan(){
        ilgialan = (Button)findViewById(R.id.IlgiAlanlari);

        ilgialan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(Anasayfa.this,IlgiAlanlari.class);
                startActivity(intent3);
            }
        });
    }

    public void Mesaj(){
        mesaj = (Button)findViewById(R.id.Messages);

        mesaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent inten4 = new Intent(Anasayfa.this,Mesajlar.class);
                startActivity(inten4);*/

                Intent intent = new Intent(Intent.ACTION_MAIN, null);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                final ComponentName cn = new ComponentName("com.mobilhanem.firebasechatandroidapp", "com.mobilhanem.firebasechatandroidapp.MainActivity");
                intent.setComponent(cn);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try
                {
                    startActivity(intent);
                }catch(ActivityNotFoundException e){

                }
            }
        });
    }
}
