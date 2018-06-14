package com.example.birusksterk.interessinew;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by BiruskSterk on 01.08.2017.
 */

public class Profil extends AppCompatActivity {


    EditText ad, soyad, kullanici_adi, mail, sifre;
    Button bilgi;
    TextView teviev;
    ListView listem1;
    private TextView txtAd, txtSoyad, txtKullAd, txtEmail, txtSifrad, veri;
    private ProgressDialog progress;
    public JSONParser jsp = new JSONParser();
    public ProgressDialog pDialog;
    List<String> veriler = new ArrayList<String>();
    String getirServis = "http://192.168.1.36/interessiWamp/getir.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);


        txtAd = (TextView) findViewById(R.id.name);
        txtSoyad = (TextView) findViewById(R.id.Sname);
        txtKullAd = (TextView) findViewById(R.id.Nikname);
        txtEmail = (TextView) findViewById(R.id.Mailname);
        txtSifrad = (TextView) findViewById(R.id.Pname);
        veri = (TextView) findViewById(R.id.gir);

        bilgi = (Button) findViewById(R.id.BilgiButton);


        ad = (EditText) findViewById(R.id.AdText);
        soyad = (EditText) findViewById(R.id.SoyadText);
        kullanici_adi = (EditText) findViewById(R.id.KullaniciAdiText);
        mail = (EditText) findViewById(R.id.MailText);
        sifre = (EditText) findViewById(R.id.SifreText);


        bilgi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Getir().execute(getirServis);
            }


        });

    }


    public class Ekle extends AsyncTask<String, Void, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Profil.this);
            pDialog.setMessage("Ekleniyor.."); //görünecek mesaj
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
            int sonuc = 0;
            List<NameValuePair> sendParams = new ArrayList<NameValuePair>();
            sendParams.add(new BasicNameValuePair("eklenecekVeri", ad.getText().toString()));
            JSONObject myObject = jsp.makeHttpRequest(params[0], "POST", sendParams);
            try {
                sonuc = myObject.getInt("basarilimi");
                return sonuc;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            pDialog.dismiss();

            if (result == 1) {
                Toast.makeText(getApplicationContext(), "Bu isim zaten kayıtlı.", Toast.LENGTH_SHORT).show();
            } else if (result == 2) {
                Toast.makeText(getApplicationContext(), "Kayıt işlemi başarılı.", Toast.LENGTH_SHORT).show();
            } else if (result == 3) {
                Toast.makeText(getApplicationContext(), "Kayıt işlemi başarılı değil.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public class Getir extends AsyncTask<String, Void, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Profil.this);
            pDialog.setMessage("Yenileniyor.."); //görünecek mesaj
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
            int sonuc = 2;
            List<NameValuePair> sendParams = new ArrayList<NameValuePair>();
            //getirme işleminde herhangi bir veri göndermeyeceğiz o yuzden sendparams boş gönderiyoruz.

            //işlem sonunda bir json objesi dönecek bu onbje içerisinde isimelrin bulunduğu
            //bir json array ve basarilimi ifadesini tutacak eğer başarılı ise verileri getireceğiz.
            //yine params[0]  adresi göndereceğiz.
            JSONObject myObject = jsp.makeHttpRequest(params[0], "POST", sendParams);
            try {
                sonuc = myObject.getInt("basarilimi"); //web servisinde belirttiğimiz tag
                //1 basarili 0 başarısız demiştik web servisimizde
                if (sonuc == 1) {
                    //listeyi temizleyelim;
                    veriler.clear();

                    JSONArray tempArray = myObject.getJSONArray("donenVeriler");
                    //sunucudan dönen 'donenVeriler' tag lı diziye ulaşıyor ve onu dizimize atıyoruz. Burada gelen
                    //her 1 nesne json objesidir. ve bu nesneler içerisinde de servisimizde aşağıdaki gibi ifade ettiğimiz


                    for (int i = 0; i < tempArray.length(); i++) {
                        //ilk başta oluşturduğumuz listeye her adımda gelen isme tag ile ulaşarak gönderiyoruz.
                        veriler.add(tempArray.getJSONObject(i).getString("tag"));
                    }
                }
                return sonuc;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return sonuc;
        }

        @Override
        protected void onPostExecute(Integer result) {
            // Burada işlem başarılı ise artık listemizde verileri göstereceğiz.Ve progress diyalogumuzu kapatacagız.
            super.onPostExecute(result);
            pDialog.dismiss();
            if (result == 1) {
                //ListView'da verileri gösterebilmek için öncelikle adapter e ihtiyacımız var. Basit bir adapter yazalım.
                //MainActivity içerisinde göüntülenecek, simple list item turunde yani sadeceyazı seklinde görüntülenecek,
                //içerisine veriler listesini alacak şekilde bir adapter yazdık.
                ArrayAdapter<String> adapter = new ArrayAdapter<>(Profil.this, android.R.layout.simple_list_item_1, veriler);
                listem1.setAdapter(adapter);
            } else {
                Toast.makeText(getApplicationContext(), "kayıtlar getirilemedi.", Toast.LENGTH_SHORT).show();
            }
        }

    }
}





