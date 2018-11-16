package com.example.andry.vocabulary.dialogFragments.suredialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.example.andry.vocabulary.OnUpdateListener;
import com.example.andry.vocabulary.R;
import com.example.andry.vocabulary.Word;
import com.example.andry.vocabulary.WordLab;
import com.example.andry.vocabulary.WordListFragment;

public class SureDeletePair extends SureDialogFragment {

    private OnUpdateListener mOnUpdateListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mOnUpdateListener = (OnUpdateListener) context;
    }

    @Override
    public String writeMessage() {
        return getString(R.string.sure_dialog_delete_pair);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case Dialog.BUTTON_POSITIVE:
                Word word = getArguments().getParcelable(WordListFragment.ARG_WORD);
                WordLab.getWordLab(getActivity()).deleteWord(word);
                mOnUpdateListener.updateUI();
                Toast.makeText(getActivity(), R.string.pair_deleted, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mOnUpdateListener = null;
    }
}