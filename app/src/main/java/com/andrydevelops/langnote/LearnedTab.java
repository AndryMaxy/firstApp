package com.andrydelops.langnote;

import android.os.Bundle;
import android.widget.Toast;

import com.andrydelops.langnote.dialogFragments.suredialogs.SureClearList;
import com.andrydelops.langnote.dialogFragments.suredialogs.SureDialogFragment;
import com.example.andry.www.R;

public class LearnedTab extends MainTab {

    private boolean isRemembered = true;


    public static LearnedTab newInstance(){
        return new LearnedTab();
    }

    @Override
    protected boolean isRemembered() {
        return isRemembered;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_main_learned;
    }

    @Override
    protected void manipulate(PartOfSpeech partOfSpeech) {
        clearList(partOfSpeech);
    }

    private void clearList(PartOfSpeech partOfSpeech){
        if (WordLab.getWordLab(getActivity()).getWords(partOfSpeech, isRemembered).size() > 0) {
            SureDialogFragment sure = new SureClearList();
            Bundle args = new Bundle();
            args.putString(ARG_PART_OF_SPEECH, partOfSpeech.toString());
            args.putBoolean(ARG_IS_REMEMBERED, isRemembered);
            sure.setArguments(args);
            assert getFragmentManager() != null;
            sure.show(getFragmentManager(), null);
        } else Toast.makeText(getActivity(), getString(R.string.list_is_empty, PartOfSpeech.format(partOfSpeech)), Toast.LENGTH_SHORT).show();
    }
}
