package com.example.andry.vocabulary.dialogFragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andry.vocabulary.LearnTab;
import com.example.andry.vocabulary.PartOfSpeech;
import com.example.andry.vocabulary.R;
import com.example.andry.vocabulary.Word;
import com.example.andry.vocabulary.WordLab;

public class NewWordDialogFragment extends DialogFragment {
    private static final String TAG = "NewWordDialogFragment";

    private PartOfSpeech mPartOfSpeech;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_new_word, null);

        final EditText mEditNative = view.findViewById(R.id.editNative);
        final EditText mEditForeign = view.findViewById(R.id.editForeign);
        mPartOfSpeech = PartOfSpeech.valueOf(getArguments().getString(LearnTab.ARG_PART_OF_SPEECH));
        final AlertDialog alertDialog =  new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.new_pair)
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, null)
                .create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                Button okBt = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);

                okBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!mEditNative.getText().toString().equals("") && !mEditNative.getText().toString().equals("")){
                            Word word = new Word(mEditNative.getText().toString(), mEditForeign.getText().toString(), mPartOfSpeech);
                            test();
                            WordLab.getWordLab(getActivity()).addWord(word);
                            dialog.cancel();
                            Toast.makeText(getActivity(), R.string.new_pair_added, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), R.string.fill_all_fields, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return alertDialog;
    }

    private void test(){
        if (WordLab.getWordLab(getActivity()).getWords(mPartOfSpeech, false).size() == 0) {
            for (int i = 1; i <= 100; i++) {
                Word word = new Word("тест #" + i, "test #" + i, mPartOfSpeech);
                WordLab.getWordLab(getActivity()).addWord(word);
            }
        }
    }
}
