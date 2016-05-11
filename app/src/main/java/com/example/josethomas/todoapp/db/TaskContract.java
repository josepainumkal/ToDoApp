package com.example.josethomas.todoapp.db;

import android.provider.BaseColumns;

/**
 * Created by JoseThomas on 3/29/2016.
 */
public class TaskContract {
    public static final String DB_NAME = "com.example.josethomas.todoapp.db.tasks";
    public static final int DB_VERSION = 1;
    public static final String TABLE = "tasks";

    public class Columns{
        public static final String TASK = "task";
        public static final String _ID = BaseColumns._ID;
    }
}
