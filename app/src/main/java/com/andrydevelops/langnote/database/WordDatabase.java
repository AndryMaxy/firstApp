package com.andrydevelops.langnote.database;

public class WordDatabase {

    public static final class WordTable{
        public static final String NAME = "words";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String NATIVE_WORD = "native_word";
            public static final String NATIVE_WORD_2 = "native_word_2";
            public static final String FOREIGN_WORD = "foreign_word";
            public static final String PART_OF_SPEECH = "part_of_speech";
            public static final String REMEMBERED = "remembered";
            public static final String DATE = "date";
        }
    }


}
