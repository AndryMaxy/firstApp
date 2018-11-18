package com.andrydelops.langnote;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

public class Word implements Parcelable {
    private static final String TAG = "WordWord";

    private UUID mId;
    private String mNativeWord;
    private String mNativeWord2;
    private String mForeignWord;
    private PartOfSpeech mPartOfSpeech;
    private boolean isRemembered;
    private Date mDate;

    public Word(UUID uuid){
        mId = uuid;
        mDate = new Date();
    }

    public Word(String foreignWord, String nativeWord, PartOfSpeech partOfSpeech) {
        mId = UUID.randomUUID();
        mNativeWord = nativeWord;
        mForeignWord = foreignWord;
        mPartOfSpeech = partOfSpeech;
        isRemembered = false;
        mDate = new Date();
    }

    public Word(Parcel in) {
        mId = (UUID) in.readValue(UUID.class.getClassLoader());
        mNativeWord = in.readString();
        mForeignWord = in.readString();
        mPartOfSpeech = PartOfSpeech.valueOf(in.readString());
        isRemembered = (boolean) in.readValue(Boolean.class.getClassLoader());
        mDate = (Date) in.readValue(Date.class.getClassLoader());
    }

    public UUID getId() {
        return mId;
    }

    public String getNativeWord() {
        return mNativeWord;
    }

    public void setNativeWord(String nativeWord) {
        mNativeWord = nativeWord;
    }

    public String getNativeWord2() {
        return mNativeWord2;
    }

    public void setNativeWord2(String nativeWord2) {
        mNativeWord2 = nativeWord2;
    }

    public String getForeignWord() {
        return mForeignWord;
    }

    public void setForeignWord(String foreignWord) {
        mForeignWord = foreignWord;
    }

    PartOfSpeech getPartOfSpeech() {
        return mPartOfSpeech;
    }

    public void setPartOfSpeech(PartOfSpeech partOfSpeech) {
        mPartOfSpeech = partOfSpeech;
    }

    public boolean isRemembered() {
        return isRemembered;
    }

    public void setRemembered(boolean remembered) {
        isRemembered = remembered;
    }

    public Date getDate() {
        return mDate;
    }

    String getFormattedDate(){
        return DateFormat.getDateInstance(DateFormat.SHORT).format(mDate);
    }

    public void setDate(Date date) {
        mDate = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mId);
        dest.writeString(mNativeWord);
        dest.writeString(mForeignWord);
        dest.writeString(mPartOfSpeech.toString());
        dest.writeValue(isRemembered);
        dest.writeValue(mDate);
    }

    public static final Parcelable.Creator<Word> CREATOR = new Parcelable.Creator<Word>(){
        @Override
        public Word createFromParcel(Parcel in) {
            return new Word(in);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };
}
