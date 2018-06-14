package com.example.birusksterk.interessinew;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by BiruskSterk on 24.07.2017.
 */

public class KayitSayfasi extends AppCompatActivity {

    EditText name, sname, nick, Mail, pass;
    Button Kayit;

    String ad,soyad,kullanici_adi,mail,sifre;
    InputStream is = null;
    String exceptionMessage = "Veritabanı bağlantısında bir hata  meydana geldi. Lütfen tekrar deneyiniz.";
    String successMessage = "Kayıt başarıyla oluşturuldu!";

    public static String url = "http://192.168.2.65/interessiWamp/insert.php";

    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder ;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    String F_Result = "Not_Found";
    String NameHolder, EmailHolder, PasswordHolder, SnameHolder, NickHolder;
    Boolean EditTextEmptyHolder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayitsayfasi);

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        name = (EditText) findViewById(R.id.AdText);
        sname = (EditText) findViewById(R.id.SoyadText);
        nick = (EditText) findViewById(R.id.KullaniciAdiText);
        Mail = (EditText) findViewById(R.id.MailText);
        pass = (EditText) findViewById(R.id.SifreText);

        Kayit = (Button) findViewById(R.id.KayitButton);

        sqLiteHelper = new SQLiteHelper(this);


        Kayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad = name.getText().toString();
                soyad = sname.getText().toString();
                kullanici_adi = nick.getText().toString();
                mail = Mail.getText().toString();
                sifre = pass.getText().toString();

                //Yedek olarak SQLite kayıtları al.
                SQLiteDataBaseBuild();
                SQLiteTableBuild();
                CheckEditTextStatus();
                CheckingEmailAlreadyExistsOrNot();
                Confirm();
                EmptyEditTextAfterDataInsert();


                //WampServer'a kaydet.
                if (ad.equals("") ||
                        soyad.equals("") ||
                        kullanici_adi.equals("") ||
                        mail.equals("") ||
                        sifre.equals("")) {
                    String msg = "Lütfen boş alan bırakmayınız.";
                    name.setText("");
                    sname.setText("");
                    nick.setText("");
                    Mail.setText("");
                    pass.setText("");
                } else {

                    List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
                    nameValuePairList.add(new BasicNameValuePair("ad", ad));
                    nameValuePairList.add(new BasicNameValuePair("soyad", soyad));
                    nameValuePairList.add(new BasicNameValuePair("kullanici_adi", kullanici_adi));
                    nameValuePairList.add(new BasicNameValuePair("mail", mail));
                    nameValuePairList.add(new BasicNameValuePair("sifre", sifre));

                    try {
                        HttpClient httpClient = new DefaultHttpClient();
                        HttpPost httpPost = new HttpPost(url);
                        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList));
                        HttpResponse httpResponse = httpClient.execute(httpPost);
                        HttpEntity httpEntity = httpResponse.getEntity();
                        is = httpEntity.getContent();
                        name.setText("");
                        sname.setText("");
                        nick.setText("");
                        Mail.setText("");
                        pass.setText("");
                        Toast.makeText(getApplicationContext(), "Kayıt İşlemi Başarılı", Toast.LENGTH_SHORT).show();
                        is.close();

                        Intent intent = new Intent(KayitSayfasi.this, GirisSayfasi.class);
                        startActivity(intent);
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(), "Bir Hata Oluştu", Toast.LENGTH_SHORT).show();
                    }
                }


            }


                /*String method = "register";
                LogAndReg logandreg = new LogAndReg(getApplicationContext());
                logandreg.execute(method,ad,soyad,kullanici_adi,mail,sifre);
                finish();

                Intent intent = new Intent(KayitSayfasi.this,Anasayfa.class);
                startActivity(intent);*/


        });

    }

    //WebServer patlarsa bilgilerin yedeğini yerelde tutmak için SQLite fonksiyonları.
    public void Confirm(){

    }

    public void SQLiteDataBaseBuild(){

        sqLiteDatabaseObj = openOrCreateDatabase(SQLiteHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }

    public void SQLiteTableBuild() {

        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + SQLiteHelper.TABLE_NAME + "(" + SQLiteHelper.Table_Column_ID + " PRIMARY KEY AUTOINCREMENT NOT NULL, " + SQLiteHelper.Table_Column_1_Name + " VARCHAR, " + SQLiteHelper.Table_Column_2_Sname + " VARCHAR, " + SQLiteHelper.Table_Column_3_Nick + " VARCHAR, " + SQLiteHelper.Table_Column_4_Email + " VARCHAR, " + SQLiteHelper.Table_Column_5_Password + " VARCHAR);");

    }

    public void InsertDataIntoSQLiteDatabase(){
        if(EditTextEmptyHolder == true)
        {
            SQLiteDataBaseQueryHolder = "INSERT INTO "+SQLiteHelper.TABLE_NAME+" (name,email,password) VALUES('"+NameHolder+"', '"+EmailHolder+"', '"+PasswordHolder+"');";
            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);
            sqLiteDatabaseObj.close();
            Toast.makeText(KayitSayfasi.this,"Kayıt Başarılı!", Toast.LENGTH_LONG).show();

        }
        else {
            Toast.makeText(KayitSayfasi.this,"Lütfen gerekli alanları doldurunuz..", Toast.LENGTH_LONG).show();

        }

    }
    public void EmptyEditTextAfterDataInsert(){

        name.getText().clear();
        sname.getText().clear();
        nick.getText().clear();
        Mail.getText().clear();
        pass.getText().clear();

    }
    public void CheckEditTextStatus(){
        NameHolder = name.getText().toString();
        SnameHolder = sname.getText().toString();
        NickHolder = nick.getText().toString();
        EmailHolder = Mail.getText().toString();
        PasswordHolder = pass.getText().toString();

        if(TextUtils.isEmpty(NameHolder) || TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder) || TextUtils.isEmpty(SnameHolder) || TextUtils.isEmpty(NickHolder)){

            EditTextEmptyHolder = false ;

        }
        else {

            EditTextEmptyHolder = true ;
        }
    }
    public void CheckingEmailAlreadyExistsOrNot(){

        sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();
        cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, null, " " + SQLiteHelper.Table_Column_4_Email + "=?", new String[]{EmailHolder}, null, null, null);

        while (cursor.moveToNext()) {

            if (cursor.isFirst()) {

                cursor.moveToFirst();
                F_Result = "Email Found";
                cursor.close();
            }
        }
        CheckFinalResult();

    }

    public void CheckFinalResult(){
        if(F_Result.equalsIgnoreCase("Email Found"))
        {
            Toast.makeText(KayitSayfasi.this,"Email Already Exists",Toast.LENGTH_LONG).show();

        }
        else {
            InsertDataIntoSQLiteDatabase();

        }

        F_Result = "Not_Found" ;

    }

}

