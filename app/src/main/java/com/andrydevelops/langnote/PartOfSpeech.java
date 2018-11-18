package com.andrydevelops.langnote;

public enum PartOfSpeech {

    NOUN,
    VERB,
    ADJECTIVE,
    ADVERB,
    OTHER;

    public static String format(PartOfSpeech partOfSpeech){
        String name = partOfSpeech.name();
        return name.substring(0, 1).toUpperCase() + name.substring(1, name.length()).toLowerCase();
    }
}
