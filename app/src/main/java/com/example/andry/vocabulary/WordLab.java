package com.example.andry.vocabulary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.andry.vocabulary.database.*;
import com.example.andry.vocabulary.database.WordDatabase.WordTable;

import java.util.ArrayList;

public class WordLab {
    private static final String TAG = "WordLab";

    private static WordLab sWordLab;

    private Context mContext;
    private SQLiteDatabase mSQLiteDatabase;

    private WordLab(Context context) {
        mContext = context.getApplicationContext();
        mSQLiteDatabase = new WordDBHelper(mContext).getWritableDatabase();
    }

    public static WordLab getWordLab(Context context) {
        if (sWordLab == null) {
            sWordLab = new WordLab(context);
        }
        return sWordLab;
    }

    public ArrayList<Word> getWords(PartOfSpeech partOfSpeech, boolean isRemembered){
        ArrayList<Word> words = new ArrayList<>();
        String remembered = isRemembered ? "1" : "0";
        String whereClause = WordTable.Cols.PART_OF_SPEECH + " = ? and " + WordTable.Cols.REMEMBERED + " = ?";
        Log.i(TAG, "pos: = " + " rem: = " + remembered);
        String[] whereArgs = {partOfSpeech.toString(), remembered};
        try (WordCursorWrapper cursorWrapper = queryWords(whereClause, whereArgs)) {

            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                words.add(0, cursorWrapper.getWord());
                cursorWrapper.moveToNext();
            }
        }
        return words;
    }

    public void addWord(Word word) {
        ContentValues values = getContentValues(word);
        mSQLiteDatabase.insert(WordTable.NAME, null, values);
    }

    public void deleteWord(Word word) {
        mSQLiteDatabase.delete(WordTable.NAME, WordTable.Cols.UUID + " = ?", new String[] {word.getId().toString()});
    }

    public void updateWord(Word word) {
        ContentValues values = getContentValues(word);
        mSQLiteDatabase.update(WordTable.NAME, values, WordTable.Cols.UUID + " = ?", new String[]{word.getId().toString()});
    }

    private static ContentValues getContentValues(Word word) {
        ContentValues values = new ContentValues();
        values.put(WordTable.Cols.UUID, word.getId().toString());
        values.put(WordTable.Cols.NATIVE_WORD, word.getNativeWord());
        values.put(WordTable.Cols.FOREIGN_WORD, word.getForeignWord());
        values.put(WordTable.Cols.PART_OF_SPEECH, word.getPartOfSpeech().toString());
        values.put(WordTable.Cols.REMEMBERED, word.isRemembered() ? "1" : "0");
        values.put(WordTable.Cols.DATE, word.getDate().getTime());
        return values;
    }

    private WordCursorWrapper queryWords(String whereClause, String[] whereArgs) {
        Cursor cursor = mSQLiteDatabase.query(
                WordTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new WordCursorWrapper(cursor);
    }
}
