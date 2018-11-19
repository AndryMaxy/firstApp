package com.andrydevelops.langnote.dialogFragments;

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
import android.widget.ImageView;
import android.widget.Toast;

import com.andrydevelops.langnote.OnUpdateListener;
import com.andrydevelops.langnote.PartOfSpeech;
import com.andrydevelops.langnote.R;
import com.andrydevelops.langnote.Word;
import com.andrydevelops.langnote.WordLab;
import com.andrydevelops.langnote.WordListFragment;

public class EditWordDialogFragment extends DialogFragment implements View.OnClickListener {

    private View mRootView;
    private EditText mEditNative;
    private EditText mEditNative2;
    private EditText mEditForeign;
    private PartOfSpeech mPartOfSpeech;
    private ImageView mAddTranslation;
    private ImageView mRemoveAddedTranslation;
    private boolean isAddActivated;

    private OnUpdateListener mOnUpdateListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mOnUpdateListener = (OnUpdateListener) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_new_word, null);

        final Word word = getArguments().getParcelable(WordListFragment.ARG_WORD);

        mEditNative = mRootView.findViewById(R.id.editNative);
        mEditForeign = mRootView.findViewById(R.id.editForeign);

        mEditNative.setText(word.getNativeWord());
        mEditForeign.setText(word.getForeignWord());

        mAddTranslation = mRootView.findViewById(R.id.addTranslation);
        mAddTranslation.setOnClickListener(this);
        if (word.getNativeWord2() != null) {
            isAddActivated = true;
            mAddTranslation.setVisibility(View.INVISIBLE);
            mEditNative2 = mRootView.findViewById(R.id.editNative2);
            mEditNative2.setVisibility(View.VISIBLE);
            mEditNative2.setText(word.getNativeWord2());

            mRemoveAddedTranslation = mRootView.findViewById(R.id.removeAddedTranslation);
            mRemoveAddedTranslation.setVisibility(View.VISIBLE);
            mRemoveAddedTranslation.setOnClickListener(this);

        }
        final AlertDialog alertDialog =  new AlertDialog.Builder(getActivity())
                .setView(mRootView)
                .setTitle(R.string.edit_pair)
                .setPositiveButton(R.string.dialog_ok, null)
                .setNegativeButton(R.string.dialog_cancel, null)
                .create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                Button okBt = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);

                okBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isAddActivated && mEditNative2 != null) {
                            if (!mEditNative.getText().toString().equals("") && !mEditForeign.getText().toString().equals("") && !mEditNative2.getText().toString().equals("")) {
                                //test();
                                word.setNativeWord(mEditNative.getText().toString());
                                word.setNativeWord2(mEditNative2.getText().toString());
                                word.setForeignWord(mEditForeign.getText().toString());
                                WordLab.getWordLab(getActivity()).addWord(word);
                                dialog.cancel();
                                mOnUpdateListener.updateUI();
                                showToast(R.string.pair_edited);
                            } else {
                                showToast(R.string.fill_all_fields);
                            }
                        } else {
                            if (!mEditNative.getText().toString().equals("") && !mEditForeign.getText().toString().equals("")) {
                                //test();
                                word.setNativeWord(mEditNative.getText().toString());
                                word.setNativeWord2(null);
                                word.setForeignWord(mEditForeign.getText().toString());
                                WordLab.getWordLab(getActivity()).addWord(word);
                                dialog.cancel();
                                mOnUpdateListener.updateUI();
                                showToast(R.string.pair_edited);
                            } else {
                                showToast(R.string.fill_all_fields);
                            }
                        }
                    }
                });
            }
        });

        return alertDialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addTranslation:
                mAddTranslation.setVisibility(View.INVISIBLE);
                mRemoveAddedTranslation = mRootView.findViewById(R.id.removeAddedTranslation);
                mRemoveAddedTranslation.setVisibility(View.VISIBLE);
                mRemoveAddedTranslation.setOnClickListener(this);
                mEditNative2 = mRootView.findViewById(R.id.editNative2);
                mEditNative2.setVisibility(View.VISIBLE);
                isAddActivated = true;
                break;
            case R.id.removeAddedTranslation:
                mRemoveAddedTranslation.setVisibility(View.GONE);
                mEditNative2.setVisibility(View.GONE);
                mAddTranslation.setVisibility(View.VISIBLE);
                isAddActivated = false;
                break;
        }
    }

    private void showToast(int resId) {
        Toast.makeText(getActivity(), resId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mOnUpdateListener = null;
    }
}
