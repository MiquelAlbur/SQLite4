package com.example.sqlite4;


import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private MyDB db;
    private static int max;
    private ListView lv;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        max = 0;
        db = new MyDB(this);
        db.insertarDades();
        lv = findViewById(R.id.list);
        setAdapter();
        createAlert();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                builder.show();
                //startActivityForResult(i, 0);
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                db.deleteItem(position);
                setAdapter();
                return false;
            }
        });
    }

    public void setAdapter() {
        Cursor c = db.selectRecords();
        max = c.getCount();
        String[] from = new String[]{"name"};
        int[] to = new int[]{android.R.id.text1};
        SimpleCursorAdapter sca = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, c, from, to, 0);
        lv.setAdapter(sca);
    }
    protected void createAlert() {
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Add new Contact");

// Set up the input
        final EditText input = new EditText(this);
        final EditText input2 = new EditText(this);
        final EditText input3 = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        input2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        input3.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

// Add a TextView here for the "Title" label, as noted in the comments
        input.setHint("Name");
        layout.addView(input); // Notice this is an add method

// Add another TextView here for the "Description" label
        input2.setHint("Surname");
        layout.addView(input2); // Another add method

        input3.setHint("Telephone Number");
        layout.addView(input3);

        // Again this is a set method, not add
        builder.setView(layout);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.createRecords(max + 1, input.getText().toString(),input2.getText().toString(), input3.getText().toString());
                setAdapter();
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }
}
