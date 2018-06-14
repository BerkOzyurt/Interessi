package com.example.birusksterk.interessinew;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button girisButton, kayitButton, hakkindaButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Giris();
        Kayit();
        Hakkinda();
    }

    public void Giris() {
        girisButton = (Button) findViewById(R.id.giris_button);

        girisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, GirisSayfasi.class);
                startActivity(intent);

            }
        });
    }

    public void Kayit(){
        kayitButton = (Button)findViewById(R.id.kayit_button);

        kayitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this,KayitSayfasi.class);
                startActivity(intent2);
            }
        });
    }

    public void Hakkinda(){
        hakkindaButton = (Button)findViewById(R.id.hakkinda_button);

        hakkindaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(MainActivity.this,HakkindaSayfasi.class);
                startActivity(intent3);
            }
        });
    }
}
