package com.andrydelops.langnote;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.andrydelops.langnote.dialogFragments.NewWordDialogFragment;
import com.example.andry.www.R;

import java.util.Objects;

public class LearnTab extends MainTab {

    private boolean isRemembered = false;

    public static LearnTab newInstance() {
        return new LearnTab();
    }

    @Override
    protected boolean isRemembered() {
        return isRemembered;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_main_to_learn;
    }

    @Override
    public void manipulate(PartOfSpeech partOfSpeech) {
        Bundle args = new Bundle();
        FragmentManager fm = getFragmentManager();
        NewWordDialogFragment dialogFragment = new NewWordDialogFragment();
        args.putSerializable(ARG_PART_OF_SPEECH, partOfSpeech.toString());
        dialogFragment.setArguments(args);
        dialogFragment.show(Objects.requireNonNull(fm), null);
    }
}
