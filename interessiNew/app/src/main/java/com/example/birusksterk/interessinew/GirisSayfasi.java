package com.example.birusksterk.interessinew;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Created by BiruskSterk on 24.07.2017.
 */

public class GirisSayfasi extends ActionBarActivity {

    Button giris;
    EditText kulad_input, sifre_input;
    String mail, sifre, sonuc, tarih;
    String hata_mesaji = "";
    String URL_POST = "http://192.168.2.5/interessiWamp/login3.php";
    Boolean hata = false;
    PostClass post = new PostClass();
    JSONObject cevap = null;
    public String donus_hata_mesaji;

    String EmailHolder, PasswordHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;
    String TempPassword = "NOT_FOUND" ;
    String UserName;
    URL url;
    public static final String UserEmail = "";
    public static final String userName="";



    //Button gir;
    //EditText nick_gir, pass_gir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_girissayfasi);

        kulad_input = (EditText) findViewById(R.id.KullaniciAdiText);
        sifre_input = (EditText) findViewById(R.id.SifreText);
        giris = (Button) findViewById(R.id.GirisButton);

        sqLiteHelper = new SQLiteHelper(this);

        giris.setOnClickListener(new View.OnClickListener() {//giri� butonu t�kland���nda

            public void onClick(View v) {
                CheckEditTextStatus();
                LoginFunction();
            }
        });
    }

    public void LoginFunction(){

        if(EditTextEmptyHolder) {

            sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();
            cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, null, " " + SQLiteHelper.Table_Column_4_Email + "=?", new String[]{EmailHolder}, null, null, null);

            while (cursor.moveToNext()) {

                if (cursor.isFirst()) {

                    cursor.moveToFirst();
                    TempPassword = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_5_Password));
                    cursor.close();
                }
            }

            CheckFinalResult();

        }
        else {
            Toast.makeText(GirisSayfasi.this,"Lütfen mail adresi ve şifrenizi girin.",Toast.LENGTH_LONG).show();

        }

    }

    public void CheckEditTextStatus(){
        EmailHolder = kulad_input.getText().toString();
        PasswordHolder = sifre_input.getText().toString();
        if( TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)){

            EditTextEmptyHolder = false ;

        }
        else {

            EditTextEmptyHolder = true ;
        }
    }
    public void CheckFinalResult(){

        if(TempPassword.equalsIgnoreCase(PasswordHolder))
        {

            Toast.makeText(GirisSayfasi.this,"Giriş Başarılı !",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(GirisSayfasi.this, Anasayfa.class);
            intent.putExtra(UserEmail,EmailHolder );
            startActivity(intent);


        }
        else {

            Toast.makeText(GirisSayfasi.this,"Mail adresi ya da şifre yanlış, lütfen tekrar deneyin..",Toast.LENGTH_LONG).show();

        }
        TempPassword = "NOT_FOUND" ;

    }
}
