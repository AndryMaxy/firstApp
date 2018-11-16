package com.example.andry.vocabulary.dialogFragments;

import android.app.Dialog;
import android.content.Context;
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

import com.example.andry.vocabulary.OnUpdateListener;
import com.example.andry.vocabulary.R;
import com.example.andry.vocabulary.Word;
import com.example.andry.vocabulary.WordLab;
import com.example.andry.vocabulary.WordListFragment;

public class EditWordDialogFragment extends DialogFragment {

    private OnUpdateListener mOnUpdateListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mOnUpdateListener = (OnUpdateListener) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_new_word, null);

        final Word word = getArguments().getParcelable(WordListFragment.ARG_WORD);

        final EditText mEditNative = view.findViewById(R.id.editNative);
        final EditText mEditForeign = view.findViewById(R.id.editForeign);

        mEditNative.setText(word.getNativeWord());
        mEditForeign.setText(word.getForeignWord());

        final AlertDialog alertDialog =  new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.edit_pair)
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
                            word.setNativeWord(mEditNative.getText().toString());
                            word.setForeignWord(mEditForeign.getText().toString());
                            WordLab.getWordLab(getActivity()).updateWord(word);
                            mOnUpdateListener.updateUI();
                            dialog.cancel();
                            Toast.makeText(getActivity(), R.string.edit_pair, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), R.string.fill_all_fields, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return alertDialog;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mOnUpdateListener = null;
    }
}
