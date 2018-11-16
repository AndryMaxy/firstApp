package com.example.andry.vocabulary.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.andry.vocabulary.database.WordDatabase.*;

public class WordDBHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "wordDB.db";

    public WordDBHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + WordTable.NAME + " (" +
                " _id integer primary key autoincrement, " +
                WordTable.Cols.UUID + ", " +
                WordTable.Cols.NATIVE_WORD + ", " +
                WordTable.Cols.FOREIGN_WORD + ", " +
                WordTable.Cols.PART_OF_SPEECH + ", " +
                WordTable.Cols.REMEMBERED + ", " +
                WordTable.Cols.DATE + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
