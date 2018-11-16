package com.example.andry.vocabulary;

import android.content.Context;
import android.preference.PreferenceManager;

class MyPreferences {
    private static final String PREF_ITEM_HEIGHT = "itemHeight";
    private static final String PREF_DIVIDER_HEIGHT = "dividerHeight";
    private static final String PREF_ITEMS_COUNT = "itemsCount";

    static int getItemHeight(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(PREF_ITEM_HEIGHT, 0);
    }

    static void setItemHeight(Context context, int itemHeight) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(PREF_ITEM_HEIGHT, itemHeight)
                .apply();
    }

    static int getDividerHeight(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(PREF_DIVIDER_HEIGHT, 0);
    }

    static void setDividerHeight(Context context, int dividerHeight) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(PREF_DIVIDER_HEIGHT, dividerHeight)
                .apply();
    }

    static int getItemsCount(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(PREF_ITEMS_COUNT, 0);
    }

    static void setItemsCount(Context context, int itemsCount) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(PREF_ITEMS_COUNT, itemsCount)
                .apply();
    }
}
