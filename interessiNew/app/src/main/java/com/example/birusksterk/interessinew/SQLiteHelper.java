package com.example.birusksterk.interessinew;

/**
 * Created by BiruskSterk on 5/21/2018.
 */

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class SQLiteHelper extends SQLiteOpenHelper {


    static String DATABASE_NAME="UserDataBase";

    public static final String TABLE_NAME="UserTable";
    public static final String Table_Column_ID="id";
    public static final String Table_Column_1_Name="name";
    public static final String Table_Column_2_Sname="sname";
    public static final String Table_Column_3_Nick="nick";
    public static final String Table_Column_4_Email="email";
    public static final String Table_Column_5_Password="password";
    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase database) {
        String CREATE_TABLE="CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("+Table_Column_ID+" INTEGER PRIMARY KEY, "+Table_Column_1_Name+" VARCHAR, "+Table_Column_2_Sname+" VARCHAR, "+Table_Column_3_Nick+" VARCHAR, "+Table_Column_4_Email+" VARCHAR, "+Table_Column_5_Password+" VARCHAR)";
        database.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }

}