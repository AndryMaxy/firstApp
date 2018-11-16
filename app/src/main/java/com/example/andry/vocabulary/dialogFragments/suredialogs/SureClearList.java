package com.example.andry.vocabulary.dialogFragments.suredialogs;

import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import com.example.andry.vocabulary.MainTab;
import com.example.andry.vocabulary.PartOfSpeech;
import com.example.andry.vocabulary.R;
import com.example.andry.vocabulary.Word;
import com.example.andry.vocabulary.WordLab;

import java.util.ArrayList;

public class SureClearList extends SureDialogFragment {

    @Override
    public String writeMessage() {
        return getString(R.string.sure_dialog_clear_list, getArguments().getString(MainTab.ARG_PART_OF_SPEECH));
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Log.i("www", "which: " + which);
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                PartOfSpeech pos = PartOfSpeech.valueOf(getArguments().getString(MainTab.ARG_PART_OF_SPEECH));
                boolean isRemembered = getArguments().getBoolean(MainTab.ARG_IS_REMEMBERED);
                ArrayList<Word> list = WordLab.getWordLab(getActivity()).getWords(pos, isRemembered);
                for (Word word : list) {
                    WordLab.getWordLab(getActivity()).deleteWord(word);
                }
                Toast.makeText(getContext(), R.string.list_cleared, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
