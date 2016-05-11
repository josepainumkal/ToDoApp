package com.example.josethomas.todoapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by JoseThomas on 3/29/2016.
 */
public class TaskDBHelper extends SQLiteOpenHelper {

    public TaskDBHelper(Context context){
        super(context,TaskContract.DB_NAME,null,TaskContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlQuery = String.format("CREATE TABLE %s ( _id INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT )", TaskContract.TABLE, TaskContract.Columns.TASK);
        Log.d("TaskDBHelper","Query to form table"+sqlQuery);
        db.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TaskContract.TABLE);
        onCreate(db);
    }
}
