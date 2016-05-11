package com.example.josethomas.todoapp;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.josethomas.todoapp.db.TaskContract;
import com.example.josethomas.todoapp.db.TaskDBHelper;

import org.w3c.dom.ls.LSException;

public class MainActivity extends AppCompatActivity {

    private TaskDBHelper helper;
    private ListView taskListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        updateUI();
    }

    private void updateUI(){
        SQLiteDatabase sqlDB = new TaskDBHelper(MainActivity.this).getReadableDatabase();

        Cursor cursor = sqlDB.query(TaskContract.TABLE,
                new String[]{TaskContract.Columns._ID, TaskContract.Columns.TASK},
                null, null, null, null, null);

        ListAdapter listAdapter = new SimpleCursorAdapter(this,
                R.layout.task_view,
                cursor,
                new String[]{TaskContract.Columns.TASK},
                new int[] {R.id.taskTextView},
                0);

        taskListView = (ListView) this.findViewById(R.id.list);
        taskListView.setAdapter(listAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_task) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add a task");
            builder.setMessage("What do you want to do?");
            final EditText inputField = new EditText(this);
            builder.setView(inputField);
            builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String task =  inputField.getText().toString();
                    Log.d("MainActivity", task);

                    helper = new TaskDBHelper(MainActivity.this);
                    SQLiteDatabase db = helper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.clear();
                    values.put(TaskContract.Columns.TASK, task);
                    db.insertWithOnConflict(TaskContract.TABLE,null,values,SQLiteDatabase.CONFLICT_IGNORE);
                    updateUI();
                }
            });
            builder.setNegativeButton("Cancel", null);
            builder.create().show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void delete(View v){

        //Get the clicked button's text
        RelativeLayout vwParentRow = (RelativeLayout)v.getParent();
        TextView taskTextView = (TextView)vwParentRow.getChildAt(0);


       // View view = (View)v.getParent();
        //TextView taskTextView = (TextView)v.findViewById(R.id.taskTextView);


        String task = taskTextView.getText().toString();
        String sqlQuery = String.format("DELETE FROM %s WHERE %s = '%s'", TaskContract.TABLE, TaskContract.Columns.TASK, task);
        SQLiteDatabase db = new TaskDBHelper(MainActivity.this).getWritableDatabase();
        db.execSQL(sqlQuery);

        updateUI();

    }
}
