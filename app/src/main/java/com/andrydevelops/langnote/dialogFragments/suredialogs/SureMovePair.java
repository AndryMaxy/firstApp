package com.andrydevelops.langnote.dialogFragments.suredialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.andrydevelops.langnote.OnUpdateListener;
import com.example.andry.www.R;
import com.andrydevelops.langnote.Word;
import com.andrydevelops.langnote.WordLab;
import com.andrydevelops.langnote.WordListFragment;

import java.util.Date;

public class SureMovePair extends SureDialogFragment {

    private OnUpdateListener mOnUpdateListener;
    private Word mWord;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mOnUpdateListener = (OnUpdateListener) context;
    }

    @Override
    public String writeMessage() {
        mWord = getArguments().getParcelable(WordListFragment.ARG_WORD);
        if (mWord.isRemembered()) {
            return getString(R.string.sure_dialog_move_to_learn);
        } else return getString(R.string.sure_dialog_move_to_learned);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case Dialog.BUTTON_POSITIVE:
                mWord.setRemembered(!mWord.isRemembered());
                mWord.setDate(new Date());
                WordLab.getWordLab(getActivity()).updateWord(mWord);
                mOnUpdateListener.updateUI();
                Toast.makeText(getActivity(), R.string.pair_moved, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnUpdateListener = null;
    }
}
