package com.example.sqlite4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyDB{

    private MyDatabaseHelper dbHelper;

    private SQLiteDatabase database;


    public final static String CON_TABLE="Contactes"; // name of table
    public final static String CON_ID="_id"; // id
    public final static String CON_NAME="name";  // name
    public final static String CON_COG="cognom";
    public final static String CON_NUM="telefon";

    /**
     *
     * @param context
     */
    public MyDB(Context context){
        dbHelper = new MyDatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }


    public long createRecords(int id, String name,String cog,String tel){
        ContentValues values = new ContentValues();
        values.put(CON_ID, id);
        values.put(CON_NAME, name);
        values.put(CON_COG,cog);
        values.put(CON_NUM,tel);
        return database.insert(CON_TABLE, null, values);
    }

    public void deleteItem(int s){
        database.delete(CON_TABLE, "_id = " + s,null);
    }

    public Cursor selectRecords() {
        String[] cols = new String[] {CON_ID,CON_NAME,CON_COG,CON_NUM};
        Cursor mCursor = database.query(true, CON_TABLE,cols,null
                , null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor; // iterate to get each value.
    }

    public void insertarDades(){
        createRecords(selectRecords().getCount() + 1,"Text","Meme","654452780");
    }
}