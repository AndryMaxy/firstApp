package com.andrydelops.langnote.dialogFragments;

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
import android.widget.ImageView;
import android.widget.Toast;

import com.andrydelops.langnote.LearnTab;
import com.andrydelops.langnote.PartOfSpeech;
import com.example.andry.www.R;
import com.andrydelops.langnote.Word;
import com.andrydelops.langnote.WordLab;

import java.util.Calendar;

public class NewWordDialogFragment extends DialogFragment implements View.OnClickListener {
    private static final String TAG = "NewWordDialogFragment";

    private View mRootView;
    private EditText mEditNative;
    private EditText mEditNative2;
    private EditText mEditForeign;
    private PartOfSpeech mPartOfSpeech;
    private ImageView mAddTranslation;
    private ImageView mRemoveAddedTranslation;
    private boolean isAddActivated;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_new_word, null);

        mEditNative = mRootView.findViewById(R.id.editNative);
        mEditForeign = mRootView.findViewById(R.id.editForeign);
        mAddTranslation = mRootView.findViewById(R.id.addTranslation);
        mAddTranslation.setOnClickListener(this);
        mPartOfSpeech = PartOfSpeech.valueOf(getArguments().getString(LearnTab.ARG_PART_OF_SPEECH));
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setView(mRootView)
                .setTitle(R.string.new_pair)
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
                                Word word = new Word(mEditNative.getText().toString(), mEditForeign.getText().toString(), mPartOfSpeech);
                                //test();
                                word.setNativeWord2(mEditNative2.getText().toString());
                                WordLab.getWordLab(getActivity()).addWord(word);
                                dialog.cancel();
                                showToast(R.string.new_pair_added);
                            } else {
                                showToast(R.string.fill_all_fields);
                            }
                        } else {
                            if (!mEditNative.getText().toString().equals("") && !mEditForeign.getText().toString().equals("")) {
                                Word word = new Word(mEditNative.getText().toString(), mEditForeign.getText().toString(), mPartOfSpeech);
                               // test();
                                WordLab.getWordLab(getActivity()).addWord(word);
                                dialog.cancel();
                                showToast(R.string.new_pair_added);
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

    private void showToast(int resId) {
        Toast.makeText(getActivity(), resId, Toast.LENGTH_SHORT).show();
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

    private void test(){
        if (WordLab.getWordLab(getActivity()).getWords(mPartOfSpeech, false).size() == 0) {
        /*    for (int i = 1; i <= 100; i++) {
                Word word = new Word("тест #" + i, "test #" + i, mPartOfSpeech);
                WordLab.getWordLab(getActivity()).addWord(word);
            }*/
            Calendar calendar = Calendar.getInstance();
            calendar.set(2018, 11,13);
            Word word = new Word("achievement", "достижение", mPartOfSpeech);
            word.setDate(calendar.getTime());

            Word word2 = new Word("receive", "получать", mPartOfSpeech);
            word.setDate(calendar.getTime());

            Word word3 = new Word("road", "дорога", mPartOfSpeech);
            word3.setDate(calendar.getTime());

            Calendar calendar2 = Calendar.getInstance();
            calendar2.set(2018, 11,14);
            Word word4 = new Word("education", "образование", mPartOfSpeech);
            word4.setDate(calendar2.getTime());
            Word word5 = new Word("article", "статья", mPartOfSpeech);
            word5.setDate(calendar2.getTime());

            Word word6 = new Word("knowledge", "знания", mPartOfSpeech);
            word6.setDate(calendar2.getTime());

            Word word7 = new Word("cinema", "кинотеатр", mPartOfSpeech);
            word7.setDate(calendar2.getTime());

            Calendar calendar3 = Calendar.getInstance();
            calendar3.set(2018, 11,15);
            Word word8 = new Word("representation", "представление", mPartOfSpeech);
            word8.setDate(calendar3.getTime());

            Word word9 = new Word("picture", "картина", mPartOfSpeech);
            word9.setDate(calendar3.getTime());
            word9.setNativeWord2("рисунок");
            Word word10 = new Word("intent", "намерение", mPartOfSpeech);
            word10.setDate(calendar3.getTime());

            Word word11 = new Word("observer", "наблюдатель", mPartOfSpeech);
            word11.setDate(calendar3.getTime());

            Word word12 = new Word("statement", "заявление", mPartOfSpeech);
            word12.setDate(calendar3.getTime());

            Calendar calendar4 = Calendar.getInstance();
            calendar4.set(2018, 11,16);
            Word word13 = new Word("reference", "ссылка", mPartOfSpeech);
            word13.setNativeWord2("упоминание");
            word13.setDate(calendar4.getTime());

            Word word14 = new Word("access", "доступ", mPartOfSpeech);
            word14.setDate(calendar4.getTime());

            Word word15 = new Word("notice", "уведомление", mPartOfSpeech);
            word15.setDate(calendar4.getTime());

            Word word16 = new Word("chain", "цепь", mPartOfSpeech);
            word16.setDate(calendar4.getTime());

            Word word17 = new Word("query", "запрос", mPartOfSpeech);
            word17.setDate(calendar4.getTime());


            Word word19 = new Word("angle", "угол", mPartOfSpeech);
           // word19.setDate(calendar.getTime());

            Word word20 = new Word("engine", "двигатель", mPartOfSpeech);
           // word20.setDate(calendar.getTime());

            Word word21 = new Word("condition", "состояние", mPartOfSpeech);
         //   word21.setDate(calendar.getTime());
            WordLab.getWordLab(getActivity()).addWord(word);
            WordLab.getWordLab(getActivity()).addWord(word2);
            WordLab.getWordLab(getActivity()).addWord(word3);
            WordLab.getWordLab(getActivity()).addWord(word4);
            WordLab.getWordLab(getActivity()).addWord(word5);
            WordLab.getWordLab(getActivity()).addWord(word6);
            WordLab.getWordLab(getActivity()).addWord(word7);
            WordLab.getWordLab(getActivity()).addWord(word8);
            WordLab.getWordLab(getActivity()).addWord(word9);
            WordLab.getWordLab(getActivity()).addWord(word10);
            WordLab.getWordLab(getActivity()).addWord(word11);
            WordLab.getWordLab(getActivity()).addWord(word12);
            WordLab.getWordLab(getActivity()).addWord(word13);
            WordLab.getWordLab(getActivity()).addWord(word14);
            WordLab.getWordLab(getActivity()).addWord(word15);
            WordLab.getWordLab(getActivity()).addWord(word16);
            WordLab.getWordLab(getActivity()).addWord(word17);
            WordLab.getWordLab(getActivity()).addWord(word19);
            WordLab.getWordLab(getActivity()).addWord(word20);
            WordLab.getWordLab(getActivity()).addWord(word21);
        }
    }
}
