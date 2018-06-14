package com.example.birusksterk.interessinew;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by BiruskSterk on 24.07.2017.
 */

public class HakkindaSayfasi extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hakkindasayfasi);

    }

    //Link yönlendirmeleri.

    public void tweet(View view)
    {
        Intent tweetIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/nottecode/"));
        startActivity(tweetIntent);
    }

    public void insta(View view)
    {
        Intent instaIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/nottecode/"));
        startActivity(instaIntent);
    }


    public void web(View view)
    {
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/nottecode/"));
        startActivity(webIntent);
    }

    public void notte(View view)
    {
        Intent notteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.nottecode.com/"));
        startActivity(notteIntent);
    }
}
