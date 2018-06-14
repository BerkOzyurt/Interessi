package com.example.birusksterk.interessinew;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by BiruskSterk on 01.08.2017.
 */

public class Mesajlar extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesajlar);


        //İnteressiChat'e geçiş için gerekli kodlar!!!!!!!
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        final ComponentName cn = new ComponentName("com.android.settings", "com.example.birusksterk.interessichat;");
        intent.setComponent(cn);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try
        {
            startActivity(intent);
        }catch(ActivityNotFoundException e){
            Toast.makeText(context,"Activity Not Found",Toast.LENGTH_SHORT).show()
        }

    }
}
