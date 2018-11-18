package com.andrydevelops.langnote.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.andrydevelops.langnote.PartOfSpeech;
import com.andrydevelops.langnote.Word;

import java.util.Date;
import java.util.UUID;

public class WordCursorWrapper extends CursorWrapper {

    public WordCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Word getWord(){
        String uuid = getString(getColumnIndex(WordDatabase.WordTable.Cols.UUID));
        String nativeWord = getString(getColumnIndex(WordDatabase.WordTable.Cols.NATIVE_WORD));
        String nativeWord2 = getString(getColumnIndex(WordDatabase.WordTable.Cols.NATIVE_WORD_2));
        String foreignWord = getString(getColumnIndex(WordDatabase.WordTable.Cols.FOREIGN_WORD));
        String part_of_speech = getString(getColumnIndex(WordDatabase.WordTable.Cols.PART_OF_SPEECH));
        boolean isRemembered = getInt(getColumnIndex(WordDatabase.WordTable.Cols.REMEMBERED)) == 1;
        long date = getLong(getColumnIndex(WordDatabase.WordTable.Cols.DATE));

        Word word = new Word(UUID.fromString(uuid));
        word.setNativeWord(nativeWord);
        word.setNativeWord2(nativeWord2);
        word.setForeignWord(foreignWord);
        word.setPartOfSpeech(PartOfSpeech.valueOf(part_of_speech));
        word.setRemembered(isRemembered);
        word.setDate(new Date(date));
        return word;
    }
}
